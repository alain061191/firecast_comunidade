package br.gov.sc.cbm.e193comunitario.util;



/**
 * 
 * @author alain
 */

public class Valida {

	/**
	 * 
	 * @param s
	 * @return
	 */
	private static boolean testeIsEmpty(String s) {
		if (!s.trim().equalsIgnoreCase("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null)
			return testeIsEmpty(s);
		else
			return true;
	}

	public static boolean isEmpty(Integer i) {
		if (i != null) {
			String s = Integer.toString(i);
			return testeIsEmpty(s);
		} else
			return true;
	}

	public static boolean isEmpty(Double d) {
		if (d != null) {
			String s = Double.toString(d);
			return testeIsEmpty(s);
		} else {
			return true;
		}
	}
	public static boolean isEmpty(Float d) {
		if (d != null) {
			String s = Float.toString(d);
			return testeIsEmpty(s);
		} else {
			return true;
		}
	}
}
