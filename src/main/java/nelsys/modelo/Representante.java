package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Representante {
	
	@Id
	private String idpessoarepresentante;

	public String getIdpessoarepresentante() {
		return idpessoarepresentante;
	}

	public void setIdpessoarepresentante(String idpessoarepresentante) {
		this.idpessoarepresentante = idpessoarepresentante;
	}
	
	

}
