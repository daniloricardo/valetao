package nelsys.modelo;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


//@Entity
//@Table(name="vw_Nsys_tabelacomissao",schema="dbo")
public class TabelaComissaoView {
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String iddocumentoitem;
	private String nrdocumento;
	private String iddocumento;
	private String dtoriginal;
	private String dtemissao;
	private String cdempresa;
	private String nmpessoa;
	private String nmproduto;
	private Double qtitem;
	private Double vlitem;
	private Double vlcomissao;
	private String idvendedor;
	@Transient
	private String nmvendedor;
	private String idgrupoproduto;
	private String nmgrupoproduto;
	private Double percentual;
	private String nmcampo;
	private Double vladicional;
	private Double vlbonificacao;
	private String stliberado;
	private String idfechamento;
	private String idlancamentoDB;
	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}
	public String getIddocumento() {
		return iddocumento;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setNmvendedor(String nmvendedor) {
		this.nmvendedor = nmvendedor;
	}
	public String getNmvendedor() {
		return nmvendedor;
	}
	public String getIddocumentoitem() {
		return iddocumentoitem;
	}
	public void setIddocumentoitem(String iddocumentoitem) {
		this.iddocumentoitem = iddocumentoitem;
	}
	public String getNrdocumento() {
		return nrdocumento;
	}
	public void setNrdocumento(String nrdocumento) {
		this.nrdocumento = nrdocumento;
	}
	public String getDtoriginal() {
		return dtoriginal;
	}
	public void setDtoriginal(String dtoriginal) {
		this.dtoriginal = dtoriginal;
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
	public String getIdvendedor() {
		return idvendedor;
	}
	public void setIdvendedor(String idvendedor) {
		this.idvendedor = idvendedor;
	}
	public String getIdgrupoproduto() {
		return idgrupoproduto;
	}
	public void setIdgrupoproduto(String idgrupoproduto) {
		this.idgrupoproduto = idgrupoproduto;
	}
	public String getNmgrupoproduto() {
		return nmgrupoproduto;
	}
	public void setNmgrupoproduto(String nmgrupoproduto) {
		this.nmgrupoproduto = nmgrupoproduto;
	}
	public Double getPercentual() {
		return percentual;
	}
	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}
	public String getNmcampo() {
		return nmcampo;
	}
	public void setNmcampo(String nmcampo) {
		this.nmcampo = nmcampo;
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
	public String getIdlancamentoDB() {
		return idlancamentoDB;
	}
	public void setIdlancamentoDB(String idlancamentoDB) {
		this.idlancamentoDB = idlancamentoDB;
	}
	
	
	
	
}