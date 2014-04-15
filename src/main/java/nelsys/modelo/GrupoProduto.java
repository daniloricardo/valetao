package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="grupoproduto")
public class GrupoProduto {

	@Id
	private String idgrupoproduto;
	private String cdchamada;
	private String nmgrupoproduto;
	private String tpclassificacao;
	public String getIdgrupoproduto() {
		return idgrupoproduto;
	}
	public void setIdgrupoproduto(String idgrupoproduto) {
		this.idgrupoproduto = idgrupoproduto;
	}
	public String getCdchamada() {
		return cdchamada;
	}
	public void setCdchamada(String cdchamada) {
		this.cdchamada = cdchamada;
	}
	public String getNmgrupoproduto() {
		return nmgrupoproduto;
	}
	public void setNmgrupoproduto(String nmgrupoproduto) {
		this.nmgrupoproduto = nmgrupoproduto;
	}
	public String getTpclassificacao() {
		return tpclassificacao;
	}
	public void setTpclassificacao(String tpclassificacao) {
		this.tpclassificacao = tpclassificacao;
	}
	
	
	
}
