package nelsys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import nelsys.modelo.TabelaComissaoView;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages="nelsys")
public class StartApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StartApp.class);
		app.setShowBanner(false);
		app.run(args);
	}
	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory){
		return entityManagerFactory.createEntityManager();
	}
	@Bean
	public static SimpleDriverDataSource dataSource() throws IOException, SQLException, ClassNotFoundException {
		
		File file1 = new File("hibernate.properties");		        
        
        Properties properties = new Properties(); 
        FileInputStream stream = null;  
        stream = new FileInputStream(file1);  
        properties.load(stream); 
        String propertiesURL = properties.getProperty("hibernate.connection.url");
        String propertiesUser = properties.getProperty("hibernate.connection.username");
        String propertiesSenha = properties.getProperty("hibernate.connection.password");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   
		Driver driver = DriverManager.getDriver(propertiesURL+
				";user=" + propertiesUser + ";password=" + propertiesSenha + ";");
		SimpleDriverDataSource df = new SimpleDriverDataSource(driver,
				propertiesURL, propertiesUser, propertiesSenha);
		return df;
	}
	
	
}
