package app.Modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Ingrediente
 */
@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @ManyToMany(mappedBy = "ingredientes", fetch = FetchType.EAGER)
    private List<Producto> listaProductos;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<Alergeno> alergenos;

    public Ingrediente(int id, String nombre, List<Producto> listaProductos, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.listaProductos = listaProductos;
        this.alergenos = alergenos;
    }

    public Ingrediente(String nombre, List<Alergeno> alergenos) {
        this.nombre = nombre;
        this.alergenos = alergenos;
    }

    public Ingrediente(String nombre, List<Producto> listaProductos, List<Alergeno> alergenos) {
        this.nombre = nombre;
        this.listaProductos = listaProductos;
        this.alergenos = alergenos;
    }

    public Ingrediente(int id, String nombre, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.alergenos = alergenos;
    }

    public Ingrediente() {
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
        return "Ingrediente [nombre=" + nombre + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
