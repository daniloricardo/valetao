package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nelsys.modelo.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository {

	@Autowired
	DataSource dataSource;
	
	public List<Produto> listaporgrupo(String id) throws SQLException{
		System.out.println("idgrupo: "+ id);
		String query = ""+
		" select P.idproduto,P.NmProduto,P.IdGrupoProduto,C.CdChamada "+ 
		" from Produto P inner join CodigoProduto C on (C.IdProduto = P.IdProduto) "+
		" where P.IdGrupoProduto = '"+id+"' and C.StCodigoPrincipal = 'S' ";
		Statement pp = dataSource.getConnection().createStatement();
		ResultSet rs = pp.executeQuery(query);
		List<Produto> lista = new ArrayList<Produto>();
		Produto p;
		System.out.println(query);
		while(rs.next()){
			p = new Produto();
			p.setIdproduto(rs.getString("idproduto"));
			p.setCdchamada(rs.getString("cdchamada"));
			p.setIdgrupo(rs.getString("idgrupoproduto"));
			p.setNmproduto(rs.getString("NmProduto"));
			lista.add(p);
		}
		return lista;
	}
}
