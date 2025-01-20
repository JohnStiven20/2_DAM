package app.Modelo;

import java.util.List;

import app.Enums.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy = SINGLE_TABLE) 
@DiscriminatorColumn(name="producto_tipo",  discriminatorType = DiscriminatorType.STRING) 
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int id;
    protected String nombre;
    protected double precio;
    @Enumerated(EnumType.STRING)
    private Size size;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    private List<Ingrediente> ingredientes;
    
    protected Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, double precio, Size size, List<Ingrediente> ingredientes) {
        this.nombre = nombre;
        this.precio = precio;
        this.size = size;
        this.ingredientes = ingredientes;
    }

    public Producto(int id, String nombre, double precio, Size size, List<Ingrediente> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.size = size;
        this.ingredientes = ingredientes;
    }

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        
    }

    public Producto(int id, String nombre, double precio, Size size) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.size = size;
    
    }

    public Producto(String nombre, double precio, Size size) {
        this.nombre = nombre;
        this.precio = precio;
        this.size = size;
    }


    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", size=" + size + ", productos="
                + ingredientes + "]";
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    // @Override
    // public String toString() {
    //     return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", size=" + size + "]";
    // }

    

   
}
