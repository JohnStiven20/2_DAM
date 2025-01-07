package app.Modelo;

import app.Enums.Size;


public class Producto {

    protected int id;
    protected String nombre;
    protected double precio;
    private Size size;
    
    protected Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        
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
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", size=" + size + "]";
    }

   
}
