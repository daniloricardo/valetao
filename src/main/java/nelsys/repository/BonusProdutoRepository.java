package nelsys.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import nelsys.modelo.BonusProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BonusProdutoRepository {

	@Autowired
	DataSource dataSource;
	
	public void executaCreate() throws SQLException{
		String create = ""+
				" IF object_id('Nsys_bonusproduto') IS NULL BEGIN   "+ 
				" create table Nsys_bonusproduto "+
				" ( "+
				" id integer primary key identity, "+
				" idfuncao nvarchar(10) not null, "+
				" idgrupo nvarchar(10) not null, "+
				" idproduto nvarchar(10) not null, "+
				" vlbonus float, "+
				" albonus float, "+
				" vlbonificacao float, "+
				" albonificao float "+
				" ) END";
		PreparedStatement pp = dataSource.getConnection()
				.prepareStatement(create);
		pp.execute();
		pp.close();
	}
	
	public void insertorupdate(List<BonusProduto> lista) throws SQLException{
		executaCreate();
		String query = ""+
	" insert into Nsys_bonusproduto "+
	" (idfuncao,idgrupo,idproduto,vlbonus,albonus) "+
	" values "+
	" (?,?,?,?,?) ";
		Statement st = dataSource.getConnection().createStatement();
		
		PreparedStatement pp = dataSource.getConnection().prepareStatement(query);
		PreparedStatement ppB;
		for(BonusProduto b : lista){
			String queryB = "select * from Nsys_bonusproduto where idfuncao = '"+b.getIdfuncao()+"' "+
					" and idgrupo ='"+b.getIdgrupo()+"' and idproduto = '"+b.getIdproduto()+"'";
			ResultSet rs = st.executeQuery(queryB);
			if(rs.next()){
				String queryC = "update Nsys_bonusproduto set vlbonus = ? where id = ?";
				ppB = dataSource.getConnection().prepareStatement(queryC);
				ppB.setDouble(1, b.getVlbonus());
				ppB.setInt(2, rs.getInt("id"));
				ppB.execute();
				
			}
			else{		
			pp.setString(1, b.getIdfuncao());
			pp.setString(2, b.getIdgrupo());
			pp.setString(3, b.getIdproduto());
			pp.setDouble(4, b.getVlbonus());
			pp.setDouble(5, new Double(0));
			pp.execute();
			
			}
			
		}
		
		pp.close();
		st.close();
	}
}
