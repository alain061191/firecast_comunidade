package br.gov.sc.cbm.e193comunitario.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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



public class DefinirCidadeActivity extends Activity implements OnItemSelectedListener,
		OnClickListener {


	private static final String TAG = "DefinirCidade";

	// reserva nome componentes
	private Spinner spCidade;
	private ImageButton imgBtnOk;
	private TextView tvCidade;
	private CidadeHelper cidadeHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	cidadeHelper = new CidadeHelper(this);
	mostraTela();
	
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//Cidade cidade = (Cidade) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public void mostraTela() {
		setContentView(R.layout.activity_definir_cidade);

		// capturando componentes de tela
		spCidade = (Spinner) findViewById(R.id.sp_cidade);
		imgBtnOk = (ImageButton) findViewById(R.id.img_btn_ok);
		tvCidade = (TextView) findViewById(R.id.tv_escolher_cidade);

		// setando atributos
		String user = ManageSP.getStringFromSharedPreference(
				this.getApplicationContext(), Globals.PREF_FILE,
				Globals.PREF_NOME);
		tvCidade.setText(user
				+ ", "+this.getString(R.string.info_erro_localizacao));

		//String jsonListCidade = Globals.LIST_CIDADE_JSON_DEFAULT;
		//List<Cidade> listCidade = CidadeJsonHelper.listCidadeFromJson(jsonListCidade);
		List<Cidade> listCidade = cidadeHelper.getAll();

		CidadeAdapter cidadeAdapter = new CidadeAdapter(this,
				R.layout.cidade_info, (ArrayList<Cidade>) listCidade);
		spCidade.setAdapter(cidadeAdapter);

		// atribuindo ações
		spCidade.setOnItemSelectedListener(this);
		imgBtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_btn_ok:
			salvaCidade();
			break;

		default:
			break;
		}
	}

	private void salvaCidade() {
		if (spCidade.getSelectedItemPosition() == 0) {
			Toast.makeText(this, this.getString(R.string.toast_info_selecionar_cidade), Toast.LENGTH_SHORT)
					.show();
		} else {
			Cidade cidade = (Cidade) spCidade.getSelectedItem();
			String jsonCidade = CidadeJsonHelper.cidadeToJson(cidade);
			ManageSP.putInSharedPreferences(this,
					Globals.PREF_FILE, Globals.PREF_CIDADE, jsonCidade);
			finish();
		}

	}

}
