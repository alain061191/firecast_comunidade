package br.gov.sc.cbm.e193comunitario.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;

public class TipoEmergenciaHelper extends GenericTableHelper {
	
	public static final String TAG = "TipoEmergenciaHelper";
	
	public static final String ID_TP_EMERGENCIA = "id_tp_emergencia";
	public static final String NM_TP_EMERGENCIA = "nm_tp_emergencia";
	public static final String IS_SELECTED = "is_selected";
	
	public TipoEmergenciaHelper(Context context) {
		DataBaseHelper dataBaseHelper = DataBaseHelper.getHelper(context);
		this.db = dataBaseHelper.getWritableDatabase();
		this.tableName = "tp_emergencia";
	}

	@Override
	public TipoEmergencia getById(String idName, int idValue) {
		TipoEmergencia tipoEmergencia = null;
		Cursor c = this.db.query(this.getTableName(), null, idName + " = ?",
				new String[] { "" + idValue }, null, null, null);
		if (c != null) {
			tipoEmergencia = new TipoEmergencia();
			c.moveToFirst();
			while (!c.isAfterLast()) {
				tipoEmergencia = this.fillTipoEmergencia(c);
				break;
			}
			c.close();
		}
		return tipoEmergencia;

	}
	
	public TipoEmergencia fillTipoEmergencia(Cursor c) {
		boolean isSelected = true;
		TipoEmergencia tipoEmergencia = new TipoEmergencia();
		tipoEmergencia.setId(c.getInt(0));
		tipoEmergencia.setNome(c.getString(1));
		if (c.getInt(2) == 0) {
			isSelected = false;
		}
		tipoEmergencia.setSelected(isSelected);
		return tipoEmergencia;
	}
	@Override
	public List<TipoEmergencia> getAll() {
		List<TipoEmergencia> listEmergencias = null;
		TipoEmergencia tipoEmergencia = null;
		Cursor cursor = this.db.query(this.getTableName(), null, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			listEmergencias = new ArrayList<TipoEmergencia>();
			while (!cursor.isAfterLast()) {
				tipoEmergencia = this.fillTipoEmergencia(cursor);
				listEmergencias.add(tipoEmergencia);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listEmergencias;
	}
	
	public List<TipoEmergencia> getAllBySelected(boolean isSelected) {
		String isSelectedParam;
		if (isSelected) {
			isSelectedParam = "1";
		} else {
			isSelectedParam = "0";
		}
		List<TipoEmergencia> listEmergencias = null;
		TipoEmergencia tipoEmergencia = null;
		Cursor cursor = this.db.query(this.getTableName(), null, TipoEmergenciaHelper.IS_SELECTED+" = ?", new String[]{isSelectedParam}, null, null, TipoEmergenciaHelper.NM_TP_EMERGENCIA);
		if (cursor != null) {
			cursor.moveToFirst();
			listEmergencias = new ArrayList<TipoEmergencia>();
			while (!cursor.isAfterLast()) {
				tipoEmergencia = this.fillTipoEmergencia(cursor);
				listEmergencias.add(tipoEmergencia);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listEmergencias;
	}
	
	public List<Integer> getOnlyIdsBySelected(){
		List<TipoEmergencia> listTipoEmergencia = this.getAllBySelected(true);
		Iterator<TipoEmergencia> iterator = listTipoEmergencia.iterator();
		List<Integer> listIdsTipoEmergencia = new ArrayList<Integer>();
		while (iterator.hasNext()) {
			TipoEmergencia tipoEmergencia = iterator.next();
			listIdsTipoEmergencia.add(tipoEmergencia.getId());
		}
		return listIdsTipoEmergencia;
	}
	
	public void updateById(TipoEmergencia tipoEmergencia) {
		int is_selected = 0;
		if (tipoEmergencia.isSelected()) {
			is_selected = 1;
		}
		ContentValues values = new ContentValues();
		values.put(TipoEmergenciaHelper.ID_TP_EMERGENCIA, tipoEmergencia.getId());
		values.put(TipoEmergenciaHelper.NM_TP_EMERGENCIA, tipoEmergencia.getNome());
		values.put(TipoEmergenciaHelper.IS_SELECTED, is_selected);
		this.update(values, TipoEmergenciaHelper.ID_TP_EMERGENCIA+" = ?", new String[] { Integer.toString(tipoEmergencia.getId()) });
	}

}
