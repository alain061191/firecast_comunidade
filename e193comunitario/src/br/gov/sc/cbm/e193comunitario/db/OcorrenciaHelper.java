package br.gov.sc.cbm.e193comunitario.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;

public class OcorrenciaHelper extends GenericTableHelper {

	public static final String TAG = "OcorrenciaHelper";
	private Context context;
	public static final String ID = "id_ocorrencia";
	public static final String TS = "ts_ocorrencia";
	private static final String ID_CIDADE = "id_cidade";
	private static final String ID_TIPO_EMERGENCIA = "id_tp_emergencia";
	private static final String DESCRICAO = "descricao";
	private static final String LOGRADOURO = "logradouro";
	private static final String NUMERO = "numero";
	private static final String BAIRRO = "bairro";
	private static final String LONGITUDE = "longitude";
	private static final String REFERENCIA = "referencia";
	private static final String LATITUDE = "latitude";

	public OcorrenciaHelper(Context context) {
		DataBaseHelper dataBaseHelper = DataBaseHelper.getHelper(context);
		this.db = dataBaseHelper.getWritableDatabase();
		this.tableName = "ocorrencias";
		this.context = context;
	}

	@Override
	public Ocorrencia getById(String idName, int idValue) {

		Ocorrencia ocorrencia = null;
		Cursor c = this.db.query(this.getTableName(), null, idName + " = ?",
				new String[] { "" + idValue }, null, null, null);
		if (c != null) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				ocorrencia = new Ocorrencia();
				ocorrencia = this.fillOcorrencia(c);
				break;
			}
			c.close();
		}
		return ocorrencia;
	}

	@Override
	public List<Ocorrencia> getAll() {
		List<Ocorrencia> listOcorrencias = null;
		Ocorrencia ocorrencia = null;
		Cursor cursor = this.db.query(this.getTableName(), null, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			listOcorrencias = new ArrayList<Ocorrencia>();
			while (!cursor.isAfterLast()) {
				ocorrencia = this.fillOcorrencia(cursor);
				listOcorrencias.add(ocorrencia);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listOcorrencias;
	}

	public Ocorrencia getByLastTs() {
		Ocorrencia ocorrencia = null;
		Cursor c = this.db.rawQuery("select * from " + this.getTableName()
				+ " as f where (select max(d." + OcorrenciaHelper.TS
				+ ") from " + this.getTableName() + " as d ) = f."
				+ OcorrenciaHelper.TS + "", null);
		if (c != null) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				ocorrencia = new Ocorrencia();
				ocorrencia = this.fillOcorrencia(c);
				break;
			}
			c.close();
		}
		return ocorrencia;
	}

	public void insert(Ocorrencia ocorrencia) {
		ContentValues values = new ContentValues();
		values.put(OcorrenciaHelper.ID, ocorrencia.getId());
		values.put(OcorrenciaHelper.ID_CIDADE, ocorrencia.getCidade().getId());
		values.put(OcorrenciaHelper.ID_TIPO_EMERGENCIA, ocorrencia
				.getTipoEmergencia().getId());
		values.put(OcorrenciaHelper.TS, ocorrencia.getTs());
		values.put(OcorrenciaHelper.DESCRICAO, ocorrencia.getDescricao());
		values.put(OcorrenciaHelper.LOGRADOURO, ocorrencia.getLogradouro());
		values.put(OcorrenciaHelper.NUMERO, ocorrencia.getNumero());
		values.put(OcorrenciaHelper.BAIRRO, ocorrencia.getBairro());
		values.put(OcorrenciaHelper.REFERENCIA, ocorrencia.getReferencia());
		values.put(OcorrenciaHelper.LATITUDE, ocorrencia.getLatitude());
		values.put(OcorrenciaHelper.LONGITUDE, ocorrencia.getLongitude());
		this.insert(values);
	}

	public Ocorrencia fillOcorrencia(Cursor c) {
		TipoEmergenciaHelper tipoEmergenciaHelper = new TipoEmergenciaHelper(
				this.context);
		CidadeHelper cidadeHelper = new CidadeHelper(this.context);
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setId(c.getInt(0));
		ocorrencia
				.setCidade(cidadeHelper.getById(CidadeHelper.ID, c.getInt(1)));
		ocorrencia.setTipoEmergencia(tipoEmergenciaHelper.getById(
				TipoEmergenciaHelper.ID_TP_EMERGENCIA, c.getInt(2)));
		ocorrencia.setTs(c.getString(3));
		ocorrencia.setDescricao(c.getString(4));
		ocorrencia.setLogradouro(c.getString(5));
		ocorrencia.setNumero(c.getInt(6));
		ocorrencia.setBairro(c.getString(7));
		ocorrencia.setReferencia(c.getString(8));
		return ocorrencia;
	}

}
