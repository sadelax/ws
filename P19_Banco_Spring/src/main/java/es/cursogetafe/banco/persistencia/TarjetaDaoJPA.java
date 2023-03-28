package es.cursogetafe.banco.persistencia;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import es.cursogetafe.banco.modelo.Tarjeta;

@Repository("tarjetaDao")
public class TarjetaDaoJPA implements TarjetaDao {

	@Autowired
	@Qualifier("emf")
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@Override
	public void save(Tarjeta entidad) {
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(entidad);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Tarjeta findById(Integer id) {
		em = emf.createEntityManager();
		Tarjeta t = em.find(Tarjeta.class, id);
		return t;
	}

	@Override
	public Tarjeta findByIdEager(Integer id) {
		em = emf.createEntityManager();
		String jpql = "SELECT t FROM Tarjeta t LEFT JOIN FETCH t.movimientos WHERE idTarjeta = :id";
		TypedQuery<Tarjeta> q = em.createQuery(jpql, Tarjeta.class);
		q.setParameter("id", id);
		Tarjeta t = q.getSingleResult();
		return t; 
	}

	@Override
	public Set<Tarjeta> findAll() {
		em = emf.createEntityManager();
		String jpql = "SELECT t FROM Tarjeta t";
		TypedQuery<Tarjeta> q = em.createQuery(jpql, Tarjeta.class);
		Set<Tarjeta> listado = new TreeSet<>(q.getResultList());
		return listado;
	}

	@Override
	public void delete(Tarjeta entidad) {
		em = emf.createEntityManager();
		if (entidad != null) {
			em.getTransaction().begin();
			em.remove(entidad);
			em.getTransaction().commit();
		}
		em.close();
		
	}

	@Override
	public List<Tarjeta> vigentes(Tarjeta tarjeta) {
		// TODO Auto-generated method stub
		return null;
	}

}
