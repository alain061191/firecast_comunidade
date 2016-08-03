package br.gov.sc.cbm.e193comunitario.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
		
		
/*
 * 
 * 
 * 
 * Não sei porque, mas esta classe deixa a tela esbranquiçada
 * Estou utilizando uma copia do tipo "classe autocontida"
 * 
 * 
 * 
 * 
 */

/***** Adapter class extends with ArrayAdapter ******/
public class OcorrenciaAdapter extends ArrayAdapter<Ocorrencia> {

	private ArrayList<Ocorrencia> listOcorrencia;
	private LayoutInflater inflater;

	/************* CustomAdapter Constructor *****************/
	public OcorrenciaAdapter(Context context, int textViewResourceId,
			ArrayList listOcorrencia) {
		super(context, textViewResourceId, listOcorrencia);
		this.listOcorrencia = listOcorrencia;
		/*********** Layout inflator to call external xml layout () **********************/
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private class ViewHolder {
		TextView tvDescricao, tvTitulo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Log.v("ConvertView", String.valueOf(position));

		
		/********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
		View row = inflater.inflate(R.layout.ocorrencia_info, parent, false);

		
		/***** Get each Model object from Arraylist ********/
		Ocorrencia ocorrencia = null;
		ocorrencia = listOcorrencia.get(position);
		
		TextView tvDescricao = (TextView) row.findViewById(R.id.tv_descricao);
		TextView tvTitulo = (TextView) row.findViewById(R.id.tv_titulo);
		
		tvTitulo.setText(ocorrencia.getTipoEmergencia().getNome());
		tvDescricao.setText(ocorrencia.getBairro() + ", "
				+ ocorrencia.getCidade().getNome());

		return row;

	}

}