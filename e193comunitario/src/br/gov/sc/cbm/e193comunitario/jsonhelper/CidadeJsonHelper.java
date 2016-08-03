package br.gov.sc.cbm.e193comunitario.jsonhelper;

import java.lang.reflect.Type;
import java.util.List;

import br.gov.sc.cbm.e193comunitario.model.Cidade;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class CidadeJsonHelper {

	public static String cidadeToJson(Cidade cidade) {
		String cidadeJson = null;
		Gson gson = new Gson();
		cidadeJson = gson.toJson(cidade);
		return cidadeJson;
	}

	public static Cidade cidadeFromJson(String cidadeJson) {
		Cidade cidade = null;
		Gson gson = new Gson();
		cidade = gson.fromJson(cidadeJson, Cidade.class);
		return cidade;
	}

	public static String listCidadeToJson(List<Cidade> listCidade) {
		String listCidadeJson = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<Cidade>>() {
		}.getType();
		listCidadeJson = gson.toJson(listCidade, type);
		return listCidadeJson;
	}

	public static List<Cidade> listCidadeFromJson(String listCidadeJson) {
		List<Cidade> listCidade = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<Cidade>>() {
		}.getType();
		listCidade = gson.fromJson(listCidadeJson, type);
		return listCidade;
	}
}
