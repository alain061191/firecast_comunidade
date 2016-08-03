package br.gov.sc.cbm.e193comunitario.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;

public class DefinirAbrangenciaActivity extends Activity implements
		OnSeekBarChangeListener, OnClickListener {

	// reserva nome componentes
	private SeekBar sbRaio;
	private Button btnSalvar;
	private TextView tvRaio;
	private int maxRaio;
	private int minRaio;
	private int raioAtual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_definir_abrangencia);

		// captura componentes da tela
		sbRaio = (SeekBar) findViewById(R.id.sb_raio);
		tvRaio = (TextView) findViewById(R.id.tv_raio);
		btnSalvar = (Button) findViewById(R.id.btn_ok);

		// seta propriedades nos componentes
		maxRaio = Globals.MAX_ABRNGENCIA_GPS;
		minRaio = Globals.MIN_ABRNGENCIA_GPS;
		raioAtual = ManageSP.getIntFromSharedPreference(this,
				Globals.PREF_FILE, Globals.PREF_ABRANGENCIA_GPS);
		sbRaio.setMax(maxRaio);
		sbRaio.setProgress(raioAtual);
		tvRaio.setText(Integer.toString(sbRaio.getProgress()) + " km");

		// Seta açoes naos componentes
		sbRaio.setOnSeekBarChangeListener(this);

		btnSalvar.setOnClickListener(this);

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress >= minRaio) {
			tvRaio.setText(Integer.toString(progress) + " km");
		} else {
			sbRaio.setProgress(minRaio);
			tvRaio.setText(Integer.toString(minRaio) + " km");
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_ABRANGENCIA_GPS, seekBar.getProgress());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			salvar();
			break;

		default:
			break;
		}

	}

	private void salvar() {
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_ABRANGENCIA_GPS, sbRaio.getProgress());
		finish();
	}
}
