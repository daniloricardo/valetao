package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nelsys.modelo.TabelaComissao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TabelaComissaoRepository {

	@Autowired
	DataSource dataSource;
	
	public void executaCreate() throws SQLException{
		String create = ""+
				" IF object_id('nsys_tabelacomissao') IS NULL BEGIN   "+ 
				" create table nsys_tabelacomissao( "+
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
				" historico nvarchar(1000), "+
				" nmgrupo nvarchar(255) "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
	}
	
	public List<TabelaComissao> listaporvendedor(String idpessoa,String data,String nmfuncao) throws SQLException{
	
		executaCreate();
		
		String query = ""+
				" With RegraComissao as  "+
				" ( Select NmCampo, IdGrupoProduto, NmGrupoProduto, Percentual, VlAdicional, VlBonificacao from Nsys_RegraComissao "+ 
				" group by NmCampo, IdGrupoProduto, NmGrupoProduto, Percentual, VlAdicional, VlBonificacao ) "+ 
				" Select  "+
				" D.NrDocumento "+
				" , Convert(Varchar,D.DtEmissao,103) as DtEmissao "+
				" , LE.CdEmpresa "+
				" , Cli.NmPessoa "+
				" , Prod.NmProduto "+
				" , DI.QtItem "+
				" , DI.VlItem "+
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
				" Where DIR.IdPessoa = ?  and D.DtEmissao <= ? and RC.NmCampo = 	? "+
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
				" UNION "+
				"  select "+ 
				" nrdocumento, "+
				" Convert(Varchar,dtmovimento,103) as dtmovimento, "+
				" empresa, "+
				" case when cliente is null then ( "+
				" case when tipo = 'D' then 'Debito' else 'Crédito'end) end  as cliente, "+
				" case when produto is null then historico end as produto, "+
				" qtitem, "+
				" vlitem, "+
				" vlcomissao, "+
				" idvendedor, "+
				" grupo, "+
				" nmgrupo,	 "+
				" percentual, "+
				" vladicional, "+
				" vlbonificacao, "+
				" stliberado, "+
				" idfechamento "+
				" from nsys_tabelacomissao "+
				" Where idvendedor = ?  and dtmovimento <= ? ";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(query);
		pp.setString(1, idpessoa);
		pp.setString(2, data);
		pp.setString(3, nmfuncao);
		pp.setString(4, idpessoa);
		pp.setString(5, data);
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
			lista.add(tabelaComissao);
		}
		return lista;
	}
	public void insertLancamentoCD(TabelaComissao t) throws SQLException{
		executaCreate();
		t.setDtemissao(converte(t.getDtemissao()));
		String insert = "insert into nsys_tabelacomissao "+
		"(tipo,idvendedor,dtmovimento,historico,vlcomissao,empresa,stliberado) "+
				" values "+
		"(?,?,?,?,?,?,?)";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(insert);
		pp.setString(1, t.getTipo());
		pp.setString(2, t.getIdvendedor());
		pp.setString(3, t.getDtemissao());
		pp.setString(4, t.getHistorico());
		pp.setDouble(5, t.getVlcomissao());
		pp.setString(6, t.getCdempresa());
		pp.setString(7, "S");
		pp.execute();
	}
	public static String converte(String data){
		return data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
	}
}
