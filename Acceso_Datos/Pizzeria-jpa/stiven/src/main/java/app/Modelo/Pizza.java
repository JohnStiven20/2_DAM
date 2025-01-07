package app.Modelo;

import java.util.List;

import app.Enums.Size;

public class Pizza  extends Producto{

    private Size size;
    private List<Ingrediente> listaIngredientes;
    
    public Pizza(String nombre, double precio, Size size, List<Ingrediente> listaIngredientes) {
        super(nombre, precio);
        this.size = size;
        this.listaIngredientes = listaIngredientes;
    }

    public Pizza(int id, String nombre, double precio, Size size, List<Ingrediente> listaIngredientes) {
        super(id, nombre, precio);
        this.size = size;
        this.listaIngredientes = listaIngredientes;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<Ingrediente> getIngredientes() {
        return listaIngredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.listaIngredientes = ingredientes;
    }

    @Override
    public String toString() {
        return super.toString() + " Pizza [size=" + size + ", listaIngredientes=" + listaIngredientes + "]";
    }

   
}