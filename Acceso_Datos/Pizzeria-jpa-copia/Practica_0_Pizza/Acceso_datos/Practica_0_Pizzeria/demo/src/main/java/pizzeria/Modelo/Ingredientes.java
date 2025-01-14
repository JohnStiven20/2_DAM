package pizzeria.Modelo;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingredientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
            CascadeType.MERGE })
    @JoinTable(name = "ingrediente_alergeno", joinColumns = @JoinColumn(name = "ingrediente_id"), inverseJoinColumns = @JoinColumn(name = "alergeno_id"))
    private List<Alergeno> alergenos;

    @ManyToMany(mappedBy = "ingredientes")
    private List<Producto> productos;

    static int contador = 0;

    public Ingredientes() {
    }

    public Ingredientes(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ingredientes(String nombre, List<Alergeno> alergenos) {
        this.nombre = nombre;
        this.alergenos = alergenos;
    }

    public Ingredientes(int id, String nombre, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.alergenos = alergenos;
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

    public List<Alergeno> getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List<Alergeno> alergenos) {
        this.alergenos = alergenos;
    }

    @Override
    public String toString() {
        return "Ingredientes [id=" + id + ", nombre=" + nombre + ", alergenos=" + alergenos + "]";
    }

}
