package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class CustomClientesDaoImpl implements CustomClientesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Clientes> findByName(String queryName) {

        String queryString =
                "SELECT c FROM Clientes c WHERE LOWER(c.nombre) LIKE LOWER(:nombre) AND" +
                        " fechabaja IS NULL";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("nombre", "%" + queryName + "%");
        List<Clientes> clientes = query.getResultList();

        return clientes;
    }

    @Override
    public List<Clientes> findAllFechabajaIsNull() {

        String queryString = "SELECT c FROM Clientes c WHERE fechabaja IS NULL";

        Query query = entityManager.createQuery(queryString);
        List<Clientes> clientes = query.getResultList();

        return clientes;
    }
}
