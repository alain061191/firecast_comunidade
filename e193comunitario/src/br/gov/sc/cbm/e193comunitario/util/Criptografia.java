package br.gov.sc.cbm.e193comunitario.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Criptografia {

	/**
	 * 
	 * @param s
	 *            Se s = null o retorno ser� null
	 * @return retorna a string criptografada com MD5
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5(String s) throws NoSuchAlgorithmException {
		String retorno = null;
		if (s != null) {
			retorno = stringToMd5Hash(s);
		}
		return retorno;
	}

	/**
	 * 
	 * @param d
	 *            Se d = null o retorno ser� null
	 * @return retorna uma string criptografada com MD5
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5(Double d) throws NoSuchAlgorithmException {
		String retorno = null;
		if (d != null) {
			retorno = stringToMd5Hash(d.toString());
		}
		return retorno;
	}

	/**
	 * 
	 * @param i
	 *            Se i = null o retorno ser� null
	 * @return retorna uma string criptografada com MD5
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5(Integer i) throws NoSuchAlgorithmException {
		String retorno = null;
		if (i != null) {
			retorno = stringToMd5Hash(i.toString());
		}
		return retorno;
	}

	private static final String stringToMd5Hash(String s)
			throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(s.getBytes(), 0, s.length());

		// get md5 bytes
		byte p_md5Data[] = md.digest();

		// create a hex string
		String numLongID = new String("");
		for (int i = 0; i < p_md5Data.length; i++) {
			int b = (0xFF & p_md5Data[i]);
			// if it is a single digit, make sure it have 0 in front
			// (proper
			// padding)
			if (b <= 0xF) {
				numLongID += "0";
			}
			// add number to string
			numLongID += Integer.toHexString(b);
		}
		return numLongID;
	}
}
