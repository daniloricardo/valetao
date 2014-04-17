package nelsys.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;


@Entity
@Table(name="nsys_tabelacomissaofechamento")
public class TabelaComissaoFechamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String datareferencia;
	private String nmvendedor;
	
	public void setDatareferencia(String datareferencia) {
		this.datareferencia = datareferencia;
	}
	public String getDatareferencia() {
		return datareferencia;
	}
	public void setNmvendedor(String nmvendedor) {
		this.nmvendedor = nmvendedor;
	}
	public String getNmvendedor() {
		return nmvendedor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	

}
