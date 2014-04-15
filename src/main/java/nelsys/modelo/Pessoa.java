package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pessoa {

	@Id
	private String idpessoa;
	private String nmpessoa;
	public String getIdpessoa() {
		return idpessoa;
	}
	public void setIdpessoa(String idpessoa) {
		this.idpessoa = idpessoa;
	}
	public String getNmpessoa() {
		return nmpessoa;
	}
	public void setNmpessoa(String nmpessoa) {
		this.nmpessoa = nmpessoa;
	}
	
	
	
}
