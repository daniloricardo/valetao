package nelsys.repository;

import java.util.List;

import javax.persistence.EntityManager;

import nelsys.modelo.EmpresaERP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpresaRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<EmpresaERP> findAll(){
		return entityManager.createQuery("from EmpresaERP e ").getResultList();
	}

}
