package br.gov.sc.cbm.e193comunitario.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.adapter.TipoEmergenciaAdapter;
import br.gov.sc.cbm.e193comunitario.db.TipoEmergenciaHelper;
import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;

public class DefinirInteressesActivity extends Activity implements
		OnClickListener {

	// reserva o nome dos componentes
	private TipoEmergenciaAdapter tipoEmergenciaAdapter = null;
	private ListView lvInteresses;
	private List<TipoEmergencia> listTipoEmergencia;
	private Button btnSalvar;
	TipoEmergenciaHelper tipoEmergenciaHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_definir_interesses);

		// captura componentes de telas
		btnSalvar = (Button) findViewById(R.id.btn_ok);
		lvInteresses = (ListView) findViewById(R.id.lv_interesses);

		// seta propriedades nos componentes
		btnSalvar.setOnClickListener(this);

		// carrega lista de tipos e cria o array adapter
		tipoEmergenciaHelper = new TipoEmergenciaHelper(this);
		listTipoEmergencia = tipoEmergenciaHelper.getAll();

		tipoEmergenciaAdapter = new TipoEmergenciaAdapter(this,
				R.layout.tipo_emergencia_info, listTipoEmergencia);

		lvInteresses.setAdapter(tipoEmergenciaAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			salvar();
			finish();
			break;

		default:
			break;
		}
	}

	private void salvar() {
		TipoEmergenciaHelper tipoEmergenciaHelper = new TipoEmergenciaHelper(
				this);
		for (int i = 0; i < tipoEmergenciaAdapter.getListTipo().size(); i++) {
			tipoEmergenciaHelper.updateById(tipoEmergenciaAdapter.getListTipo()
					.get(i));
		}
	}

}