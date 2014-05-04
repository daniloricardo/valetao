package nelsys.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="nsys_tabelacomissao")
public class TabelaComissao {
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nrdocumento;
	private String dtemissao;
	private String cdempresa;
	private String nmpessoa;
	private String nmproduto;
	private Double qtitem;
	private Double vlitem;
	private Double vlcomissao;
	private String nmgrupo;
	private String funcao;
	private String idgrupo;
	private Double percentual;
	private String vendedor;
	private Double vladicional;
	private Double vlbonificacao;
	//dados tabelacomissao base de dados
	
	private String idvendedor;
	private String stliberado;
	private String idfechamento;
	private String historico;
	private String tipo;
	private String idlancamentodb;
	
	public void setIdlancamentodb(String idlancamentodb) {
		this.idlancamentodb = idlancamentodb;
	}
	public String getIdlancamentodb() {
		return idlancamentodb;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public String getHistorico() {
		return historico;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdvendedor() {
		return idvendedor;
	}
	public void setIdvendedor(String idvendedor) {
		this.idvendedor = idvendedor;
	}
	public String getStliberado() {
		return stliberado;
	}
	public void setStliberado(String stliberado) {
		this.stliberado = stliberado;
	}
	public String getIdfechamento() {
		return idfechamento;
	}
	public void setIdfechamento(String idfechamento) {
		this.idfechamento = idfechamento;
	}
	public String getNrdocumento() {
		return nrdocumento;
	}
	public void setNrdocumento(String nrdocumento) {
		this.nrdocumento = nrdocumento;
	}
	public String getDtemissao() {
		return dtemissao;
	}
	public void setDtemissao(String dtemissao) {
		this.dtemissao = dtemissao;
	}
	public String getCdempresa() {
		return cdempresa;
	}
	public void setCdempresa(String cdempresa) {
		this.cdempresa = cdempresa;
	}
	public String getNmpessoa() {
		return nmpessoa;
	}
	public void setNmpessoa(String nmpessoa) {
		this.nmpessoa = nmpessoa;
	}
	public String getNmproduto() {
		return nmproduto;
	}
	public void setNmproduto(String nmproduto) {
		this.nmproduto = nmproduto;
	}
	public Double getQtitem() {
		return qtitem;
	}
	public void setQtitem(Double qtitem) {
		this.qtitem = qtitem;
	}
	public Double getVlitem() {
		return vlitem;
	}
	public void setVlitem(Double vlitem) {
		this.vlitem = vlitem;
	}
	public Double getVlcomissao() {
		return vlcomissao;
	}
	public void setVlcomissao(Double vlcomissao) {
		this.vlcomissao = vlcomissao;
	}
	public String getNmgrupo() {
		return nmgrupo;
	}
	public void setNmgrupo(String nmgrupo) {
		this.nmgrupo = nmgrupo;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public String getIdgrupo() {
		return idgrupo;
	}
	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}
	public Double getPercentual() {
		return percentual;
	}
	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public Double getVladicional() {
		return vladicional;
	}
	public void setVladicional(Double vladicional) {
		this.vladicional = vladicional;
	}
	public Double getVlbonificacao() {
		return vlbonificacao;
	}
	public void setVlbonificacao(Double vlbonificacao) {
		this.vlbonificacao = vlbonificacao;
	}
	
	
}
