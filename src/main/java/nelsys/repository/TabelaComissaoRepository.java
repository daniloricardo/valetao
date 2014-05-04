package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import nelsys.modelo.TabelaComissao;
import nelsys.modelo.TabelaComissaoView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TabelaComissaoRepository {

	@Autowired
	DataSource dataSource;
	@Autowired
	ConfiguracaoRepository configuracaoRepository;
	@Autowired
	EntityManager entityManager;
	
	public void executaCreateLancamento() throws SQLException{
		String create = ""+
				" IF object_id('Nsys_LancamentoDebitoCredito') IS NULL BEGIN   "+ 
				" create table Nsys_LancamentoDebitoCredito( "+
				" id integer primary key identity, "+
				" idvendedor nvarchar(255) not null, "+
				" nrdocumento nvarchar(255), "+
				" dtmovimento datetime not null, "+
				" empresa integer not null, "+
				" nmvendedor nvarchar(255), "+
				" produto nvarchar(255), "+
				" grupo nvarchar(255), "+
				" qtitem float, "+
				" vlitem float, "+
				" vlcomissao float, "+
				" idgrupo nvarchar(255), "+
				" percentual float, "+
				" cliente nvarchar(255), "+
				" vladicional float, "+
				" vlbonificacao float, "+
				" stliberado nvarchar(1), "+
				" idfechamento integer, "+
				" tipo nvarchar(1), "+
				" historico nvarchar(1000)," +
				" idlancamentoDB integer "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
		pp.close();
		dataSource.getConnection().close();
	}
	public void executaCreate() throws SQLException{
		String create = ""+
				" IF object_id('Nsys_TabelaComissao') IS NULL BEGIN   "+ 
				" create table Nsys_TabelaComissao( "+
				" id integer primary key identity, "+
				" idvendedor nvarchar(255) not null, "+
				" nrdocumento nvarchar(255), "+
				" dtmovimento datetime not null, "+
				" empresa integer not null, "+
				" nmvendedor nvarchar(255), "+
				" produto nvarchar(255), "+
				" grupo nvarchar(255), "+
				" qtitem float, "+
				" vlitem float, "+
				" vlcomissao float, "+
				" idgrupo nvarchar(255), "+
				" percentual float, "+
				" cliente nvarchar(255), "+
				" vladicional float, "+
				" vlbonificacao float, "+
				" stliberado nvarchar(1), "+
				" idfechamento integer, "+
				" tipo nvarchar(1), "+
				" historico nvarchar(1000)," +
				" idlancamentoDB nvarchar(10) "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
		pp.close();
		dataSource.getConnection().close();
	}
	public void executaCreateFechamentoItem() throws SQLException{
		String create = ""+
				" IF object_id('Nsys_ComissaoFechamentoItem') IS NULL BEGIN   "+ 
				" CREATE TABLE [dbo].[Nsys_ComissaoFechamentoItem]( "+
				"  [Id] [int] IDENTITY(1,1) NOT NULL, "+
       " [IdDocumento] [nvarchar](10) NOT NULL, "+
       " [IdDocumentoItem] [nvarchar](10) NOT NULL, "+
       " [IdVendedor] [nvarchar](10) NOT NULL, "+
       " [DtMovimento] [datetime] NOT NULL, "+
       " [QtItem] [float] NULL, "+
       " [VlItem] [float] NULL, "+
       " [VlComissao] [float] NULL, "+
       " [Percentual] [float] NULL, "+
       " [VlAdicional] [float] NULL, "+
       " [VlBonificacao] [float] NULL, "+
       " [IdGrupo] [nvarchar](10) NULL, "+
       " [StLiberado] [nvarchar](1) NULL, "+
       " [TpTipo] [nvarchar](1) NULL, "+
       " [Historico] [nvarchar](1000) NULL, "+
       " [Idfechamento] [int] not NULL, "+
       " [TpMovimento] [nvarchar](255) NULL "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
		pp.close();
		dataSource.getConnection().close();
	}
	public void executaCreateFechamento() throws SQLException{
		String create = ""+
				" IF object_id('Nsys_ComissaoFechamento') IS NULL BEGIN   "+ 
				" CREATE TABLE [dbo].[Nsys_ComissaoFechamento]( "+
				" [IdFechamento] [int] IDENTITY(1,1) NOT NULL, "+
       " [Data] [datetime] NULL, "+
       " [DataReferencia] [nvarchar](20) NULL, "+
       " [NmVendedor] [nvarchar](255) NULL, "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
		pp.close();
		dataSource.getConnection().close();
	}
	
	public List<TabelaComissao> listaporvendedor(String idpessoa,String data,String nmfuncao) throws SQLException{
	
		//executaCreate();
		//executaCreateLancamento();
		String datacorte = configuracaoRepository.configuracaoPorNmconfiguracao("datacorte").getVlconfiguracao();
		String query = ""+
				" With RegraComissao as  "+
				" ( Select NmCampo, IdGrupoProduto, NmGrupoProduto, Percentual, VlAdicional, VlBonificacao from Nsys_RegraComissao "+ 
				" group by NmCampo, IdGrupoProduto, NmGrupoProduto, Percentual, VlAdicional, VlBonificacao ) "+ 
				" Select  "+
				" D.NrDocumento "+
				" , Convert(Varchar,D.DtEmissao,103) as DtEmissao "+
				" , DtEmissao  as DtOriginal "+
				" , LE.CdEmpresa "+
				" , Cli.NmPessoa "+
				" , Prod.NmProduto "+
				" , DI.QtItem "+
				"  , CASE WHEN O.TpOperacao = 'D' then  (-DI.VlItem) else DI.VlItem end   as VlItem  "+
				" , (DI.VlItem * RC.Percentual/100) +isnull(( RC.VlAdicional + RC.VlBonificacao),0)as VlComissao "+
				" , P.IdPessoa as Vendedor "+
				" , GP.IdGrupoProduto "+
				" , GP.NmGrupoProduto "+
				" , RC.Percentual "+
				" , isnull(( select BP.vlbonus  from Nsys_bonusproduto BP "+ 
				" where BP.idfuncao = ( select top 1 idfuncao from Funcao where NmFuncao = RC.NmCampo) "+
				" and BP.idgrupo = GP.IdGrupoProduto "+
				" and BP.idproduto = Prod.IdProduto),0) "+
				"  as VlAdicional "+
				" , RC.VlBonificacao "+
				" , 'N' as stliberado "+
				" , null as idfechamento "+
				"  , '' as idlancamentoDB "+
				" from "+
				" Documento D  "+
				" INNER JOIN LoteEstoque LE ON (D.IdLoteEstoque = LE.IdLoteEstoque) "+
				" INNER JOIN Operacao  O ON (D.IdOperacao = O.IdOperacao)  "+
				" INNER JOIN DocumentoItem DI ON (D.IdDocumento = DI.IdDocumento) "+
				"  INNER JOIN Produto  Prod ON (DI.IdProduto = Prod.IdProduto) "+
				" Inner join GrupoProduto  GP on (GP.IdGrupoProduto = Prod.IdGrupoProduto) "+
				" INNER JOIN DocumentoItemRepasse DIR ON (DI.IdDocumentoItem = DIR.IdDocumentoItem) "+
				" INNER JOIN Pessoa  Cli ON (D.IdPessoa = Cli.IdPessoa)  "+
				" JOIN RegraComissao RC on (RC.IdGrupoProduto = GP.IdGrupoProduto) "+
				" LEFT OUTER JOIN Pessoa  P ON (DIR.IdPessoa = P.IdPessoa) "+
				" LEFT OUTER JOIN PessoaComplementar PC ON (P.IdPessoa = PC.IdPessoa) "+
				" Where DIR.IdPessoa = ?  and D.DtEmissao between ? and ? and RC.NmCampo = 	? "+
				"  and O.TpOperacao = 'V' and D.StDocumentoCancelado = 'N' "+
				" and D.NrDocumento not in "+
				" ( select isnull(NrDocumento,0) from nsys_tabelacomissao where idvendedor = ? and DtEmissao <= ?) "+
				" group by "+
				"  RC.NmCampo "+
				" ,D.NrDocumento "+
				" , D.DtEmissao "+
				" , LE.CdEmpresa "+
				" , Cli.NmPessoa "+
				" , Prod.IdProduto "+
				" , Prod.NmProduto "+
				" , DI.QtItem "+
				" , DI.VlItem "+
				" , GP.NmGrupoProduto "+
				" , P.NmPessoa  "+
				" , PC.DsProfissao "+
				" , DIR.IdPessoa "+ 
				" , P.IdPessoa "+
				" , GP.IdGrupoProduto "+
				" , RC.Percentual "+
				" , RC.VlAdicional "+
				" , RC.VlBonificacao "+
				" , O.TpOperacao "+
				
				" UNION "+
				"  select "+ 
				" nrdocumento, "+
				" Convert(Varchar,dtmovimento,103) as dtmovimento, "+
				"  dtmovimento as t,  "+
				" empresa, "+
				" case when cliente is null then ( "+
				" case when tipo = 'D' then 'Debito' else 'Crédito'end) end  as cliente, "+
				" case when produto is null then historico end as produto, "+
				" qtitem, "+
				" vlitem, "+
				" vlcomissao, "+
				" idvendedor, "+
				" idgrupo, "+
				" grupo,	 "+
				" percentual, "+
				" vladicional, "+
				" vlbonificacao, "+
				" stliberado, "+
				" idfechamento, "+
				" id "+
				" from Nsys_LancamentoDebitoCredito "+
				" Where idvendedor = ?  and dtmovimento between ? and ? "+
				" and id not in "+
				" (select idlancamentodb from nsys_tabelacomissao )"+
				" order by dtoriginal ";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(query);
		pp.setString(1, idpessoa);
		pp.setString(2, converte(datacorte));
		pp.setString(3, data);
		pp.setString(4, nmfuncao);
		pp.setString(5, idpessoa);
		pp.setString(6, data);
		pp.setString(7, idpessoa);
		pp.setString(8, converte(datacorte));
		pp.setString(9, data);
		
		ResultSet rs = pp.executeQuery();
		List<TabelaComissao> lista = new ArrayList<TabelaComissao>();
		TabelaComissao tabelaComissao;
		while(rs.next()){
			tabelaComissao = new TabelaComissao();
			tabelaComissao.setNrdocumento(rs.getString("nrdocumento"));
			tabelaComissao.setDtemissao(rs.getString("dtemissao"));
			tabelaComissao.setCdempresa(rs.getString("cdempresa"));
			tabelaComissao.setNmpessoa(rs.getString("nmpessoa"));
			tabelaComissao.setNmproduto(rs.getString("nmproduto"));
			tabelaComissao.setQtitem(rs.getDouble("qtitem"));
			tabelaComissao.setVlitem(rs.getDouble("vlitem"));
			tabelaComissao.setVlcomissao(rs.getDouble("vlcomissao"));
			tabelaComissao.setNmgrupo(rs.getString("nmgrupoproduto"));
			tabelaComissao.setVendedor(rs.getString("vendedor"));
			tabelaComissao.setIdgrupo(rs.getString("idgrupoproduto"));
			tabelaComissao.setPercentual(rs.getDouble("percentual"));
			tabelaComissao.setVladicional(rs.getDouble("VlAdicional"));
			if(configuracaoRepository.configuracaoPorNmconfiguracao("grupopercentual") != null 
					&& tabelaComissao.getIdgrupo() != null){
				if(
					tabelaComissao.getIdgrupo().equals(
								configuracaoRepository.configuracaoPorNmconfiguracao("grupopercentual").getVlconfiguracao())){
					
					tabelaComissao.setVladicional(
						(tabelaComissao.getVlitem() *
						rs.getDouble("VlAdicional"))/100);
				}
				
			}
			tabelaComissao.setIdlancamentodb(rs.getString("idlancamentoDB"));
			lista.add(tabelaComissao);
		}
		rs.close();
		pp.close();
		dataSource.getConnection().close();
		return lista;
	}
	
	public void insertLancamentoCD(TabelaComissao t) throws SQLException{
		executaCreate();
		executaCreateLancamento();
		t.setDtemissao(converte(t.getDtemissao()));
		String insert = "insert into Nsys_LancamentoDebitoCredito "+
		"(tipo,idvendedor,dtmovimento,historico,vlcomissao,empresa,stliberado,nrdocumento) "+
				" values "+
		"(?,?,?,?,?,?,?,?)";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(insert);
		pp.setString(1, t.getTipo());
		pp.setString(2, t.getIdvendedor());
		pp.setString(3, t.getDtemissao());
		pp.setString(4, t.getHistorico());
		pp.setDouble(5, t.getVlcomissao());
		pp.setString(6, t.getCdempresa());
		pp.setString(7, "S");
		pp.setString(8, "");
		pp.execute();
		
		pp.close();
		dataSource.getConnection().close();
	}
	@SuppressWarnings("unchecked")
	public List<TabelaComissaoView> listaporvendedorView(String idpessoa,String data,String nmfuncao) throws SQLException{
		executaCreate();
		executaCreateFechamentoItem();
		executaCreateFechamento();
		mataEntity();
		String datacorte = configuracaoRepository.configuracaoPorNmconfiguracao("datacorte").getVlconfiguracao();
		
		return entityManager.createQuery("from TabelaComissaoView v where v.idvendedor = :idpessoa " +
				" and (v.nmcampo = :nmfuncao or v.nmcampo is null) and v.dtoriginal > :datacorte and v.dtoriginal <= :data " +
				" and v.iddocumentoitem not in (select t.idlancamentodb from TabelaComissao t) order by v.dtoriginal	").
				setParameter("idpessoa",idpessoa).
				setParameter("nmfuncao", nmfuncao).
				setParameter("datacorte", converte(datacorte)).
				setParameter("data", data).
				
				getResultList();
			
		
	}
	@SuppressWarnings("unchecked")
	public List<TabelaComissaoView> listaporvendedorView2(String idpessoa,String data,String nmfuncao) throws SQLException{
		executaCreate();
		executaCreateFechamentoItem();
		executaCreateFechamento();
		String datacorte = configuracaoRepository.configuracaoPorNmconfiguracao("datacorte").getVlconfiguracao();
		String query = ""+
				" select * from vw_Nsys_TabelaComissao where " +
				" idvendedor = ? and dtoriginal > ? and dtoriginal <= ? " +
				" and ( nmcampo = ? or nmcampo is null) "+
				" and (IdDocumentoitem+IdVendedor) not in ( "+
				" select "+
				"(IdDocumentoitem+IdVendedor) "+
				" from Nsys_ComissaoFechamentoItem ) "+
				" order by dtoriginal ";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(query);
		pp.setString(1, idpessoa);
		pp.setString(2, converte(datacorte));
		pp.setString(3, data);
		pp.setString(4, nmfuncao);
		
		
		ResultSet rs = pp.executeQuery();
		List<TabelaComissaoView> lista = new ArrayList<TabelaComissaoView>();
		TabelaComissaoView tabelaComissao;
		while(rs.next()){
			tabelaComissao = new TabelaComissaoView();
			tabelaComissao.setIddocumento(rs.getString("iddocumento"));
			tabelaComissao.setNrdocumento(rs.getString("nrdocumento"));
			tabelaComissao.setIddocumentoitem(rs.getString("iddocumentoitem"));
			tabelaComissao.setDtemissao(rs.getString("dtemissao"));
			tabelaComissao.setCdempresa(rs.getString("cdempresa"));
			tabelaComissao.setNmpessoa(rs.getString("nmpessoa"));
			tabelaComissao.setNmproduto(rs.getString("nmproduto"));
			tabelaComissao.setQtitem(rs.getDouble("qtitem"));
			tabelaComissao.setVlitem(rs.getDouble("vlitem"));
			tabelaComissao.setVlcomissao(rs.getDouble("vlcomissao"));
			tabelaComissao.setNmgrupoproduto(rs.getString("nmgrupoproduto"));
			tabelaComissao.setIdvendedor(rs.getString("idvendedor"));
			tabelaComissao.setIdgrupoproduto(rs.getString("idgrupoproduto"));
			tabelaComissao.setPercentual(rs.getDouble("percentual"));
			tabelaComissao.setVladicional(rs.getDouble("VlAdicional"));
			if(configuracaoRepository.configuracaoPorNmconfiguracao("grupopercentual") != null 
					&& tabelaComissao.getIdgrupoproduto() != null){
				if(
					tabelaComissao.getIdgrupoproduto().equals(
								configuracaoRepository.configuracaoPorNmconfiguracao("grupopercentual").getVlconfiguracao())){
					
					tabelaComissao.setVladicional(
						(tabelaComissao.getVlitem() *
						rs.getDouble("VlAdicional"))/100);
				}
				
			}
			tabelaComissao.setIdlancamentoDB(rs.getString("idlancamentoDB"));
			lista.add(tabelaComissao);
		}
		rs.close();
		pp.close();
		dataSource.getConnection().close();
		return lista;
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
	public void mataEntity(){
		entityManager.clear();
	}
}
