package br.gov.sc.cbm.e193comunitario.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.model.Cidade;

/***** Adapter class extends with ArrayAdapter ******/
public class CidadeAdapter extends ArrayAdapter<String> {

	private ArrayList<Cidade> listCidade;
	LayoutInflater inflater;

	/************* CustomAdapter Constructor *****************/
	public CidadeAdapter(Context context, int viewResource, ArrayList listCidade) {
		super(context, viewResource, listCidade);
		this.listCidade = listCidade;
		/*********** Layout inflator to call external xml layout () **********************/
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	// This funtion called for each row ( Called data.size() times )
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		/********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
		View row = inflater.inflate(R.layout.cidade_info, parent, false);

		/***** Get each Model object from Arraylist ********/
		Cidade cidade = null;
		cidade = listCidade.get(position);

		TextView label = (TextView) row.findViewById(R.id.tv_cidade_info);
		TextView code = (TextView) row.findViewById(R.id.tv_cidade_code);

		if (position == 0) {
			// TODO Coorigir pois esta comendo o primeiro item da lista de
			// cidades
			// Default selected Spinner item
			label.setText("Qual a sua cidade?");
			code.setText("0");
		} else {
			// Set values for spinner each row
			label.setText(cidade.getNome());
			code.setText(Integer.toString(cidade.getId()));
		}

		return row;
	}
}