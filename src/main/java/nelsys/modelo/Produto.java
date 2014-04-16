package nelsys.modelo;

public class Produto {

	private String idproduto;
	private String cdchamada;
	private String nmproduto;
	private String idgrupo;
	private Double vlbonus;
	
	public void setVlbonus(Double vlbonus) {
		this.vlbonus = vlbonus;
	}
	public Double getVlbonus() {
		return vlbonus;
	}
	public String getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(String idproduto) {
		this.idproduto = idproduto;
	}
	public String getCdchamada() {
		return cdchamada;
	}
	public void setCdchamada(String cdchamada) {
		this.cdchamada = cdchamada;
	}
	public String getNmproduto() {
		return nmproduto;
	}
	public void setNmproduto(String nmproduto) {
		this.nmproduto = nmproduto;
	}
	public String getIdgrupo() {
		return idgrupo;
	}
	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}
	
	
}
