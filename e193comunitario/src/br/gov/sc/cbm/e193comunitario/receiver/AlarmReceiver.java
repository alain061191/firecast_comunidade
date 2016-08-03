package br.gov.sc.cbm.e193comunitario.receiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.db.OcorrenciaHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.CidadeJsonHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.OcorrenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Cidade;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
import br.gov.sc.cbm.e193comunitario.util.ConexaoHttpClient;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Notificacao;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class AlarmReceiver extends BroadcastReceiver {

	public static final String TAG = "AlarmReceiver";

	@Override
	public void onReceive(final Context context, Intent intent) {

		Log.d(TAG, "O alarme executou as " + new Date());

		// busca as ocorrencias do servidor, balcula GEO, insere e notifica
		final OcorrenciaHelper ocorrenciaHelper = new OcorrenciaHelper(context);
		// final Ocorrencia lastOcorrencia = ocorrenciaHelper.getByLastTs();
		final Ocorrencia lastOcorrencia = null;
		final Cidade cidade = getCidadePref(context);

		AsyncTask<String, Void, List<Ocorrencia>> asyncTask = new AsyncTask<String, Void, List<Ocorrencia>>() {

			@Override
			protected List<Ocorrencia> doInBackground(String... params) {

				// lista das novas ocorrencias que podem ser notificadas
				List<Ocorrencia> listOcorrenciaNovas = new ArrayList<Ocorrencia>();

				// esta link é HTTP
				List<Ocorrencia> listOcorrenciaServer = null;
				try {
					listOcorrenciaServer = getOcorrenciaFromServerByCidade(
							context, cidade, lastOcorrencia);
					// se não voltar nada do servidor insere uma lista vazia
					if (listOcorrenciaServer == null) {
						listOcorrenciaServer = new ArrayList<Ocorrencia>();
					}
				} catch (Exception e2) {
					// e2.printStackTrace();
					Log.e(TAG, "getOcorrenciaFromServerByCidade : ERRO");
					Log.e(TAG, e2.getLocalizedMessage());
					listOcorrenciaServer = new ArrayList<Ocorrencia>();
				}

				// verifica se as ocorrencias que vieram são novas, se forem
				// calcula o GEO
				for (Ocorrencia ocorrenciaServer : listOcorrenciaServer) {
					Ocorrencia ocorrenciaAux = ocorrenciaHelper.getById(
							OcorrenciaHelper.ID, ocorrenciaServer.getId());

					// ocorrecia nova, calcular GEO e inserir na base local e
					// notificar
					if (null == ocorrenciaAux) {

						// ocorrecia nova, calcular GEO e inserir na base local
						// e notificar
						if (0 == ocorrenciaServer.getLatitude()
								|| 0 == ocorrenciaServer.getLongitude()) {

							try {
								ocorrenciaAux = findGeoOcorrencia(context,
										ocorrenciaServer);
							} catch (IOException e1) {
								// e1.printStackTrace();
								Log.e(TAG, "calcGeoOcorrencia : ERRO");
								Log.e(TAG, e1.getLocalizedMessage());
								ocorrenciaAux = null;
							}

							// se não encontrou resultados salva a ocorrencia do
							// servidor mesmo sem o GEO
							if (null == ocorrenciaAux) {
								ocorrenciaHelper.insert(ocorrenciaServer);
								listOcorrenciaNovas.add(ocorrenciaServer);

								// Encontrou ocorrencia com GEO, atualziar na base
								// quente
							} else {
								ocorrenciaHelper.insert(ocorrenciaAux);
								listOcorrenciaNovas.add(ocorrenciaAux);

								// tenta inserir o GEO da nova ocorrencia no
								// servidor
								try {
									Log.d(TAG, "Ocorrencia sem GEO, atualziando servidor");
									insertGeoOcorrenciaServer(ocorrenciaServer);
								} catch (Exception e) {
									// e.printStackTrace();
									Log.e(TAG,
											"insertGeoOcorrenciaServer: ERRO");
									Log.e(TAG, e.getLocalizedMessage());
								}
							}
							// ocorrecia nova com GEO do servidor. Inserir na
							// base local e notificar
						} else {
							ocorrenciaHelper.insert(ocorrenciaServer);
							listOcorrenciaNovas.add(ocorrenciaServer);
						}
					}
				} // for

				return listOcorrenciaNovas;
			}

			protected void onPostExecute(
					List<Ocorrencia> listOcorrenciaNotificar) {
				if (listOcorrenciaNotificar != null && !listOcorrenciaNotificar.isEmpty()) {
				
					// TODO verificar se as ocorrencias novas estão aptas a
					// serem notificadas de acordo com as preferencias
					for (Ocorrencia ocorrencia : listOcorrenciaNotificar) {

						Notificacao notificacao = new Notificacao();
						notificacao.notifyBasedOnPreferences(context,
								ocorrencia);

					}
				}
			}
		};
		asyncTask.execute("");

	}// do onReceive

	private Ocorrencia findGeoOcorrencia(Context context, Ocorrencia ocorrencia)
			throws IOException {

		LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = manager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// Location location =
		// manager.getLastKnownLocation(manager.getBestProvider(new Criteria(),
		// true));

		// se não tem Location ou o GeoCoder não esta instalado não faz nada
		if (null == location || !Geocoder.isPresent()) {
			return null;
		}

		String endereceCorreio = ocorrencia.getEnderecoFormatoCorreio();
		Geocoder geocoder = new Geocoder(context);
		List<Address> listAddress = geocoder.getFromLocationName(
				endereceCorreio, 1);

		// se não encontrar tenta sem o bairo
		if (null == listAddress || listAddress.size() < 1) {
			String enderecoWhichOutBairro = ocorrencia
					.getEnderecoWhichOutBairro();
			listAddress = geocoder.getFromLocationName(enderecoWhichOutBairro,
					1);
		}
		// se não encontrar sem o bairro nao faz nada
		if (null == listAddress || listAddress.size() < 1) {
			return null;
		}

		// encontrou algum endereço atribui a ocorrencia
		ocorrencia.setAddress(listAddress.get(0));
		ocorrencia.setLatitude(listAddress.get(0).getLatitude());
		ocorrencia.setLongitude(listAddress.get(0).getLongitude());
		return ocorrencia;
	}

	private List<Ocorrencia> getOcorrenciaFromServerByCidade(Context context,
			Cidade cidade, Ocorrencia lastOcorrencia) throws Exception {
		// TODO Buscar ocorrencias no servidor, verificar a possibilidade de
		// buscar todas
		ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
		parametros.add(new BasicNameValuePair(Globals.URL_PARAM_CIDADE, cidade
				.getNome()));
		// parametros.add(new BasicNameValuePair(Globals.URL_PARAM_LIST_IDS,
		// jsonListIds.toString()));

		// TODO para pegar somente as últimas ocorrecniass
		if (lastOcorrencia != null) {
			parametros.add(new BasicNameValuePair(
					Globals.URL_PARAM_OCORRENCIA_TS_MINIMO, lastOcorrencia
							.getTs()));
		}
		String jsonListOcorrenciaServer = ConexaoHttpClient.executaHttpPost(
				Globals.URL_OCORRENCIAS, parametros);
		Log.d(TAG, Globals.URL_OCORRENCIAS);
		Log.d(TAG, jsonListOcorrenciaServer);
		// para realizar o escape de HTML Entities
		// jsonListOcorrenciaServer =
		// Html.fromHtml(jsonListOcorrenciaServer).toString();
		return OcorrenciaJsonHelper
				.listOcorrenciaFromJson(jsonListOcorrenciaServer);
	}

	private Cidade getCidadePref(Context context) {
		// TODO Verificar a possibilidade de realizar um busca local ou buscar a
		// cidade com base no GEO atual
		Cidade cidade = null;
		String jsonCidade = ManageSP.getStringFromSharedPreference(context,
				Globals.PREF_FILE, Globals.PREF_CIDADE);
		if (!Valida.isEmpty(jsonCidade)) {
			cidade = CidadeJsonHelper.cidadeFromJson(jsonCidade);
		}
		return cidade;
	}

	// TODO realizar o insrt na base do bombeiro
	// TODO inserur no servidor as cooredenadas
	// TODO realizar insert no servidor
	private void insertGeoOcorrenciaServer(Ocorrencia ocorrencia)
			throws Exception {
		ArrayList<NameValuePair> parametro = new ArrayList<NameValuePair>();
		parametro.add(new BasicNameValuePair(Globals.URL_PARAM_OCORRENCIA,
				OcorrenciaJsonHelper.ocorrenciaToJson(ocorrencia)));
		String retornoPutLatLon = ConexaoHttpClient.executaHttpPost(
				Globals.URL_PUT_LAT_LON_OCORRENCIA, parametro);
		Log.d(TAG, "insertGeoOcorrenciaServer: " + retornoPutLatLon);
	}

}