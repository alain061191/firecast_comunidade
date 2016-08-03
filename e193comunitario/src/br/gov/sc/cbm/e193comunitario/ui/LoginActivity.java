package br.gov.sc.cbm.e193comunitario.ui;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.util.Criptografia;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";

	// reserva nome componentes
	private EditText edtNome, edtPW;
	private ImageButton imgBtnOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// verifica se possui nome, entao verifica se possuir senha
		if (!Valida.isEmpty(ManageSP
				.getStringFromSharedPreference(this, Globals.PREF_FILE,
						Globals.PREF_NOME))) {
			// Verifica se possui senha, se for em branco passa para o
			// monitoramento
			if (Valida.isEmpty(ManageSP
					.getStringFromSharedPreference(this, Globals.PREF_FILE,
							Globals.PREF_PW))) {
				Intent intent = new Intent(this, MonitoramentoActivity.class);
				startActivity(intent);
				finish();
			}
		} else {
			Toast.makeText(this, this.getString(R.string.toast_info_informe_nome), Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		setContentView(R.layout.activity_login);

		// captura componentes da tela
		edtNome = (EditText) findViewById(R.id.edt_nome);
		edtPW = (EditText) findViewById(R.id.edt_pw);
		imgBtnOK = (ImageButton) findViewById(R.id.img_btn_ok);

		// seta propriedades nos componentes
		imgBtnOK.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_btn_ok:
			verificaLogin();
			break;

		default:
			break;
		}
	}

	private void verificaLogin() {
		// verifica se esta tudo preenchido
		if (Valida.isEmpty(edtNome.getText().toString())
				|| Valida.isEmpty(edtPW.getText().toString())) {
			Toast.makeText(this, this.getString(R.string.toast_info_informe_nome_senha), Toast.LENGTH_SHORT)
					.show();
			edtNome.requestFocus();
			// se nome e senha forem iguais aos armazenados prossegue
		} else {
			String userInfo = edtNome.getText().toString();
			String userPref = ManageSP
					.getStringFromSharedPreference(this, Globals.PREF_FILE,
							Globals.PREF_NOME);
			String senhaMD5Pref = ManageSP
					.getStringFromSharedPreference(this, Globals.PREF_FILE,
							Globals.PREF_PW);
			String senhaMD5Info;
			try {
				senhaMD5Info = Criptografia.getMd5(edtPW.getText().toString());
				if (userPref.equalsIgnoreCase(userInfo)
						&& senhaMD5Pref.equals(senhaMD5Info)) {
					Intent intent = new Intent(this,
							MonitoramentoActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(this, this.getString(R.string.toast_erro_usuario_senha_incorretos),
							Toast.LENGTH_SHORT).show();
					edtNome.requestFocus();
				}
			} catch (NoSuchAlgorithmException e) {
				// e.printStackTrace();
				String msg = this.getString(R.string.toast_erro_critografia_limpar_dados);
				Log.e(TAG, msg);
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

				senhaMD5Info = edtPW.getText().toString();
				if (userPref.equalsIgnoreCase(userInfo)
						&& senhaMD5Pref.equals(senhaMD5Info)) {
					Intent intent = new Intent(this,
							MonitoramentoActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(this, this.getString(R.string.toast_erro_usuario_senha_incorretos),
							Toast.LENGTH_SHORT).show();
					edtNome.requestFocus();
				}

			}
		}
	}
}
