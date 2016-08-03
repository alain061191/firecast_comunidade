package br.gov.sc.cbm.e193comunitario.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ManageSP {

	public static void remove(Context context, String fileName,
			String key) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static void putInSharedPreferences(Context context, String fileName,
			String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void putInSharedPreferences(Context context, String fileName,
			String key, Integer value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void putInSharedPreferences(Context context, String fileName,
			String key, Float value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void putInSharedPreferences(Context context, String fileName,
			String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void putInSharedPreferences(Context context, String fileName,
			String key, Long value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static Integer getIntFromSharedPreference(Context context,
			String fileName, String key) {
		Integer retorno = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		retorno = preferences.getInt(key, 0);
		return retorno;
	}
	
	public static Float getFloatFromSharedPreference(Context context,
			String fileName, String key) {
		Float retorno = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		retorno = preferences.getFloat(key, 0);
		return retorno;
	}
	public static boolean getBooleanFromSharedPreference(Context context,
			String fileName, String key) {
		boolean retorno = false;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		retorno = preferences.getBoolean(key, false);
		return retorno;
	}
	public static Long getLongFromSharedPreference(Context context,
			String fileName, String key) {
		Long retorno = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		retorno = preferences.getLong(key, 0);
		return retorno;
	}
	public static String getStringFromSharedPreference(Context context,
			String fileName, String key) {
		String retorno = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		retorno = preferences.getString(key, "");
		return retorno;
	}
	
	public static Set<String> getStringSetFromSharedPreference(Context context,
			String fileName, String key) {
		Set<String> retorno = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_MULTI_PROCESS);
		retorno = preferences.getStringSet(key,null);
		return retorno;
	}
	
	public static void putInSharedPreferences(Context context, String fileName,
			String key, Set<String> value) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_MULTI_PROCESS);
		Editor editor = preferences.edit();
		editor.putStringSet(key, value);
		editor.commit();
	}

}
