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
	
	public int fecha(String vendedor,String database) throws SQLException{
		executaCreate();
		Date data = new Date();
		TabelaComissaoFechamento fech = new TabelaComissaoFechamento();
		fech.setData(data);
		fech.setDatareferencia(database);
		fech.setNmvendedor(vendedor);
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
			" (idvendedor,iddocumento,iddocumentoitem,dtmovimento," +
			" qtitem,vlitem,vlcomissao,idgrupo, "+
			" percentual,vladicional,stliberado,idfechamento,TpTipo,historico) "+
			" values "+ 
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pp = dataSource.getConnection().prepareStatement(query);
			pp.setString(1, t.getIdvendedor());
			pp.setString(2, t.getIddocumento());
			pp.setString(3, t.getIddocumentoitem());
			pp.setString(4, t.getDtemissao());
			pp.setDouble(5, t.getQtitem());
			pp.setDouble(6, t.getVlitem());
			pp.setDouble(7, t.getVlcomissao());
			pp.setString(8, t.getIdgrupoproduto());
			pp.setDouble(9, t.getPercentual());
			pp.setDouble(10, t.getVladicional());
			pp.setString(11, "S");
			pp.setString(12, t.getIdfechamento());
			if(t.getVlitem()>0){
			pp.setString(13, "C");
			}
			else {
				pp.setString(13, "D");
			}
			pp.setString(14, t.getNmproduto());
			pp.execute();
			dataSource.getConnection().close();
		}
		pp.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<TabelaComissaoFechamento> lista(){
		return entityManager.createQuery("from TabelaComissaoFechamento t order by t.idfechamento desc").getResultList();
	}
	public List<TabelaComissao> listaFechados(String id) throws SQLException{
		executaCreate();
		String query = "select "+
				" convert(varchar,dtmovimento,103) as data, "+ 
" (select NrDocumento from Documento where IdDocumento = F.IdDocumento) as  NrDocumento, "+
" (select NmPessoa from Pessoa where IdPessoa =  "+
" (select IdPessoa from Documento where IdDocumento = F.IdDocumento)) as cliente, "+
" (select NmProduto from Produto where IdProduto =  "+
" (select IdProduto from DocumentoItem where IdDocumentoItem = F.IdDocumentoItem)) as produto, "+
" F.QtItem,F.VlItem,F.VlComissao, "+
" (select NmGrupoProduto from GrupoProduto where IdGrupoProduto = F.IdGrupo) as grupo, "+
" (select NmPessoa from Pessoa where IdPessoa = F.IdVendedor) as nmvendedor, "+
" F.Percentual, F.VlAdicional, F.historico, F.TpTipo "+
" from Nsys_ComissaoFechamentoItem F " +
				" where f.idfechamento = ?";
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		pp.setString(1, id);
		ResultSet rs = pp.executeQuery();
		List<TabelaComissao> lista = new ArrayList<TabelaComissao>();
		TabelaComissao tabelaComissao;
		while(rs.next()){
			tabelaComissao = new TabelaComissao();
			tabelaComissao.setNrdocumento(rs.getString("nrdocumento"));
			tabelaComissao.setDtemissao(rs.getString("data"));
			tabelaComissao.setNmpessoa(rs.getString("cliente"));
			tabelaComissao.setNmproduto(rs.getString("produto"));
			tabelaComissao.setVlcomissao(rs.getDouble("vlcomissao"));
			if(rs.getString("nrdocumento") == null ||  rs.getString("nrdocumento").equals("") ){
				tabelaComissao.setNmproduto(rs.getString("historico"));
				if(tabelaComissao.getVlcomissao()>0){
					tabelaComissao.setNmpessoa("Crédito");
				}
				else{
					tabelaComissao.setNmpessoa("Débito");
				}
			}
			else {
				tabelaComissao.setNmproduto(rs.getString("cliente"));
			}
			
			tabelaComissao.setQtitem(rs.getDouble("qtitem"));
			tabelaComissao.setVlitem(rs.getDouble("vlitem"));
			
			tabelaComissao.setNmgrupo(rs.getString("grupo"));
			tabelaComissao.setVendedor(rs.getString("nmvendedor"));
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
		return entityManager.find(TabelaComissaoFechamento.class, new Integer(id));
	}
}
