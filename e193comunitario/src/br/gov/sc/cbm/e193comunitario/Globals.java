package br.gov.sc.cbm.e193comunitario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Globals {

	// impede a instaciação
	private Globals() {
	}

	public static final String DATABASE_NAME = "e193comunitario.db";
	public static final int DATABASE_VERSION = 20151027;

	public static final String PREF_FILE = "PREF_FILE";

	public static final String PREF_NOME = "PREF_NOME";
	public static final String PREF_PW = "PREF_PW";

	public static final String PREF_CIDADE = "PREF_CIDADE";
	public static final String PREF_STATUS_MONITORAMENTO = "PREF_STATUS_MONITORAMENTO";
	public static final String PREF_INTERESSE_STATUS = "PREF_INTERESSES_STATUS";
	public static final String PREF_ABRANGENCIA_GPS = "PREF_ABRANGENCIA_GPS";

	public static final String PREF_OCORRENCIA_ALL_JSON = "PREF_OCORRENCIA_ALL_JSON";
	public static final String PREF_OCORRENCIA_ALL_GEO_JSON = "PREF_OCORRENCIA_ALL_GEO_JSON";
	public static final String PREF_COD_OCORRENCIA_NOTIFICAR = "PREF_COD_OCORRENCIA_NOTIFICAR";

	public static final String PREF_MY_LATITUDE = "PREF_MY_LATITUDE";
	public static final String PREF_MY_LONGITUDE = "PREF_MY_LONGITUDE";

	public static double myLatitude = -27;
	public static double myLongitude = -48;

	public static final String URL_DOMINIO = "http://aplicativosweb.cbm.sc.gov.br/e193comunitario/";
	
	
	public static final String URL_OCORRENCIAS = URL_DOMINIO + "listar_ocorrencia_aberta_by_servidor_json.php";
	
	public static final String URL_DETALHES_OCORRENCIA = "http://aplicativosweb.cbm.sc.gov.br/193/r.php";
	
	public static final String URL_PUT_LAT_LON_OCORRENCIA = URL_DOMINIO + "put_lat_lon_ocorrencia.php";

	public static final String URL_PARAM_LIST_IDS = "list_ids";
	public static final String URL_PARAM_OCORRENCIA_TS_MINIMO = "ts_minimo";
	public static final String URL_PARAM_OCORRENCIA_ID_MINIMO = "id_minimo";
	public static final String URL_PARAM_CIDADE = "cidade";
	public static final String URL_PARAM_ID = "id";
	public static final String URL_PARAM_OCORRENCIA = "ocorrencia";

	public static final String KEY_FIXA_CIDADE = "KEY_FIXA_CIDADE";
	public static final String KEY_SELECIONA_CIDADE = "KEY_SELECIONA_CIDADE";

	public static final String KEY_NOTIFICACAO_VIBRAR = "KEY_NOTIFICACAO_VIBRAR";
	public static final String KEY_NOTIFICACAO_SOM = "KEY_NOTIFICACAO_SOM";
	public static final String KEY_NOTIFICACAO_TOAST = "KEY_NOTIFICACAO_TOAST";
	public static final String KEY_NOTIFICACAO_NOTIFICAR = "KEY_NOTIFICACAO_NOTIFICAR";

	public static final String KEY_EXTRA_OCORRENCIA = "KEY_EXTRA_OCORRENCIA";

	public static final String KEY_OCORRENCIA_TODAS = "KEY_OCORRENCIA_TODAS";
	public static final String KEY_OCORRENCIA_TIPO = "KEY_OCORRENCIA_TIPO";
	public static final String KEY_OCORRENCIA_ABRANGENCIA = "KEY_OCORRENCIA_ABRANGENCIA";
	public static final String KEY_OCORRENCIA_ULTIMO_TS = "KEY_OCORRENCIA_ULTIMO_TS";

	public static final int MAX_ABRNGENCIA_GPS = 100;
	public static final int MIN_ABRNGENCIA_GPS = 1;

	public static final String TS_MINIMO = "2015-01-01 01:01:01.000000";

	public static final HashMap<String, String> MAP_URL_RADIO_BY_CITY_INTERNO = new HashMap<String, String>() {

		/**
		 * Map com as URL das Radios Online para uso em rede externa
		 */
		private static final long serialVersionUID = 1L;

		{
			put("Florianópolis", "http://gravacaofns.cbm.sc.gov.br:8000/radio");
			put("Balneário Camboriú",
					"http://gravacaobcu.cbm.sc.gov.br:8000/radio");
			put("Blumenau", "http://gravacaobnu.cbm.sc.gov.br:8000/radio");
			put("Brusque", "http://gravacaobqe.cbm.sc.gov.br:8000/radio");
			put("Curitibanos", "http://gravacaocbs.cbm.sc.gov.br:8000/radio");
			put("Chapecó", "http://gravacaocco.cbm.sc.gov.br:8000/radio");
			put("Canoinhas", "http://gravacaocni.cbm.sc.gov.br:8000/radio");
			put("Criciúma", "http://gravacaocua.cbm.sc.gov.br:8000/radio");
			put("Dionísio Cerqueira",
					"http://gravacaodcq.cbm.sc.gov.br:8000/radio");
			put("Itajaí", "http://gravacaoiai.cbm.sc.gov.br:8000/radio");
			put("Iporã do Oeste", "http://gravacaoipt.cbm.sc.gov.br:8000/radio");
			put("Itapiranga", "http://gravacaoita.cbm.sc.gov.br:8000/radio");
			put("Joaçaba", "http://gravacaojca.cbm.sc.gov.br:8000/radio");
			put("Jaguaruna Aeroporto",
					"http://gravacaojju.cbm.sc.gov.br:8000/radio");
			put("Lages", "http://gravacaolgs.cbm.sc.gov.br:8000/radio");
			put("Maravilha", "http://gravacaomvh.cbm.sc.gov.br:8000/radio");
			put("Palma Sola", "http://gravacaopmx.cbm.sc.gov.br:8000/radio");
			put("Porto União", "http://gravacaopun.cbm.sc.gov.br:8000/radio");
			put("Pinhalzinho", "http://gravacaopzo.cbm.sc.gov.br:8000/radio");
			put("Rio do Sul", "http://gravacaorsl.cbm.sc.gov.br:8000/radio");
			put("São Bento do Sul",
					"http://gravacaosbs.cbm.sc.gov.br:8000/radio");
			put("São Miguel do Oeste",
					"http://gravacaosge.cbm.sc.gov.br:8000/radio");
			put("São Lourenço do Oeste",
					"http://gravacaosnx.cbm.sc.gov.br:8000/radio");
			put("Timbó", "http://gravacaotio.cbm.sc.gov.br:8000/radio");
			put("Videira", "http://gravacaovii.cbm.sc.gov.br:8000/radio");
			put("Xanxerê", "http://gravacaoxxe.cbm.sc.gov.br:8000/radio");
		}
	};

	public static final Map<String, String> MAP_URL_RADIO_BY_CITY_EXTERNO = new HashMap<String, String>() {

		/**
		 * Map com as URL das Radios Online para uso em rede externa
		 */
		private static final long serialVersionUID = 1L;

		{
			put("Florianópolis", "http://radio.cbm.sc.gov.br:8000/radio");
			put("Balneario Camboriu", "http://radio.cbm.sc.gov.br:8028/radio");
			put("Blumenau", "http://radio.cbm.sc.gov.br:8001/radio");
			put("Iporã do Oeste", "http://radio.cbm.sc.gov.br:8059/radio");
			put("Canoinhas", "http://radio.cbm.sc.gov.br:8003/radio");
			put("Chapecó", "http://radio.cbm.sc.gov.br:8004/radio");
			put("Criciúma", "http://radio.cbm.sc.gov.br:8005/radio");
			put("Curitibanos", "http://radio.cbm.sc.gov.br:8006/radio");
			put("Dionisio Cerqueira", "http://radio.cbm.sc.gov.br:8007/radio");
			put("Brusque", "http://radio.cbm.sc.gov.br:8009/radio");
			put("Itajai", "http://radio.cbm.sc.gov.br:8013/radio");
			put("Itapiranga", "http://radio.cbm.sc.gov.br:8014/radio");
			put("Jaguaruna Aeroporto", "http://radio.cbm.sc.gov.br:8025/radio");
			put("Joaçaba", "http://radio.cbm.sc.gov.br:8011/radio");
			put("Lages", "http://radio.cbm.sc.gov.br:8015/radio");
			put("Maravilha", "http://radio.cbm.sc.gov.br:8017/radio");
			put("Palma Sola", "http://radio.cbm.sc.gov.br:8018/radio");
			put("Pinhalzinho", "http://radio.cbm.sc.gov.br:8019/radio");
			put("Porto União", "http://radio.cbm.sc.gov.br:8020/radio");
			put("Rio do Sul", "http://radio.cbm.sc.gov.br:8021/radio");
			put("São Bento do Sul", "http://radio.cbm.sc.gov.br:8022/radio");
			put("São Lourenço do Oeste",
					"http://radio.cbm.sc.gov.br:8023/radio");
			put("São Miguel do Oeste", "http://radio.cbm.sc.gov.br:8024/radio");
			put("Videira", "http://radio.cbm.sc.gov.br:8026/radio");
			put("Xanxerê", "http://radio.cbm.sc.gov.br:8027/radio");
		}
	};
	public static List<String> LIST_NOME_CIDADE_RADIO_ON_LINE = new ArrayList<String>() {
		/**
		 * Lista com as cidades que tem radio Online
		 */
		private static final long serialVersionUID = 1L;
		{
			add("Balneario Camboriu");
			add("Blumenau");
			add("Brusque");
			add("Canoinhas");
			add("Chapecó");
			add("Criciúma");
			add("Curitibanos");
			add("Dionisio Cerqueira");
			add("Florianópolis");
			add("Iporã do Oeste");
			add("Itajai");
			add("Itapiranga");
			add("Jaguaruna Aeroporto");
			add("Joaçaba");
			add("Lages");
			add("Maravilha");
			add("Palma Sola");
			add("Pinhalzinho");
			add("Porto União");
			add("Rio do Sul");
			add("São Bento do Sul");
			add("São Lourenço do Oeste");
			add("São Miguel do Oeste");
			add("Videira");
			add("Xanxerê");
		}
	};
}