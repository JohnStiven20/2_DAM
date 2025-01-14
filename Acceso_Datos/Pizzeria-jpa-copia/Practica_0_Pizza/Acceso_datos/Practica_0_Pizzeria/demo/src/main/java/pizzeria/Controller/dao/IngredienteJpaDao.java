package pizzeria.Controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Ingredientes;

public interface IngredienteJpaDao {

    public void save(Ingredientes ingrediente); 
    public List<Ingredientes> getAllIngredientesByIdProducto(int id); 

}
