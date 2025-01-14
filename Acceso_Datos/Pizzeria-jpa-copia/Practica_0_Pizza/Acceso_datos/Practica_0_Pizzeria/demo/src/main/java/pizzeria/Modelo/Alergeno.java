package pizzeria.Modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Alergeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @ManyToMany(mappedBy = "alergenos",  cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Ingredientes> ingrediente;

    public Alergeno() {

    }

    public Alergeno(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Alergeno(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Alergeno [id=" + id + ", nombre=" + nombre + "]";
    }

}
