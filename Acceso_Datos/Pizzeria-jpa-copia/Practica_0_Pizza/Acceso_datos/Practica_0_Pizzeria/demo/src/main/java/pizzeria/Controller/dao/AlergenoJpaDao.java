package pizzeria.Controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pizzeria.Modelo.Alergeno;

public interface AlergenoJpaDao {

       
    public void save( Alergeno alergeno) ; 

    public Alergeno findById(int id) ; 

    public Alergeno findByName(String name); 

    public void relacionIngredienteAlergeno(int id_ingrediente, int id_alergeno);

    // public List<Alergeno> getAllAlergenoByidIngrediente(Connection con, int id_ingrediente)
    // throws SQLException, ClassNotFoundException ; 
            
} 

