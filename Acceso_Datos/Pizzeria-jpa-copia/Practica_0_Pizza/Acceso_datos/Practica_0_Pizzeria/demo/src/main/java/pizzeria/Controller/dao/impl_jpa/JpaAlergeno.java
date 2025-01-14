package pizzeria.Controller.dao.impl_jpa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pizzeria.Controller.dao.AlergenoJpaDao;
import pizzeria.Modelo.Alergeno;

public class JpaAlergeno implements AlergenoJpaDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    @Override
    public void save(Alergeno alergeno) {

    }

    @Override
    public Alergeno findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Alergeno findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    @Override
    public void relacionIngredienteAlergeno(int id_ingrediente, int id_alergeno) {

    }

    public List<Alergeno> getAllAlergenoByidIngrediente(int id_ingrediente) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Alergeno> alergenos = entityManager
                .createQuery("SELECT a FROM Alergeno a JOIN a.ingrediente p WHERE p.id = :idIngrediente\r\n" + //
                        "", Alergeno.class)
                .setParameter("idIngrediente", id_ingrediente)
                .getResultList();

        return alergenos;
    }

}
