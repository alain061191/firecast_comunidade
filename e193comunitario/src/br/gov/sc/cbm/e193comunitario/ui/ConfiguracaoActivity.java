package br.gov.sc.cbm.e193comunitario.ui;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;

public class ConfiguracaoActivity extends Activity implements OnClickListener {

	// reserva nome dos componentes
	private Button btnSenha, btnNotificacao, btnInteresse, btnAbrangencia,
			btnCidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);

		// captura componentes de tela
		btnNotificacao = (Button) findViewById(R.id.btn_notificacao);
		btnSenha = (Button) findViewById(R.id.btn_senha);
		btnInteresse = (Button) findViewById(R.id.btn_interesses);
		btnAbrangencia = (Button) findViewById(R.id.btn_abrangencia);
		btnCidade = (Button) findViewById(R.id.btn_cidade);

		// seta propriedades nos componentes
		btnSenha.setOnClickListener(this);
		btnNotificacao.setOnClickListener(this);
		btnInteresse.setOnClickListener(this);
		btnAbrangencia.setOnClickListener(this);
		btnCidade.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_senha:
			definirSenha();
			break;
		case R.id.btn_interesses:
			definirInteresse();
			break;
		case R.id.btn_abrangencia:
			definirAbrangencia();
			break;
		case R.id.btn_cidade:
			definirCidade();
			break;
		case R.id.btn_notificacao:
			definirNotificacao();
			break;
		default:
			break;
		}
	}

	private void definirNotificacao() {
		Intent intent = new Intent(this, NotificacaoActivity.class);
		startActivity(intent);
	}

	private void definirCidade() {
		if (Geocoder.isPresent()) {
			Intent intent = new Intent(this, VerificaCidadeActivity.class);
			intent.putExtra(Globals.KEY_SELECIONA_CIDADE, true);
			startActivity(intent);
		} else {
			Toast.makeText(
					this,this.getString(R.string.toast_info_erro_api_geocoder),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void definirAbrangencia() {
		if (Geocoder.isPresent()) {
			Intent intent = new Intent(this, DefinirAbrangenciaActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(
					this,this.getString(R.string.toast_info_erro_api_geocoder),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void definirInteresse() {
		Intent intent = new Intent(this, DefinirInteressesActivity.class);
		startActivity(intent);
	}

	private void definirSenha() {
		Intent intent = new Intent(this, DefinirSenhaActivity.class);
		startActivity(intent);
	}
}
