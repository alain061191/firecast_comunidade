package br.gov.sc.cbm.e193comunitario.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;

public class NotificacaoActivity extends Activity implements OnClickListener {

	// reserva nome de componentes
	private CheckBox cbNotificar, cbVibrar, cbTocarSom, cbExibirToast;
	private Button btnSalvar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificacao);

		// captura componentes de tela
		cbExibirToast = (CheckBox) findViewById(R.id.cb_exibir_msg);
		cbNotificar = (CheckBox) findViewById(R.id.cb_notificar);
		cbTocarSom = (CheckBox) findViewById(R.id.cb_tocar_som);
		cbVibrar = (CheckBox) findViewById(R.id.cb_vibrar);
		btnSalvar = (Button) findViewById(R.id.btn_salvar);

		// seta acoes aos componentes
		btnSalvar.setOnClickListener(this);
		cbNotificar.setOnClickListener(this);

		// seta propriedades aos componentes
		
		cbNotificar.setChecked(ManageSP
				.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_NOTIFICAR));
		/*
		
		cbVibrar.setChecked(ManageSharedPreferences
				.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_VIBRAR));
		cbExibirToast.setChecked(ManageSharedPreferences
				.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_TOAST));
		cbTocarSom.setChecked(ManageSharedPreferences
				.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_SOM));
						*/
		enableDisableDemais();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_salvar:
			salvar();
			finish();
			break;
		case R.id.cb_notificar:
			enableDisableDemais();
			break;
		default:
			break;
		}
	}

	private void enableDisableDemais() {
		if (cbNotificar.isChecked()) {
			cbExibirToast.setChecked(ManageSP
					.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
							Globals.KEY_NOTIFICACAO_TOAST));
			cbTocarSom.setChecked(ManageSP
					.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
							Globals.KEY_NOTIFICACAO_SOM));
			cbVibrar.setChecked(ManageSP
					.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
							Globals.KEY_NOTIFICACAO_VIBRAR));
			cbExibirToast.setEnabled(true);
			cbTocarSom.setEnabled(true);
			cbVibrar.setEnabled(true);
		} else {
			cbExibirToast.setChecked(false);
			cbExibirToast.setEnabled(false);
			cbTocarSom.setChecked(false);
			cbTocarSom.setEnabled(false);
			cbVibrar.setChecked(false);
			cbVibrar.setEnabled(false);
		}
	}

	private void salvar() {
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.KEY_NOTIFICACAO_NOTIFICAR, cbNotificar.isChecked());
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.KEY_NOTIFICACAO_SOM, cbTocarSom.isChecked());
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.KEY_NOTIFICACAO_TOAST, cbExibirToast.isChecked());
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.KEY_NOTIFICACAO_VIBRAR, cbVibrar.isChecked());
	}
}
