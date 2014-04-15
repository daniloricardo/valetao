package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import nelsys.modelo.Regra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegraRepository {
	
	@Autowired
	DataSource dataSource;

	public void insere(List<Regra> lista) throws SQLException{
		PreparedStatement preparedStatement;
		for(Regra r : lista){
			String insert = "insert into Nsys_RegraComissao "+
		"(nmcampo,idgrupoproduto,nmgrupoproduto,percentual,vladicional,vlbonificacao) "+
					" values "+
		"('"+r.getNmcampo()+"','"+r.getIdgrupogrupo()+"','"+r.getNmgrupoproduto()+"',"+r.getPercentual()
		+","+r.getVladicional()+","+r.getVlbonificacao()+")";
		String update = "update Nsys_RegraComissao set percentual ="+r.getPercentual()+" "+
				"where nmcampo = '"+r.getNmcampo()+"' and idgrupoproduto = '"+r.getIdgrupogrupo()+"'";
			Statement statement = dataSource.getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from Nsys_RegraComissao where nmcampo = '"+
			r.getNmcampo()+"' and idgrupoproduto = '"+r.getIdgrupogrupo()+"'");
			if(rs.next()){
				preparedStatement = 
						dataSource.getConnection().prepareStatement(update);
				preparedStatement.execute();
			}
			else {
				preparedStatement = 
						dataSource.getConnection().prepareStatement(insert);
				preparedStatement.execute();
			}
		}
	}
}
