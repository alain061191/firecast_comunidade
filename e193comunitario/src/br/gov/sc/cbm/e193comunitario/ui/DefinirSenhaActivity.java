package br.gov.sc.cbm.e193comunitario.ui;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.util.Criptografia;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class DefinirSenhaActivity extends Activity implements OnClickListener {
	
	public static final String TAG = "DefinirSenhaActivity";

	// reserva nome componentes
	private EditText edtPW;
	private Button btnLimparSenha, btnSalvar;
	private Switch swSenha;
	private LinearLayout llSenhaItens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_definir_senha);

		// captura componentes da tela
		edtPW = (EditText) findViewById(R.id.edt_pw);
		btnLimparSenha = (Button) findViewById(R.id.btn_limpa_senha);
		btnSalvar = (Button) findViewById(R.id.btn_salvar);
		swSenha = (Switch) findViewById(R.id.sw_senha);
		llSenhaItens = (LinearLayout) findViewById(R.id.ll_senha_itens);

		// seta propriedades nos componentes
		btnSalvar.setOnClickListener(this);
		btnLimparSenha.setOnClickListener(this);
		swSenha.setOnClickListener(this);
		pwIsSet();
	}

	private void pwIsSet() {
		String pw = ManageSP.getStringFromSharedPreference(this,
				Globals.PREF_FILE, Globals.PREF_PW);
		if (Valida.isEmpty(pw)) {
			swSenha.setChecked(false);
			exigirPW(false);
		} else {
			swSenha.setChecked(true);
			exigirPW(true);
			edtPW.setText(pw);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_salvar:
			setaLogin();
			break;
		case R.id.sw_senha:
			exigirPW(swSenha.isChecked());
			break;
		case R.id.btn_limpa_senha:
			limparSenha();
			break;
		default:
			break;
		}
	}

	private void limparSenha() {
		edtPW.getText().clear();
	}

	private void exigirPW(boolean isChecked) {
		if (isChecked) {
			llSenhaItens.setVisibility(View.VISIBLE);
			edtPW.requestFocus();
		} else {
			llSenhaItens.setVisibility(View.GONE);
			edtPW.clearFocus();
		}
	}

	private void setaLogin() {
		if (swSenha.isChecked()) {
			if (Valida.isEmpty(edtPW.getText().toString())) {
				Toast.makeText(this, this.getString(R.string.toast_info_senha_em_branco),
						Toast.LENGTH_SHORT).show();
				edtPW.requestFocus();
			} else {
				String senhaMd5;
				
				try {
					senhaMd5 = Criptografia.getMd5(edtPW.getText()
							.toString());
				} catch (NoSuchAlgorithmException e) {
					senhaMd5 = edtPW.getText()
							.toString();
					String msg = this.getString(R.string.toast_info_erro_criptografia);
					Log.e(TAG, msg);
					Log.e(TAG, e.getMessage());
					Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
				}
				
				ManageSP.putInSharedPreferences(this,
						Globals.PREF_FILE,Globals.PREF_PW, senhaMd5);
				finish();
			}
		} else {
			ManageSP.remove(this, Globals.PREF_FILE,
					Globals.PREF_PW);
			finish();
		}
	}
}
