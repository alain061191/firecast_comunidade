package br.gov.sc.cbm.e193comunitario.model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Location;
import br.gov.sc.cbm.e193comunitario.util.Valida;

public class Ocorrencia {

	private int id;
	private String ts;
	private String descricao;
	private String logradouro;
	private String complemento;
	private int numero;
	private String bairro;
	private String referencia;
	private Cidade cidade;
	private TipoEmergencia tipoEmergencia;
	private double latitude;
	private double longitude;
	private Address address;
	private List<Viatura> listViatura;

	public Ocorrencia() {
	}

	public Ocorrencia(int id, String ts, String descricao, String logradouro,
			String complemento, int numero, String bairro, String referencia,
			Cidade cidade, TipoEmergencia tipoEmergencia, double latitude,
			double longitude, Address address, List<Viatura> listViatura) {
		this.id = id;
		this.ts = ts;
		this.descricao = descricao;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.numero = numero;
		this.bairro = bairro;
		this.referencia = referencia;
		this.cidade = cidade;
		this.tipoEmergencia = tipoEmergencia;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.listViatura = listViatura;
	}

	public List<Viatura> getListViatura() {
		return listViatura;
	}

	public void setListViatura(List<Viatura> listViatura) {
		this.listViatura = listViatura;
	}

	/**
	 * 
	 * @return endereço no formato do correio: logradouro, numero, bairro,
	 *         cidade, uf
	 */
	public String getEnderecoFormatoCorreio() {
		StringBuilder aux = new StringBuilder();
		// logradouro
		if (!Valida.isEmpty(this.logradouro)) {
			aux.append(this.logradouro + ", ");
			// numero
			if (!Valida.isEmpty(this.numero) && this.numero != 0) {
				aux.append(this.numero + ", ");
				// bairro
				if (!Valida.isEmpty(this.bairro)) {
					aux.append(this.bairro + ", ");
					// cidade
					if (!Valida.isEmpty(this.cidade.getNome())) {
						aux.append(this.cidade.getNome() + ", ");
					}
					// sem bairro
				} else {
					// cidade
					if (!Valida.isEmpty(this.cidade.getNome())) {
						aux.append(this.cidade.getNome() + ", ");
					}
				}
				// sem numero
			} else {
				if (!Valida.isEmpty(this.bairro)) {
					aux.append(this.bairro + ", ");
					// cidade
					if (!Valida.isEmpty(this.cidade.getNome())) {
						aux.append(this.cidade.getNome() + ", ");
					}
					// sem bairro
				} else {
					// cidade
					if (!Valida.isEmpty(this.cidade.getNome())) {
						aux.append(this.cidade.getNome() + ", ");
					}
				}

			}
			// sem logradouro
		} else {
			if (!Valida.isEmpty(this.bairro)) {
				aux.append(this.bairro + ", ");
				// cidade
				if (!Valida.isEmpty(this.cidade.getNome())) {
					aux.append(this.cidade.getNome() + ", ");
				}
				// sem bairro
			} else {
				// cidade
				if (!Valida.isEmpty(this.cidade.getNome())) {
					aux.append(this.cidade.getNome() + ", ");
				}
			}

		}
		aux.append("SC");
		return aux.toString();
	}

	/**
	 * 
	 * @return endereço sem o bairo: logradouro, numero, cidade, uf
	 */
	public String getEnderecoWhichOutBairro() {
		StringBuilder aux = new StringBuilder();
		// logradouro
		if (!Valida.isEmpty(this.logradouro)) {
			aux.append(this.logradouro + ", ");
			// numero
			if (!Valida.isEmpty(this.numero) && this.numero != 0) {
				aux.append(this.numero + ", ");
				// cidade
				if (!Valida.isEmpty(this.cidade.getNome())) {
					aux.append(this.cidade.getNome() + ", ");
				}
				// sem numero
			} else {
				if (!Valida.isEmpty(this.cidade.getNome())) {
					aux.append(this.cidade.getNome() + ", ");
				}
			}
			// sem logradouro
		} else {
			// cidade
			if (!Valida.isEmpty(this.cidade.getNome())) {
				aux.append(this.cidade.getNome() + ", ");
			}

		}
		aux.append("SC");
		return aux.toString();
	}

	public String getDistanciaFormatada(double latitude, double longitude) {
		float distancia = this.getDistancia(latitude, longitude);
		String aux = null;
		if (distancia > 1000) {
			char decimalSeparator = ',';
			char perMilSeparator = '.';
			Locale locale = new Locale("pt", "BR");
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
			dfs.setDecimalSeparator(decimalSeparator);
			dfs.setPerMill(perMilSeparator);
			DecimalFormat df = new DecimalFormat("#.## km");
			aux = df.format(distancia/1000);
		} else {
			DecimalFormat df = new DecimalFormat("#,### m");
			aux = df.format(distancia);
		}
		return aux;
	}

	public String getDescriptionForHumanRead() {
		String aux = "";
		if (Valida.isEmpty(this.referencia)) {
			aux = "Ocorrência de " + this.tipoEmergencia.getNome() + " em "
					+ this.getEnderecoFormatoCorreio() + " Descrição: "
					+ this.descricao + ". Data: " + this.getTsFormatoBR() + ".";
		} else {
			aux = "Ocorrência de " + this.tipoEmergencia.getNome() + " em "
					+ this.getEnderecoFormatoCorreio() + "(Referência: "
					+ this.referencia + "). Descrição: " + this.descricao
					+ ". Data: " + this.getTsFormatoBR() + ".";
		}
		return aux.toString();
	}

	public String getTsFormatoBR() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

		String tsOcorrencia = this.getTs();
		Timestamp ts = Timestamp.valueOf(tsOcorrencia);

		Date data = new Date(ts.getTime());
		String dataFinal = sdf.format(data);

		return dataFinal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public TipoEmergencia getTipoEmergencia() {
		return tipoEmergencia;
	}

	public void setTipoEmergencia(TipoEmergencia tipoEmergencia) {
		this.tipoEmergencia = tipoEmergencia;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public float getDistancia(double latitude, double longitude) {
		if (0 == latitude || 0 == longitude || 
			latitude < -90 || latitude > 90 || 
			longitude < -180 || longitude > 180	||
			0 == this.latitude || 0 == this.longitude ||
			(latitude == this.latitude && longitude == this.longitude)) {
			return 0;
		}
		float[] distancia = new float[3];
		Location.distanceBetween(latitude, longitude, this.getLatitude(),
				this.getLongitude(), distancia);
		return distancia[0];
	}
}