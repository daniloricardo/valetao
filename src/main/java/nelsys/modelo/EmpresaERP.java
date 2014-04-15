package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpresaERP {

	@Id
	private String cdempresa;
	private String nmempresa;
	private String nmempresacurto;
	
	public void setNmempresacurto(String nmempresacurto) {
		this.nmempresacurto = nmempresacurto;
	}
	public String getNmempresacurto() {
		return nmempresacurto;
	}
	public String getCdempresa() {
		return cdempresa;
	}
	public void setCdempresa(String cdempresa) {
		this.cdempresa = cdempresa;
	}
	public String getNmempresa() {
		return nmempresa;
	}
	public void setNmempresa(String nmempresa) {
		this.nmempresa = nmempresa;
	}
	
	
}
