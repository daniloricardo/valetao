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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FechamentoRepository {

	@Autowired
	EntityManager entityManager;
	@Autowired
	DataSource dataSource;
	
	public int fecha() throws SQLException{
		executaCreate();
		Date data = new Date();
		TabelaComissaoFechamento fech = new TabelaComissaoFechamento();
		fech.setData(data);
		entityManager.getTransaction().begin();
		entityManager.persist(fech);
		entityManager.getTransaction().commit();
		TabelaComissaoFechamento fechRecuperado =
				(TabelaComissaoFechamento) entityManager.createQuery("from TabelaComissaoFechamento t order by t.id desc ")
		.setMaxResults(1)
		.getSingleResult();
		return fechRecuperado.getId();
	}
	public void executa(List<TabelaComissao> lista ) throws SQLException{
		
		PreparedStatement pp;
		for(TabelaComissao t : lista ){
			String query = "insert into nsys_tabelacomissao " +
			" (idvendedor,nrdocumento,dtmovimento,empresa,nmvendedor,produto," +
			" grupo,qtitem,vlitem,vlcomissao,idgrupo, "+
			" percentual,cliente,vladicional,stliberado,idfechamento,tipo,historico,idlancamentoDB) "+
			" values "+ 
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pp = dataSource.getConnection().prepareStatement(query);
			pp.setString(1, t.getIdvendedor());
			pp.setString(2, t.getNrdocumento());
			pp.setString(3, t.getDtemissao());
			pp.setString(4, t.getCdempresa());
			pp.setString(5, t.getVendedor());
			pp.setString(6, t.getNmproduto());
			pp.setString(7, t.getNmgrupo());
			pp.setDouble(8, t.getQtitem());
			pp.setDouble(9, t.getVlitem());
			pp.setDouble(10, t.getVlcomissao()+t.getVladicional());
			pp.setString(11, t.getIdgrupo());
			pp.setDouble(12, t.getPercentual());
			pp.setString(13, t.getNmpessoa());
			pp.setDouble(14, t.getVladicional());
			pp.setString(15, "S");
			pp.setString(16, t.getIdfechamento());
			if(t.getVlitem()>0){
			pp.setString(17, "C");
			}
			else {
				pp.setString(17, "D");
			}
			pp.setString(18, t.getHistorico());
			pp.setString(19, t.getIdlancamentodb());
			pp.execute();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TabelaComissaoFechamento> lista(){
		return entityManager.createQuery("from TabelaComissaoFechamento t order by t.id desc").getResultList();
	}
	public List<TabelaComissao> listaFechados(String id) throws SQLException{
		executaCreate();
		String query = "select * from nsys_tabelacomissao where idfechamento = ?";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, id);
		ResultSet rs = pp.executeQuery();
		List<TabelaComissao> lista = new ArrayList<TabelaComissao>();
		TabelaComissao tabelaComissao;
		while(rs.next()){
			tabelaComissao = new TabelaComissao();
			tabelaComissao.setNrdocumento(rs.getString("nrdocumento"));
			tabelaComissao.setDtemissao(rs.getString("dtmovimento"));
			tabelaComissao.setCdempresa(rs.getString("empresa"));
			tabelaComissao.setNmpessoa(rs.getString("cliente"));
			tabelaComissao.setNmproduto(rs.getString("produto"));
			tabelaComissao.setQtitem(rs.getDouble("qtitem"));
			tabelaComissao.setVlitem(rs.getDouble("vlitem"));
			tabelaComissao.setVlcomissao(rs.getDouble("vlcomissao"));
			tabelaComissao.setNmgrupo(rs.getString("grupo"));
			tabelaComissao.setVendedor(rs.getString("nmvendedor"));
			tabelaComissao.setIdgrupo(rs.getString("idgrupo"));
			tabelaComissao.setPercentual(rs.getDouble("percentual"));
			tabelaComissao.setVladicional(rs.getDouble("VlAdicional"));
			tabelaComissao.setIdlancamentodb(rs.getString("idlancamentoDB"));
			lista.add(tabelaComissao);
		}
		return lista;
		
	}
	public void executaCreate() throws SQLException{
		String query = ""+
	"  IF object_id('nsys_tabelacomissaofechamento') IS NULL BEGIN   "+ 
	" create table nsys_tabelacomissaofechamento "+
	"( "+
		" id integer primary key identity, "+
	" data datetime "+
	" ) end";
	
	PreparedStatement pp = dataSource.getConnection()
			.prepareStatement(query);
	pp.execute();
	}
	public void deletafechamento(String id) throws SQLException{
		String query = ""+
	"  delete from nsys_tabelacomissaofechamento where id = ?   "+ 
	" delete from nsys_tabelacomissao where idfechamento = ? ";
	PreparedStatement pp = dataSource.getConnection()
			.prepareStatement(query);
	pp.setString(1, id);
	pp.setString(2, id);
	pp.execute();
	}
	
}
