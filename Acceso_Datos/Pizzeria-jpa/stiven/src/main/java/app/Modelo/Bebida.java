package app.Modelo;

import app.Enums.Size;

public class Bebida extends Producto {

    private Size size;

    public Bebida(String nombre, double precio, Size size) {
        super(nombre, precio);
        this.size = size;
    }

    public Bebida(int id, String nombre, double precio, Size size) {
        super(id, nombre, precio);
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + " Bebida [size=" + size + "]";
    }

}
