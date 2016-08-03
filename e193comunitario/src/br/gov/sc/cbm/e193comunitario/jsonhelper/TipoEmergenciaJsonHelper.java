package br.gov.sc.cbm.e193comunitario.jsonhelper;

import java.lang.reflect.Type;
import java.util.List;

import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class TipoEmergenciaJsonHelper {

	public static String tipoEmergenciaToJson(TipoEmergencia tipoEmergencia) {
		String tipoEmergenciaJson = null;
		Gson gson = new Gson();
		tipoEmergenciaJson = gson.toJson(tipoEmergencia);
		return tipoEmergenciaJson;
	}

	public static TipoEmergencia tipoEmergenciaFromJson(
			String tipoEmergenciaJson) {
		TipoEmergencia tipoEmergencia = null;
		Gson gson = new Gson();
		tipoEmergencia = gson
				.fromJson(tipoEmergenciaJson, TipoEmergencia.class);
		return tipoEmergencia;
	}

	public static String listTipoEmergenciaToJson(
			List<TipoEmergencia> listTipoEmergencia) {
		String listTipoEmergenciaJson = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<TipoEmergencia>>() {
		}.getType();
		listTipoEmergenciaJson = gson.toJson(listTipoEmergencia,type);
		return listTipoEmergenciaJson;
	}

	public static List<TipoEmergencia> listTipoEmergenciaFromJson(
			String listTipoEmergenciaJson) {
		List<TipoEmergencia> listTipoEmergencia = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<TipoEmergencia>>() {
		}.getType();
		listTipoEmergencia = gson.fromJson(listTipoEmergenciaJson, type);
		return listTipoEmergencia;
	}

}
