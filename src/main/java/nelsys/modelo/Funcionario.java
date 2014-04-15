package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Funcionario {

	@Id
	private String idpessoafuncionario;
	
	public void setIdpessoafuncionario(String idpessoafuncionario) {
		this.idpessoafuncionario = idpessoafuncionario;
	}
	public String getIdpessoafuncionario() {
		return idpessoafuncionario;
	}
}
