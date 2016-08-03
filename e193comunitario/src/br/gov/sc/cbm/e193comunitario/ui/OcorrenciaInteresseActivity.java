package br.gov.sc.cbm.e193comunitario.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.jsonhelper.CidadeJsonHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.OcorrenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.TipoEmergenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Cidade;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;
import br.gov.sc.cbm.e193comunitario.util.ConexaoHttpClient;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class OcorrenciaInteresseActivity extends Activity {
	public static final String TAG = "OcorrenciaInteresseActivity";
	StringBuffer aux = new StringBuffer();

	// reserva o nome dos componentes
	ListView lvOcorrencia;
	TextView tvInfo;
	List<Ocorrencia> listOcorrenciaAux = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ocorrencias_todas);

		// captura componentes da tela
		lvOcorrencia = (ListView) findViewById(R.id.lv_ocorrencia);
		tvInfo = (TextView) findViewById(R.id.tv_info);

		// seta prpriedades nos componentes

		// busca ocorrencias

		String jsonListTipoEmergencia = ManageSP
				.getStringFromSharedPreference(this, Globals.PREF_FILE,
						Globals.PREF_INTERESSE_STATUS);
		if (!Valida.isEmpty(jsonListTipoEmergencia)) {
			List<TipoEmergencia> listTipoEmergencia = TipoEmergenciaJsonHelper.listTipoEmergenciaFromJson(jsonListTipoEmergencia);
			ArrayList<String> auxIds = new ArrayList<String>();
			for (TipoEmergencia tipoEmergencia : listTipoEmergencia) {
				if (tipoEmergencia.isSelected()) {
					auxIds.add(Integer.toString(tipoEmergencia.getId()));
					aux.append(Integer.toString(tipoEmergencia.getId()) + ",");
				}
			}
			
			BuscaTodasOcorrencias busca = new BuscaTodasOcorrencias();
			busca.execute(aux.toString());

		}
			 


	}

	private class OcorrenciaAdapter extends ArrayAdapter<Ocorrencia> {

		private List<Ocorrencia> listOcorrencia;

		public OcorrenciaAdapter(Context context, int textViewResourceId,
				List<Ocorrencia> listOcorrencia) {
			super(context, textViewResourceId, listOcorrencia);
			this.listOcorrencia = listOcorrencia;
		}

		private class ViewHolder {
			TextView tvDescricao, tvTitulo;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(R.layout.ocorrencia_info,
						null);

				holder = new ViewHolder();
				holder.tvDescricao = (TextView) convertView
						.findViewById(R.id.tv_descricao);
				holder.tvTitulo = (TextView) convertView
						.findViewById(R.id.tv_titulo);

				convertView.setTag(holder);

				/*
				 * holder.tvTitulo .setOnClickListener(new
				 * View.OnClickListener() { public void onClick(View v) { //TODO
				 * mostrar detalhes da ocorrencia em um DIALOG } });
				 */

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Ocorrencia ocorrencia = listOcorrencia.get(position);
			holder.tvTitulo.setText(ocorrencia.getTipoEmergencia().getNome());
			holder.tvDescricao.setText(ocorrencia.getBairro() + ", "
					+ ocorrencia.getCidade().getNome());

			return convertView;

		}

	}

	public class BuscaTodasOcorrencias extends
			AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// tvInfo.setText(tvInfo.getText().toString() + " ...");

		}

		@Override
		protected void onPostExecute(String result) {

			// TODO alterar com o valor atual de ocorrencias
			// Toast.makeText(getBaseContext(),
			// "on post execute",Toast.LENGTH_SHORT).show();

			if (!Valida.isEmpty(result)) {
				tvInfo.setVisibility(View.GONE);
				listOcorrenciaAux = OcorrenciaJsonHelper
						.listOcorrenciaFromJson(result);

				OcorrenciaAdapter ocorrenciaAdapter = new OcorrenciaAdapter(
						getApplicationContext(), R.layout.ocorrencia_info,
						listOcorrenciaAux);

				lvOcorrencia.setAdapter(ocorrenciaAdapter);
			} else {
				tvInfo.setText(R.string.tv_info_ocorrencias);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// Busca ocorrencias no servidor
			
			
			String jsonListOcorrencia = "";
			try {
				
				String jsonCidade = ManageSP.getStringFromSharedPreference(getApplicationContext(), Globals.PREF_FILE, Globals.PREF_CIDADE);
				if (!Valida.isEmpty(jsonCidade)) {
					Cidade cidade = CidadeJsonHelper.cidadeFromJson(jsonCidade);
					
					/*
					String urlAux = Globals.URL_OCORRENCIAS + "?" +
									Globals.URL_PARAM_CIDADE + "=" + cidade.getNome() + "&" + 
									Globals.URL_PARAM_LIST_IDS + "=" + aux.toString();
					
					jsonListOcorrencia = ConexaoHttpClient
							.executaHttpGet(urlAux);
					*/
					
					ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
					parametros.add(new BasicNameValuePair(Globals.URL_PARAM_CIDADE, cidade.getNome()));
					parametros.add(new BasicNameValuePair(Globals.URL_PARAM_LIST_IDS, aux.toString()));
										
					jsonListOcorrencia = ConexaoHttpClient.executaHttpPost(Globals.URL_OCORRENCIAS, parametros);
					
					//TODO remover o LOG
					Log.i(TAG,jsonListOcorrencia);
					jsonListOcorrencia = Html.fromHtml(jsonListOcorrencia)
							.toString();
					Log.i(TAG,jsonListOcorrencia);
					return jsonListOcorrencia;
					
				} else {
					Log.e(TAG, "Erro na busca de ocorrencias do servidor");
					Log.e(TAG, "Parâmetro cidade não esta presente");
					return null;
				}
				
			} catch (Exception e) {
				Log.e(TAG, "Erro na busca de ocorrencias do servidor");
				Log.e(TAG, e.toString());
				// e.printStackTrace();
				return null;
			}
		}

	}
}
