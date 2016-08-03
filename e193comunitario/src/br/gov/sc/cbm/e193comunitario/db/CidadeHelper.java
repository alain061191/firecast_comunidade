package br.gov.sc.cbm.e193comunitario.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.gov.sc.cbm.e193comunitario.model.Cidade;

public class CidadeHelper extends GenericTableHelper {

	public static final String ID = "id_cidade";
	public static final String NOME = "nm_cidade";

	public CidadeHelper(Context context) {
		DataBaseHelper dataBaseHelper = DataBaseHelper.getHelper(context);
		this.db = dataBaseHelper.getWritableDatabase();
		this.tableName = "cidades";
	}

	@Override
	public Cidade getById(String idName, int idValue) {
		Cidade cidade = null;
		Cursor c = this.db.query(this.getTableName(), null, idName + " = ?",
				new String[] { "" + idValue }, null, null, null);
		if (c != null) {
			cidade = new Cidade();
			c.moveToFirst();
			while (!c.isAfterLast()) {
				cidade = this.fillCidade(c);
				break;
			}
			c.close();
		}
		return cidade;

	}
	
	public Cidade getByNomeAproximado(String idName, String idValue) {
		Cidade cidade = null;
		String limit = "1"; 
		Cursor c = this.db.query(this.getTableName(), null, idName + " like ?",
				new String[] { "%"+idValue+"%" }, null, null, null, limit);
		if (c != null) {
			cidade = new Cidade();
			c.moveToFirst();
			while (!c.isAfterLast()) {
				cidade = this.fillCidade(c);
				break;
			}
			c.close();
		}
		return cidade;

	}

	public Cidade fillCidade(Cursor c) {
		Cidade cidade = new Cidade();
		cidade.setId(c.getInt(0));
		cidade.setNome(c.getString(1));
		return cidade;
	}

	@Override
	public List<Cidade> getAll() {
		List<Cidade> listCidades = null;
		Cidade cidade = null;
		Cursor cursor = this.db.query(this.getTableName(), null, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			listCidades = new ArrayList<Cidade>();
			while (!cursor.isAfterLast()) {
				cidade = this.fillCidade(cursor);
				listCidades.add(cidade);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listCidades;
	}

}
