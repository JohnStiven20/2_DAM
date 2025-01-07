package app.Modelo;

import java.util.List;

public class Pasta extends Producto{

    private List<Ingrediente> ingredientes;

    public Pasta(String nombre, double precio, List<Ingrediente> listaIngredientes) {
        super(nombre, precio);
        this.ingredientes = listaIngredientes;
    }

    public Pasta(int id, List<Ingrediente> ingredientes, String nombre, double precio) {
        super(id, nombre, precio);
        this.ingredientes = ingredientes;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return super.toString() + " Pasta [ingredientes=" + ingredientes + "]";
    }

}
