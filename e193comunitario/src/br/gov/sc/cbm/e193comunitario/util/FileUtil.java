package br.gov.sc.cbm.e193comunitario.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe utilit�ria para manipula��o de arquivos
 * 
 * @author roberson
 * 
 */
public final class FileUtil {
	public final String getFileContent(String file) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					this.getClass().getResourceAsStream(file)));
			String str;
			while ((str = reader.readLine()) != null) {
				content.append(str);
			}
			reader.close();
		} catch (IOException e) {
		}

		return content.toString();
	}
}
