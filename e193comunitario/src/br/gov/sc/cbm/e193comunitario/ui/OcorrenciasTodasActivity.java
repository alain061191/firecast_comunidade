package br.gov.sc.cbm.e193comunitario.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.db.TipoEmergenciaHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.CidadeJsonHelper;
import br.gov.sc.cbm.e193comunitario.jsonhelper.OcorrenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Cidade;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
import br.gov.sc.cbm.e193comunitario.util.ManageSP;
import br.gov.sc.cbm.e193comunitario.util.Valida;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class OcorrenciasTodasActivity extends Activity implements
		OnItemClickListener {

	public static final String TAG = "OcorrenciasTodasActivity";

	// reserva o nome dos componentes
	private ListView lvOcorrencia;
	private TextView tvInfo;
	private List<Ocorrencia> listOcorrenciaAux = null;
//	private ArrayList<NameValuePair> parametros = null;
//	private Context context;
	private RequestQueue rq;
	Cidade cidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ocorrencias_todas);
		//context = this.getApplicationContext();

		// captura componentes da tela
		lvOcorrencia = (ListView) findViewById(R.id.lv_ocorrencia);
		tvInfo = (TextView) findViewById(R.id.tv_info);

		// seta prpriedades nos componentes
		lvOcorrencia.setOnItemClickListener(this);

		// Carrega os parametros
		String jsonCidade = ManageSP
				.getStringFromSharedPreference(this, Globals.PREF_FILE,
						Globals.PREF_CIDADE);

		if (!Valida.isEmpty(jsonCidade)) {
			cidade = CidadeJsonHelper.cidadeFromJson(jsonCidade);
			tvInfo.setText(R.string.tv_info_pesquisando);
			tvInfo.setVisibility(View.VISIBLE);
			// verifica qual busca deve ser realizada
			String auxKey = this.getIntent().getStringExtra(
					Globals.KEY_EXTRA_OCORRENCIA);
			if (!Valida.isEmpty(auxKey)) {
				if (auxKey.equalsIgnoreCase(Globals.KEY_OCORRENCIA_TODAS)) {
					buscaOcorrencia(cidade.getNome(), null);
				} else if (auxKey.equalsIgnoreCase(Globals.KEY_OCORRENCIA_TIPO)) {
					buscaTipo();
				}
			}// if auxKey

		} else {
			Log.e(TAG, "Erro na busca de ocorrencias do servidor");
			Log.e(TAG, "Parâmetro cidade não esta presente");
			tvInfo.setText(R.string.tv_info_erro_parametro_cidade_tipo_emergencia);
			tvInfo.setVisibility(View.VISIBLE);
		}
	}// on create

	@Override
	public void onStop() {
		super.onStop();
		rq.cancelAll(TAG);
	}

	// TODO portando para volley
	private void buscaOcorrencia(final String nomeCidade, final String idtipo) {

		rq = Volley.newRequestQueue(OcorrenciasTodasActivity.this);
		StringRequest request = new StringRequest(Method.POST,
				Globals.URL_OCORRENCIAS, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, response);
						if (!Valida.isEmpty(response)) {
							tvInfo.setVisibility(View.GONE);
							listOcorrenciaAux = OcorrenciaJsonHelper
									.listOcorrenciaFromJson(response);
							OcorrenciaAdapter ocorrenciaAdapter = new OcorrenciaAdapter(
									getApplicationContext(),
									R.layout.ocorrencia_info, listOcorrenciaAux);
							lvOcorrencia.setAdapter(ocorrenciaAdapter);
						} else {
							tvInfo.setText(R.string.tv_info_sem_resultado);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "onErrorResponse");
						Toast.makeText(OcorrenciasTodasActivity.this,
								"Error: " + error.getMessage(),
								Toast.LENGTH_LONG).show();
						tvInfo.setText(R.string.tv_info_erro_parametro_cidade_tipo_emergencia);

					}
				}) {
			@Override
			public Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put(Globals.URL_PARAM_CIDADE, nomeCidade);
				if (idtipo != null) {
					params.put(Globals.URL_PARAM_LIST_IDS, idtipo);
				}
				return (params);
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> header = new HashMap<String, String>();
				// header.put("apiKey",
				// "Essa e minha API KEY: sdvkjbsdjvkbskdv");
				return (header);
			}

			@Override
			public Priority getPriority() {
				return (Priority.NORMAL);
			}
		};

		request.setTag(TAG);
		rq.add(request);
	}// busca Todas

	private void buscaTipo() {
		TipoEmergenciaHelper tipoEmergenciaHelper = new TipoEmergenciaHelper(
				this);
		List<Integer> listIdsTipoEmergencia = tipoEmergenciaHelper
				.getOnlyIdsBySelected();
		Gson gson = new Gson();
		String jsonListIds = gson.toJson(listIdsTipoEmergencia);
		buscaOcorrencia(cidade.getNome(), jsonListIds);
	} // buscaTipo
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Ocorrencia auxOcorrencia = (Ocorrencia) parent
				.getItemAtPosition(position);
		String aux = OcorrenciaJsonHelper.ocorrenciaToJson(auxOcorrencia);
		Intent intent = new Intent(getApplicationContext(),
				DetalheOcorrenciaActivity.class);
		intent.putExtra(Globals.KEY_EXTRA_OCORRENCIA, aux);
		startActivity(intent);
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
	
	/*public class BuscaOcorrencias extends AsyncTask<String, String, String> {

	@Override
	protected void onPreExecute() {
		tvInfo.setText(R.string.tv_info_pesquisando);
		tvInfo.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(String result) {
		parametros = null;
		if (!Valida.isEmpty(result)) {
			tvInfo.setVisibility(View.GONE);
			listOcorrenciaAux = OcorrenciaJsonHelper
					.listOcorrenciaFromJson(result);
			OcorrenciaAdapter ocorrenciaAdapter = new OcorrenciaAdapter(
					getApplicationContext(), R.layout.ocorrencia_info,
					listOcorrenciaAux);
			lvOcorrencia.setAdapter(ocorrenciaAdapter);
		} else {
			tvInfo.setText(R.string.tv_info_sem_resultado);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String jsonListOcorrencia = "";
		try {
			if (parametros != null) {
				jsonListOcorrencia = ConexaoHttpClient.executaHttpPost(
						Globals.URL_OCORRENCIAS, parametros);
				jsonListOcorrencia = Html.fromHtml(jsonListOcorrencia)
						.toString();
				parametros = null;
				Log.d(TAG, jsonListOcorrencia);
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
}*/

}
