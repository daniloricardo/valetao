package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoFechamento;
import nelsys.modelo.TabelaComissaoView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FechamentoRepository {

	@Autowired
	EntityManager entityManager;
	@Autowired
	DataSource dataSource;
	
	
	public int fecha(String vendedor,String database,String idusuario) throws SQLException{
		//executaCreate();
		mataEntity();
		
		Date data = new Date();
		TabelaComissaoFechamento fech = new TabelaComissaoFechamento();
		fech.setData(data);
		fech.setDatareferencia(database);
		fech.setIdvendedor(vendedor);
		fech.setIdusuariofechamento(idusuario);
		System.out.println("Data: "+data+ " database :"+database+ " vendedor: "+vendedor);
		
		entityManager.getTransaction().begin();
		entityManager.persist(fech);
		entityManager.getTransaction().commit();
		
		TabelaComissaoFechamento fechRecuperado =
				(TabelaComissaoFechamento) entityManager.createQuery("from TabelaComissaoFechamento t order by t.id desc ")
		.setMaxResults(1)
		.getSingleResult();
		return fechRecuperado.getIdfechamento();
	}
	public void executa(List<TabelaComissaoView> lista2 ) throws SQLException{
		
		PreparedStatement pp = null;
		for(TabelaComissaoView t : lista2 ){
			String query = "insert into Nsys_ComissaoFechamentoItem " +
			" (idvendedor,iddocumento,iddocumentoitem,idproduto,dtmovimento," +
			" qtitem,vlitem,vlcomissao,idgrupo, "+
			" percentual,vladicional,stliberado,idfechamento,TpMovimento,dshistorico) "+
			" values "+ 
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pp = dataSource.getConnection().prepareStatement(query);
			pp.setString(1, t.getIdvendedor());
			pp.setString(2, t.getIddocumento());
			pp.setString(3, t.getIddocumentoitem());
			pp.setString(4, t.getIdproduto());
			pp.setString(5, t.getDtemissao());
			pp.setDouble(6, t.getQtitem());
			pp.setDouble(7, t.getVlitem());
			pp.setDouble(8, t.getVlcomissao());
			pp.setString(9, t.getIdgrupoproduto());
			pp.setDouble(10, t.getPercentual());
			pp.setDouble(11, t.getVladicional());
			pp.setString(12, "S");
			pp.setString(13, t.getIdfechamento());
			if(t.getVlitem()>0){
			pp.setString(14, "C");
			}
			else {
				pp.setString(14, "D");
			}
			pp.setString(15, t.getNmproduto());
			pp.execute();
			
		}
		pp.close();
		dataSource.getConnection().close();
	}
	public List<TabelaComissaoFechamento> listaporvendedor(String idpessoa){
		
		mataEntity();
		@SuppressWarnings("unchecked")
		List<TabelaComissaoFechamento> lista = entityManager.createQuery("from TabelaComissaoFechamento " +
				" t where t.idvendedor = :idpessoa  order by t.idfechamento desc")
					.setParameter("idpessoa", idpessoa)
					.getResultList();
			return lista; 
	}
	@SuppressWarnings("unchecked")
	public List<TabelaComissaoFechamento> listapordata(String data,String tipo){
		
		mataEntity();
		if(tipo.equals("dtfechamento")){
			data = converte(data);
			List<TabelaComissaoFechamento> lista = entityManager.createQuery("from TabelaComissaoFechamento t where Convert(date,t.data) = :data  order by t.idfechamento desc")
					.setParameter("data", data)
					.getResultList();
			return lista; 
		}
		else{
			
			List<TabelaComissaoFechamento> lista = entityManager.createQuery("from TabelaComissaoFechamento t where t.datareferencia = :data order by t.idfechamento desc")
					.setParameter("data", data)
					.getResultList();
			return lista; 
		}
		
	}
	public List<TabelaComissao> listaFechados(String id) throws SQLException{
		executaCreate();
		String query = 
				"select * from vw_Nsys_FechamentoComissao where idfechamento = ? order by dtemissao";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, id);
		ResultSet rs = pp.executeQuery();
		List<TabelaComissao> lista = new ArrayList<TabelaComissao>();
		TabelaComissao tabelaComissao;
		while(rs.next()){
			tabelaComissao = new TabelaComissao();
			tabelaComissao.setNrdocumento(rs.getString("nrdocumento"));
			tabelaComissao.setDtemissao(rs.getString("dtmovimento"));
			tabelaComissao.setNmpessoa(rs.getString("nmpessoa"));
			tabelaComissao.setNmproduto(rs.getString("nmproduto"));
			tabelaComissao.setVlcomissao(rs.getDouble("vlcomissao"));
			if(rs.getString("nrdocumento") == null ||  rs.getString("nrdocumento").equals("") ){
				tabelaComissao.setNmproduto(rs.getString("dshistorico"));
				if(tabelaComissao.getVlcomissao()>0){
					tabelaComissao.setNmpessoa("Crédito");
				}
				else{
					tabelaComissao.setNmpessoa("Débito");
				}
			}
			else {
				tabelaComissao.setNmproduto(rs.getString("nmproduto"));
			}
			
			tabelaComissao.setQtitem(rs.getDouble("qtitem"));
			tabelaComissao.setVlitem(rs.getDouble("vlitem"));
			
			tabelaComissao.setNmgrupo(rs.getString("nmgrupoproduto"));
			tabelaComissao.setPercentual(rs.getDouble("percentual"));
			tabelaComissao.setVladicional(rs.getDouble("VlAdicional"));
			lista.add(tabelaComissao);
		}
		rs.close();
		pp.close();
		dataSource.getConnection().close();
		return lista;
		
	}
	public void executaCreate() throws SQLException{
		String query = ""+
	"  IF object_id('nsys_tabelacomissaofechamento') IS NULL BEGIN   "+ 
	" create table nsys_tabelacomissaofechamento "+
	"( "+
		" id integer primary key identity, "+
	" data datetime,datareferencia nvarchar(20),nmvendedor nvarchar(255) "+
	" ) end";
	
	PreparedStatement pp = dataSource.getConnection()
			.prepareStatement(query);
	pp.execute();
	pp.close();
	dataSource.getConnection().close();
	}
	public void deletafechamento(String id) throws SQLException{
		String query = ""+
	"  delete from Nsys_ComissaoFechamento where idfechamento = ?   "+ 
	" delete from Nsys_ComissaoFechamentoItem where idfechamento = ? ";
	PreparedStatement pp = dataSource.getConnection()
			.prepareStatement(query);
	pp.setString(1, id);
	pp.setString(2, id);
	pp.execute();
	pp.close();
	dataSource.getConnection().close();
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
	public TabelaComissaoFechamento getFechamento(String id){
		mataEntity();
		TabelaComissaoFechamento t = entityManager.find(TabelaComissaoFechamento.class, new Integer(id));
		return t;
	}
	public void mataEntity(){
		entityManager.clear();
	}
}
