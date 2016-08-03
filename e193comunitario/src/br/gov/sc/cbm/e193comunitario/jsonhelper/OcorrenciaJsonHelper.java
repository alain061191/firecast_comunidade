package br.gov.sc.cbm.e193comunitario.jsonhelper;

import java.lang.reflect.Type;
import java.util.List;

import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class OcorrenciaJsonHelper {
	
	public static String ocorrenciaToJson(Ocorrencia ocorrencia){
		String ocorrenciaJson = null;
		Gson gson = new Gson();
		ocorrenciaJson = gson.toJson(ocorrencia);
		return ocorrenciaJson;
	}
	public static Ocorrencia ocorrenciaFromJson(String ocorrenciaJson) {
		Ocorrencia ocorrencia = null;
		Gson gson = new Gson();
		ocorrencia = gson.fromJson(ocorrenciaJson, Ocorrencia.class);
		return ocorrencia;
	}
	public static String listOcorrenciaToJson(List<Ocorrencia> listOcorrencia) {
		String listOcorrenciaJson = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<Ocorrencia>>(){}.getType();
		listOcorrenciaJson = gson.toJson(listOcorrencia, type);
		return listOcorrenciaJson;
	}
	public static List<Ocorrencia> listOcorrenciaFromJson(String listOcorrenciaJson) {
		List<Ocorrencia> listOcorrencia = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<Ocorrencia>>(){}.getType();
		listOcorrencia = gson.fromJson(listOcorrenciaJson, type);
		return listOcorrencia;
	}

}
