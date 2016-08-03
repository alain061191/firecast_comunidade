package br.gov.sc.cbm.e193comunitario.ui;

import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.jsonhelper.OcorrenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
import br.gov.sc.cbm.e193comunitario.model.Viatura;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class DetalheOcorrenciaActivity extends Activity implements
		OnClickListener {

	public static final String TAG = "DetalheOcorrenciaActivity";
	

	// reserva nome componentes
	private TextView tvTipo, tvCidade, tvBairro, tvLogradouro, tvComplemento,
			tvNumero, tvReferencia, tvDescicao, tvTempo, tvDistancia,
			tvViatura;
	private Button btnIr, btnMostrarMapa, btnCompartilhar;
	private Ocorrencia ocorrencia = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_ocorrencia);

		// captura componentes de tela
		btnIr = (Button) findViewById(R.id.btn_ir);
		btnMostrarMapa = (Button) findViewById(R.id.btn_mostrar_mapa);
		btnCompartilhar = (Button) findViewById(R.id.btn_compartilhar);
		tvBairro = (TextView) findViewById(R.id.tv_bairro);
		tvCidade = (TextView) findViewById(R.id.tv_escolher_cidade);
		tvDescicao = (TextView) findViewById(R.id.tv_descricao);
		tvLogradouro = (TextView) findViewById(R.id.tv_logradouro);
		tvNumero = (TextView) findViewById(R.id.tv_numero);
		tvComplemento = (TextView) findViewById(R.id.tv_complemento);
		tvTipo = (TextView) findViewById(R.id.tv_tipo);
		tvTempo = (TextView) findViewById(R.id.tv_tempo);
		tvReferencia = (TextView) findViewById(R.id.tv_referencia);
		tvDistancia = (TextView) findViewById(R.id.tv_distancia);
		tvViatura = (TextView) findViewById(R.id.tv_viatura);

		// seta propriedades
		btnCompartilhar.setOnClickListener(this);

		btnIr.setClickable(false);
		btnIr.setActivated(false);
		btnIr.setEnabled(false);

		// busca por parâmetro da ocorrência e mostra na tela
		String auxOcorrencia = this.getIntent().getStringExtra(
				Globals.KEY_EXTRA_OCORRENCIA);
		// Log.d(TAG, auxOcorrencia);
		if (!Valida.isEmpty(auxOcorrencia)) {
			Log.d(TAG, auxOcorrencia);
			// veio alguma ocorrencia no extras
			ocorrencia = OcorrenciaJsonHelper.ocorrenciaFromJson(auxOcorrencia);

			if (!Valida.isEmpty(ocorrencia.getTipoEmergencia().getNome())) {
				tvTipo.setText(ocorrencia.getTipoEmergencia().getNome());
			}
			if (!Valida.isEmpty(ocorrencia.getCidade().getNome())) {
				tvCidade.setText(ocorrencia.getCidade().getNome());
			}
			if (!Valida.isEmpty(ocorrencia.getBairro())) {
				tvBairro.setText(ocorrencia.getBairro());
			}
			if (!Valida.isEmpty(ocorrencia.getLogradouro())) {
				tvLogradouro.setText(ocorrencia.getLogradouro());
			}
			if (!Valida.isEmpty(ocorrencia.getComplemento())) {
				tvComplemento.setText(ocorrencia.getComplemento());
			}
			if (!Valida.isEmpty(Integer.toString(ocorrencia.getNumero()))) {
				tvNumero.setText(Integer.toString(ocorrencia.getNumero()));
			}
			if (!Valida.isEmpty(ocorrencia.getReferencia())) {
				tvReferencia.setText(ocorrencia.getReferencia());
			}
			if (!Valida.isEmpty(ocorrencia.getDescricao())) {
				tvDescicao.setText(ocorrencia.getDescricao());
			}

			// TODO
			if (ocorrencia.getListViatura() != null) {
				StringBuffer vtrAux = new StringBuffer();
				for (Iterator<Viatura> iterator = ocorrencia.getListViatura()
						.iterator(); iterator.hasNext();) {
					Viatura vtr = iterator.next();
					vtrAux.append(vtr.getNome());
				}
				String vtrs = vtrAux.toString();
				if (!Valida.isEmpty(vtrs)) {
					tvViatura.setText(vtrs);
				}
			}

			if (!Valida.isEmpty(ocorrencia.getTs())) {
				tvTempo.setText(ocorrencia.getTsFormatoBR());
			}
					
			
			//TODO buscar as minha coordenadas locais e colocar no getDistancia
			if (!Valida.isEmpty(ocorrencia.getLatitude()) && !Valida.isEmpty(ocorrencia.getLongitude())) {
				String distancia = this.getString(R.string.info_sem_informacao);
							
				LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				
				// se não tem Location ou o GeoCoder não esta instalado não faz nada
				if (null != location && Geocoder.isPresent()) {
					double lat = location.getLatitude();
					double lon = location.getLongitude();
					float avg = 0.0f;
					if (location.hasAccuracy()) {
						avg = location.getAccuracy();
					}					
					
					if (ocorrencia.getDistancia(lat, lon) != 0) {
						distancia = ocorrencia.getDistanciaFormatada(lat, lon);
					}
					tvDistancia.setText(distancia);
				}
			}		
			
			
			String endereco = ocorrencia.getEnderecoWhichOutBairro();
			if (!Valida.isEmpty(endereco)) {
				btnMostrarMapa.setClickable(true);
				btnMostrarMapa.setActivated(true);
				btnMostrarMapa.setEnabled(true);
				btnMostrarMapa.setOnClickListener(this);
			} else {
				btnMostrarMapa.setClickable(false);
				btnMostrarMapa.setActivated(false);
				btnMostrarMapa.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_mostrar_mapa:
			if (ocorrencia != null) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("google.navigation:q="
								+ ocorrencia.getEnderecoWhichOutBairro()));
				startActivity(intent);
				Toast.makeText(this,
						this.getString(R.string.toast_info_abrindo_navegador),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						this,
						this.getString(R.string.toast_info_endereco_insificiente),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_compartilhar:
			// Converte os IDs de cidade e ocorrencia para B36
			String idCidadeB36 = Integer.toString(ocorrencia.getCidade()
					.getId(), 36);
			String idOcorrenciaB36 = Integer.toString(ocorrencia.getId(), 36);

			// concatena os IDs em uma URL para ser lida na Redes sociais
			String param = "?" + Globals.URL_PARAM_ID + "=" + idCidadeB36
					+ idOcorrenciaB36;

			String aux = ocorrencia.getDescriptionForHumanRead();
			aux = aux + "\nEnviado a partir de "
					+ this.getString(R.string.app_name) + " - CBMSC\n"
					+ Globals.URL_DETALHES_OCORRENCIA + param + "\n" + 
					"Disponível em: https://goo.gl/3nSKO0";

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, aux);
			intent.setType("text/plain");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
