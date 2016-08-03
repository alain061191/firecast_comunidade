package br.gov.sc.cbm.e193comunitario.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.receiver.AlarmReceiver;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;

public class MonitoramentoActivity extends Activity implements OnClickListener,
		LocationListener {
	public static final String TAG = "MonitoramentoActivity";

	// reserva nome componentes
	private TextView tvGps;
	private ToggleButton tbtnMonitorar;
	private Button btnVerAbertas, btnVerInteresse;
	private LocationManager lManager;
	private Spinner spnRadiosOnLine;
	private ImageButton imgBtnPlayRadioOnLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoramento);

		// captura componentes na tela
		tvGps = (TextView) findViewById(R.id.tv_gps);
		tbtnMonitorar = (ToggleButton) findViewById(R.id.tbtn_monitorar);
		btnVerAbertas = (Button) findViewById(R.id.btn_ver_abertas);
		btnVerInteresse = (Button) findViewById(R.id.btn_ver_interesse);
		spnRadiosOnLine = (Spinner) findViewById(R.id.spn_radios);
		imgBtnPlayRadioOnLine = (ImageButton) findViewById(R.id.img_btn_play_radio_on_line);

		// seta propriedades nos componentes
		imgBtnPlayRadioOnLine.setOnClickListener(this);
		tbtnMonitorar.setOnClickListener(this);
		btnVerAbertas.setOnClickListener(this);
		btnVerInteresse.setOnClickListener(this);

		ArrayAdapter<String> adapterRadios = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				Globals.LIST_NOME_CIDADE_RADIO_ON_LINE);
		spnRadiosOnLine.setAdapter(adapterRadios);

		tbtnMonitorar.setChecked(ManageSP
				.getBooleanFromSharedPreference(this, Globals.PREF_FILE,
						Globals.PREF_STATUS_MONITORAMENTO));

		verificaStatusBotao();

		startGPS();
	}

	@SuppressLint("SimpleDateFormat")
	private void startGPS() {
		lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// TELA
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_COARSE);
		// LocationManager managerLast = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		String best = lManager.getBestProvider(c, false);
		Location l = lManager.getLastKnownLocation(best);
		if (l != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String time = sdf.format(l.getTime());

			Log.i(TAG, "Latitude = " + Double.toString(l.getLatitude())
					+ "\nLongitude = " + Double.toString(l.getLongitude())
					+ "\nPrecisão = " + Float.toString(l.getAccuracy())
					+ "\nProvedor = " + l.getProvider() + "\nEm: " + time);

			tvGps.setText("Latitude = " + Double.toString(l.getLatitude())
					+ "\nLongitude = " + Double.toString(l.getLongitude())
					+ "\nPrecisão = " + Float.toString(l.getAccuracy())
					+ "\nProvedor = " + l.getProvider() + "\nEm: " + time);
		}
		// TELA

		if (!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("GPS");
			builder.setMessage(R.string.dialog_gps);
			builder.setPositiveButton(R.string.tbtn_yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(intent);
						}
					});
			builder.setNeutralButton(R.string.tbtn_no, null);
			Dialog alertDialog = builder.create();
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.show();
		}

		LocationListener lListener = this;
		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000,
				25, lListener);
		// lManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, lListener,
		// null);

	}

	@Override
	protected void onResume() {
		super.onResume();

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.monitoramento, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			configuar();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tbtn_monitorar:
			verificaStatusBotao();
			break;
		case R.id.btn_ver_abertas:
			verOcorrenciasTodas();
			break;
		case R.id.btn_ver_interesse:
			verOcorrenciasInteresse();
			break;
		case R.id.img_btn_play_radio_on_line:
			actionPlayRadioOnline();
			break;
		default:
			break;
		}

	}

	private void actionPlayRadioOnline() {
		Log.i(TAG, "actionPlayRadioOnline");
		String cityKey = (String) spnRadiosOnLine.getSelectedItem();
		String urlRadioOnLine = getUrlRadioByCity(cityKey);
		Uri myUri = Uri.parse(urlRadioOnLine);
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(myUri, "audio/*");
		startActivity(intent);
	}

	public boolean isRedeInterna() {
		Log.i(TAG, "isRedeInterna");
		String ipBase10 = getIPv4AddressBase10(this);
		String ipSeparado[] = ipBase10.split("\\.");
		if (ipSeparado[0].equalsIgnoreCase("10")
				&& (ipSeparado[1].equalsIgnoreCase("193") || ipSeparado[1]
						.equalsIgnoreCase("194"))) {
			Log.i(TAG, "Rede Interna");
			return true;
		} else {
			Log.i(TAG, "Rede Externa");
			return false;
		}

	}

	private String getIPv4AddressBase10(Context context) {
		Log.i(TAG, "getIPv4AddressBase10");
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		String strIP = String.format(Locale.getDefault(), "%d.%d.%d.%d",
				(ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff),
				(ip >> 24 & 0xff));
		Log.i(TAG, strIP);
		return strIP;
	}

	public String getUrlRadioByCity(String nameCity) {
		Log.i(TAG, "getUrlRadioByCity");
		if (isRedeInterna()) {
			Log.i(TAG, Globals.MAP_URL_RADIO_BY_CITY_INTERNO.get(nameCity));
			return Globals.MAP_URL_RADIO_BY_CITY_INTERNO.get(nameCity);
		} else {
			Log.i(TAG, Globals.MAP_URL_RADIO_BY_CITY_EXTERNO.get(nameCity));
			return Globals.MAP_URL_RADIO_BY_CITY_EXTERNO.get(nameCity);
		}
	}

	private void verOcorrenciasTodas() {
		//Intent intent = new Intent(this, OcorrenciasTodasActivity.class);
		Intent intent = new Intent(this, OcorrenciasTodasActivity.class);
		intent.putExtra(Globals.KEY_EXTRA_OCORRENCIA,
				Globals.KEY_OCORRENCIA_TODAS);
		startActivity(intent);
	}

	private void verOcorrenciasInteresse() {
		Intent intent = new Intent(this, OcorrenciasTodasActivity.class);
		intent.putExtra(Globals.KEY_EXTRA_OCORRENCIA,
				Globals.KEY_OCORRENCIA_TIPO);
		startActivity(intent);
	}

	private void verificaStatusBotao() {
		if (tbtnMonitorar.isChecked()) {
			ativarMonitoramento();
		} else {
			pararMonitoramento();
		}
	}

	private void pararMonitoramento() {
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_STATUS_MONITORAMENTO, false);
		AlarmManager alarmMgr = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent,
				0);

		alarmMgr.cancel(alarmIntent);
	}

	private void ativarMonitoramento() {
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_STATUS_MONITORAMENTO, true);
		AlarmManager alarmMgr = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent,
				0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		long inicio = calendar.getTimeInMillis();

		long intervalo = 45 * 1000;

		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, inicio, intervalo,
				alarmIntent);

	}

	private void configuar() {
		Intent intent = new Intent(this, ConfiguracaoActivity.class);
		startActivity(intent);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// tvGps.setText("Provedor: " + provider + "\nStatus: " +
		// Integer.toString(status));
	}

	@Override
	public void onProviderEnabled(String provider) {
		tvGps.setText(this.getString(R.string.tv_gps_ativado) + provider);
	}

	@Override
	public void onProviderDisabled(String provider) {
		tvGps.setText(R.string.tv_gps_desativado);
	}

	@Override
	public void onLocationChanged(Location location) {
		Globals.myLatitude = location.getLatitude();
		Globals.myLongitude = location.getLongitude();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String time = sdf.format(location.getTime());

		// TODO intenacionaliza
		tvGps.setText("Latitude = " + Double.toString(Globals.myLatitude)
				+ "\nLongitude = " + Double.toString(Globals.myLongitude)
				+ "\nPrecisão = " + Float.toString(location.getAccuracy())
				+ "\nProvedor = " + location.getProvider() + "\nEm: " + time);

		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_MY_LATITUDE,
				Double.toString(location.getLatitude()));
		ManageSP.putInSharedPreferences(this, Globals.PREF_FILE,
				Globals.PREF_MY_LATITUDE,
				Double.toString(location.getLatitude()));

	}

}
