package br.gov.sc.cbm.e193comunitario.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;


public class TipoEmergenciaAdapter extends ArrayAdapter<TipoEmergencia> {

	private List<TipoEmergencia> listTipo;
	private LayoutInflater inflater;
	public TipoEmergenciaAdapter(Context context, int textViewResourceId,
			List<TipoEmergencia> listTipoEmergencia) {
		super(context, textViewResourceId, listTipoEmergencia);
		this.listTipo = listTipoEmergencia;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	private class ViewHolder {
		TextView tvCodigo;
		CheckBox cbInteresse;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Log.v("ConvertView", String.valueOf(position));

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tipo_emergencia_info, null);

			holder = new ViewHolder();
			holder.tvCodigo = (TextView) convertView
					.findViewById(R.id.tv_code);
			holder.cbInteresse = (CheckBox) convertView
					.findViewById(R.id.cb_interesse);
			convertView.setTag(holder);

			holder.cbInteresse
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							CheckBox cb = (CheckBox) v;
							TipoEmergencia tipo = (TipoEmergencia) cb
									.getTag();
							tipo.setSelected(cb.isChecked());
						}
					});

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TipoEmergencia tipoEmergencia = listTipo.get(position);
		holder.tvCodigo.setText(Integer.toString(tipoEmergencia.getId()));
		holder.cbInteresse.setText(tipoEmergencia.getNome());
		holder.cbInteresse.setChecked(tipoEmergencia.isSelected());
		holder.cbInteresse.setTag(tipoEmergencia);

		return convertView;

	}

	public List<TipoEmergencia> getListTipo() {
		return listTipo;
	}

	public void setListTipo(List<TipoEmergencia> listTipo) {
		this.listTipo = listTipo;
	}

}