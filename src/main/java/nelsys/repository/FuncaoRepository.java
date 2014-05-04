package nelsys.repository;

import java.util.List;

import javax.persistence.EntityManager;

import nelsys.modelo.Funcao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FuncaoRepository {

	@Autowired
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Funcao> lista(){
		return entityManager.createQuery("from Funcao f where f.cdclassificacao like 'NSYS%' order by f.nmfuncao").getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Funcao> listaDestinos(String idfuncao){
		return entityManager.createQuery("from Funcao f where f.cdclassificacao like" +
				" 'NSYS%' and f.idfuncao not in('"+idfuncao+"')").getResultList();
	}
	public Funcao findById(String idfuncao){
		return entityManager.find(Funcao.class, idfuncao);
	}
}
