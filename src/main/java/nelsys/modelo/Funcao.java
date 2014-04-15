package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Funcao {

	@Id
	private String idfuncao;
	private String cdchamada;
	private String nmfuncao;
	private String tpfuncao;
	public String getIdfuncao() {
		return idfuncao;
	}
	public void setIdfuncao(String idfuncao) {
		this.idfuncao = idfuncao;
	}
	public String getCdchamada() {
		return cdchamada;
	}
	public void setCdchamada(String cdchamada) {
		this.cdchamada = cdchamada;
	}
	public String getNmfuncao() {
		return nmfuncao;
	}
	public void setNmfuncao(String nmfuncao) {
		this.nmfuncao = nmfuncao;
	}
	public String getTpfuncao() {
		return tpfuncao;
	}
	public void setTpfuncao(String tpfuncao) {
		this.tpfuncao = tpfuncao;
	}
	
	
}
