package nelsys.repository;

import java.util.List;

import javax.persistence.EntityManager;

import nelsys.modelo.GrupoProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GrupoRepository {

	@Autowired
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<GrupoProduto> lista(){
		return entityManager.createQuery("from GrupoProduto g  order by g.nmgrupoproduto").getResultList();
	}
	public GrupoProduto findByIndice(int indice){
		return (GrupoProduto) entityManager.createQuery("from GrupoProduto p").getResultList().get(indice);
	}
}
