package br.gov.sc.cbm.e193comunitario.ui;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.adapter.CidadeAdapter;
import br.gov.sc.cbm.e193comunitario.db.CidadeHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.CidadeJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Cidade;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class VerificaCidadeActivity extends Activity implements
		OnItemSelectedListener, OnClickListener {

	private static final String TAG = "VerificaCidadeActivity";

	// reserva nome componentes
	private Spinner spCidade;
	private ImageButton imgBtnOk;
	private TextView tvCidade;
	private CheckBox cbNaoPergunta;
	private boolean isCidadeFixa;
	private boolean isSelecionaCidade;
	private CidadeHelper cidadeHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		// Busca pela minha cidade no Geocoder
		// TODO alterar o fluxo para prevenir a criação

		isCidadeFixa = ManageSP.getBooleanFromSharedPreference(
				this, Globals.PREF_FILE, Globals.KEY_FIXA_CIDADE);
		isSelecionaCidade = this.getIntent().getBooleanExtra(
				Globals.KEY_SELECIONA_CIDADE, false);

		cidadeHelper = new CidadeHelper(this);

		if (!isCidadeFixa && !isSelecionaCidade) {
			MeuTeste teste = new MeuTeste();
			teste.execute();
		} else if (isSelecionaCidade) {
			mostraTela();
		} else {
			iniciaMonitoramento();
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "onItemSelected");
		// como realizar a interação
		// Cidade cidade = (Cidade) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onNothingSelected");
	}

	public void mostraTela() {
		setContentView(R.layout.activity_verifica_cidade);
		// capturando componentes de tela
		spCidade = (Spinner) findViewById(R.id.sp_cidade);
		imgBtnOk = (ImageButton) findViewById(R.id.img_btn_ok);
		tvCidade = (TextView) findViewById(R.id.tv_escolher_cidade);
		cbNaoPergunta = (CheckBox) findViewById(R.id.cb_nao_perguntar);

		// setando atributos
		cbNaoPergunta.setChecked(isCidadeFixa);
		String user = ManageSP.getStringFromSharedPreference(
				this.getApplicationContext(), Globals.PREF_FILE,
				Globals.PREF_NOME);
		tvCidade.setText(user + ", "
				+ this.getString(R.string.tv_cidade_erro_localizacao));

		// String jsonListCidade = Globals.LIST_CIDADE_JSON_DEFAULT;
		// List<Cidade> listCidade =
		// CidadeJsonHelper.listCidadeFromJson(jsonListCidade);
		List<Cidade> listCidade = cidadeHelper.getAll();

		CidadeAdapter cidadeAdapter = new CidadeAdapter(this,
				R.layout.cidade_info, (ArrayList<Cidade>) listCidade);
		spCidade.setAdapter(cidadeAdapter);

		String jsonCidade = ManageSP
				.getStringFromSharedPreference(this, Globals.PREF_FILE,
						Globals.PREF_CIDADE);
		if (!Valida.isEmpty(jsonCidade)) {
			Cidade cidadePref = CidadeJsonHelper.cidadeFromJson(jsonCidade);
			if (cidadePref != null && !Valida.isEmpty(cidadePref.getId())) {
				for (int i = 0; i < listCidade.size(); i++) {
					Cidade auxCidade = listCidade.get(i);
					if (auxCidade.getId() == cidadePref.getId()) {
						spCidade.setSelection(i);
						break;
					}
				}
			}
		}

		// atribuindo ações
		spCidade.setOnItemSelectedListener(this);
		imgBtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Log.i(TAG, "onClick");
		switch (v.getId()) {
		case R.id.img_btn_ok:
			salvaCidade();
			break;
		default:
			break;
		}
	}

	private void salvaCidade() {
		Log.i(TAG, "salvaCidade");
		if (spCidade.getSelectedItemPosition() == 0) {
			Toast.makeText(this, R.string.toast_selecionar_cidade,
					Toast.LENGTH_SHORT).show();
		} else {
			Cidade cidade = (Cidade) spCidade.getSelectedItem();
			String jsonCidade = CidadeJsonHelper.cidadeToJson(cidade);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_CIDADE, jsonCidade);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_FIXA_CIDADE,
					cbNaoPergunta.isChecked());
			Log.i(TAG, jsonCidade);
			if (isSelecionaCidade) {
				Log.i(TAG, "finish");
				finish();
			} else {
				Log.i(TAG, "iniciaMonitoramento");
				iniciaMonitoramento();
			}
		}

	}

	private void iniciaMonitoramento() {
		Log.i(TAG, "iniciaMonitoramento");
		Intent intent = new Intent(this, MonitoramentoActivity.class);
		startActivity(intent);
		finish();
	}

	// AsyncTask<Params, Progress, Result>
	final class MeuTeste extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			//Log.i(TAG, "executei");
			if (!Valida.isEmpty(result)) {

				// collator para os teste
				Collator collator = Collator
						.getInstance(new Locale("pt", "BR"));
				collator.setStrength(Collator.PRIMARY);

				// listadas cidades
				// List<Cidade> listCidade =
				// CidadeJsonHelper.listCidadeFromJson(Globals.LIST_CIDADE_JSON_DEFAULT);
				List<Cidade> listCidade = cidadeHelper.getAll();
				boolean encontrou = false;
				for (Cidade cidade : listCidade) {

					if (collator
							.compare(result.trim(), cidade.getNome().trim()) == 0) {
						String jsonCidade = CidadeJsonHelper
								.cidadeToJson(cidade);
						ManageSP.putInSharedPreferences(
								getApplicationContext(), Globals.PREF_FILE,
								Globals.PREF_CIDADE, jsonCidade);
						encontrou = true;
						break;
					}
				}
				if (encontrou) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					mostraTela();
				}
			} else {
				mostraTela();
			}
		}

		@Override
		protected void onPreExecute() {
			//Log.i(TAG, "antes de executar");
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "Buscando cidade com base em LatLong");
			Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());
			List<Address> listAddress = null;
			Criteria c = new Criteria();
			c.setAccuracy(Criteria.ACCURACY_COARSE);
			LocationManager lManager = (LocationManager) getApplicationContext()
					.getSystemService(LOCATION_SERVICE);
			// LocationManager managerLast = (LocationManager)
			// getSystemService(Context.LOCATION_SERVICE);
			// String best = lManager.getProvider(name) getBestProvider(c,
			// true);
			// if (best != null) {
			Location l = lManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (l != null) {
				// Log.i(TAG, best);
				try {
					listAddress = geocoder.getFromLocation(l.getLatitude(),
							l.getLongitude(), 1);
					if (listAddress != null && listAddress.size() > 0) {
						Log.i(TAG, "ok");
						Address meuEndereco = listAddress.get(0);
						Log.i(TAG, meuEndereco.getLocality());
						return meuEndereco.getLocality();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, "Problema na requisição HTTP");
					Log.i(TAG, e.getMessage());
					Log.i(TAG, e.getLocalizedMessage());
					// e.printStackTrace();
					return null;
				}
			}
			// }
			return null;
		}

	}
}
