package br.gov.sc.cbm.e193comunitario.db;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericTableHelper {
	protected SQLiteDatabase db;
	protected String tableName = "table_name";

	/**
	 * M�todo gen�rico para inserts
	 * 
	 * @param values
	 */
	public void insert(ContentValues values) {
		this.db.insert(this.getTableName(), null, values);
	}

	/**
	 * M�todo gen�rico para os updates
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void update(ContentValues values, String whereClause,
			String[] whereArgs) {
		this.db.update(this.getTableName(), values, whereClause, whereArgs);
	}

	/**
	 * M�todo para excluir todos os registros
	 */
	public void deleteAll() {
		this.db.delete(this.getTableName(), null, null);
	}

	/**
	 * Delete baseado em where/id
	 * 
	 * @param idName
	 * @param idValue
	 */
	public void delete(String whereClause, String[] whereArgs) {
		this.db.delete(this.getTableName(), whereClause, whereArgs);
	}

	/**
	 * Retorna uma linha baseado no id/codigo
	 * 
	 * @param idName
	 * @param idValue
	 * @return
	 */
	public abstract Object getById(String idName, int idValue);

	/**
	 * Retorna uma lista de registros
	 * 
	 * @return List
	 */
	public abstract List<?> getAll();

	/**
	 * Retorna o nome da tabela
	 * 
	 * @return
	 */
	protected String getTableName() {
		return this.tableName;
	}

	/**
	 * Metodo para fechamento da conexao, caso esteja aberta
	 */
	public void close() {
		if (this.db != null && this.db.isOpen()) {
			this.db.close();
		}
	}
}