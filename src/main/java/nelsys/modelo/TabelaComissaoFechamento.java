package nelsys.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Transient;


@Entity
@Table(name="Nsys_comissaofechamento")
public class TabelaComissaoFechamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfechamento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String datareferencia;
	private String idvendedor;
	private String idusuariofechamento;
	@Transient
	private String nmusuario;
	
	public void setNmusuario(String nmusuario) {
		this.nmusuario = nmusuario;
	}
	public String getNmusuario() {
		return nmusuario;
	}
	public void setIdusuariofechamento(String idusuariofechamento) {
		this.idusuariofechamento = idusuariofechamento;
	}
	public String getIdusuariofechamento() {
		return idusuariofechamento;
	}
	public void setDatareferencia(String datareferencia) {
		this.datareferencia = datareferencia;
	}
	public String getDatareferencia() {
		return datareferencia;
	}
	public void setIdvendedor(String idvendedor) {
		this.idvendedor = idvendedor;
	}
	public String getIdvendedor() {
		return idvendedor;
	}
	public void setIdfechamento(int idfechamento) {
		this.idfechamento = idfechamento;
	}
	public int getIdfechamento() {
		return idfechamento;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	

}
