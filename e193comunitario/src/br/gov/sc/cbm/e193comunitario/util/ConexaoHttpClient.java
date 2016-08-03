package br.gov.sc.cbm.e193comunitario.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ConexaoHttpClient {
	public static final int HTTP_TIMEOUT = 30 * 1000;
	public static HttpClient httpClient;

	private static HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
		}
		return httpClient;
	}
	
	/*
	
	public static String executaHttpPost(String url, String jsonParam){
		String resultado = null;
		
		return resultado;
	}

	*/
	
	
	public static String executaHttpPost(String url,
			ArrayList<NameValuePair> parametroPost) throws Exception {
		BufferedReader bufferedReader = null;
		String resultado = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					parametroPost);
			httpPost.setEntity(formEntity);
			HttpResponse httpResponse = client.execute(httpPost);
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	public static String executaHttpGet(String url) throws Exception {
		BufferedReader bufferedReader = null;
		String resultado = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setURI(new URI(url));
			HttpResponse httpResponse = client.execute(httpGet);
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}
	public static String executaHttpGetJson(String url) throws Exception {
		BufferedReader bufferedReader = null;
		String resultado = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setURI(new URI(url));
			HttpResponse httpResponse = client.execute(httpGet);
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}
}
