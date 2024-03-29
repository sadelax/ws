package es.cursogetafe.banco.persistencia;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import es.cursogetafe.banco.modelo.Cuenta;

@Repository("cuentaDao")
public class CuentaDaoJPA implements CuentaDao {

	@Autowired
	@Qualifier("emf")
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@Override
	public void save(Cuenta entidad) {
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(entidad);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Cuenta findById(Integer id) {
		em = emf.createEntityManager();
		Cuenta c = em.find(Cuenta.class, id);
		em.close();
		return c;
	}

	@Override
	public Cuenta findByIdEager(Integer id) {
		Cuenta c;
		em = emf.createEntityManager();
		String jpql = "SELECT c FROM Cuenta c LEFT JOIN FETCH c.tarjetas WHERE idCuenta = :id";
		TypedQuery<Cuenta> q = em.createQuery(jpql, Cuenta.class);
		q.setParameter("id", id);
		try {
			c = q.getSingleResult();
		} catch (Exception e) {
			c = null;
		}
		em.close();
		return c;
	}

	@Override
	public Set<Cuenta> findAll() {
		Set<Cuenta> listado;
		em = emf.createEntityManager();
		String jpql = "SELECT c FROM Cuenta c";
		TypedQuery<Cuenta> q = em.createNamedQuery(jpql, Cuenta.class);
		try {
			listado = new TreeSet<>(q.getResultList());
			
		} catch (Exception e) {
			listado = null;
		}
		em.close();
		return listado;
	}

	@Override
	public void delete(Cuenta entidad) {
		em = emf.createEntityManager();
		if (entidad != null) {
			em.getTransaction().begin();
			em.remove(entidad);
			em.getTransaction().commit();
		}
		em.close();
	}

}
