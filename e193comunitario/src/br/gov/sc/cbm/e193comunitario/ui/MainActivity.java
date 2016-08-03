package br.gov.sc.cbm.e193comunitario.ui;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class MainActivity extends Activity implements OnClickListener {

	public static final String TAG = "MainActivity";
	// reserva nome dos componentes
	private EditText edtNome;
	private ImageButton imgBtnOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO fezer o esquema para atualizar a lista de cidades e tipos

		// Verifica se possui nome, se possuir passa para o login
		String nome = ManageSP.getStringFromSharedPreference(
				this, Globals.PREF_FILE, Globals.PREF_NOME);
		if (!Valida.isEmpty(nome)) {
			Intent intent = new Intent(this, VerificaCidadeActivity.class);
			startActivity(intent);
			finish();

		} else {
			// se for em branco é a primeira execução
			// por padrão o monitoramento é ativado
			// atribui o valor do raio de busca para a metado do maximo
			// inicio o set de código vazio
			// ativa todos os parametros de notificações: 
			// Notificação
			// Som
			// Mensagem Toast
			// Vibrar
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_STATUS_MONITORAMENTO, true);
			float auxRaio = Globals.MAX_ABRNGENCIA_GPS / 2;
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_ABRANGENCIA_GPS,
					Math.round(auxRaio));
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_OCORRENCIA_ULTIMO_TS,
					Globals.TS_MINIMO);
			Set<String> codigos = new LinkedHashSet<String>();
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_COD_OCORRENCIA_NOTIFICAR,
					codigos);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_NOTIFICAR, true);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_SOM, true);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_TOAST, true);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_VIBRAR, true);
		}

		setContentView(R.layout.activity_main);

		// busca componentes na tela
		imgBtnOK = (ImageButton) findViewById(R.id.img_btn_ok);
		edtNome = (EditText) findViewById(R.id.edt_nome);

		// seta acoes
		imgBtnOK.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_btn_ok:
			salvaNome();
			break;

		default:
			break;
		}
	}

	private void salvaNome() {
		if (Valida.isEmpty(edtNome.getText().toString())) {
			Toast.makeText(this, this.getString(R.string.toast_info_informe_nome), Toast.LENGTH_SHORT).show();
			edtNome.requestFocus();
		} else {
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_NOME, edtNome.getText()
							.toString());
			Intent intent = new Intent(this, VerificaCidadeActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
