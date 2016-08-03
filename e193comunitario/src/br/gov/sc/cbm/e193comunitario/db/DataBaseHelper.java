package br.gov.sc.cbm.e193comunitario.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.util.FileUtil;

public final class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = Globals.DATABASE_NAME;
	private static final int DATABASE_VERSION = Globals.DATABASE_VERSION;
	
	private static final String TAG = "DataBaseHelper"; 

	private static DataBaseHelper instance;

	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null) {
			instance = new DataBaseHelper(context);
		}
		return instance;
	}

	/**
	 * Helper para auxiliar na manipula��o da base de dados
	 * 
	 * @author roberson
	 */
	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// lendo arquivo de script para cria��o da base de dados
		String scriptCreate = new FileUtil()
				.getFileContent("/br/gov/sc/cbm/e193comunitario/db/scripts/er_mobile_create.sql");
		String[] instrucoes = scriptCreate.split(";");
		for (int i = 0; i < instrucoes.length; i++) {
			if (instrucoes[i] != null && !instrucoes[i].trim().equals("")) {
				db.execSQL(instrucoes[i]);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String scriptDrop = new FileUtil()
				.getFileContent("/br/gov/sc/cbm/e193comunitario/db/scripts/er_mobile_drop.sql");
		db.execSQL(scriptDrop);
		this.onCreate(db);
	}
}