package app.Modelo;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

/**
 * Ingrediente
 */

 public class Ingrediente {

    private int id;
    @CsvBindByName(column = "NOMBRE", required = true)
    private String nombre;

    @CsvBindAndSplitByName(elementType= String.class , writeDelimiter=",")
    private List<String> alergenos = new ArrayList<>();

    public Ingrediente(String nombre, List<String> alergenos) {
        this.nombre = nombre;
        this.alergenos = alergenos;
    }


    public Ingrediente(int id, String nombre, List<String> alergenos) {
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

    public List<String> getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List<String> alergenos) {
        this.alergenos = alergenos;
    }

    @Override
    public String toString() {
        return "Ingrediente [nombre=" + nombre + ", alergenos=" + alergenos + "]";
    }



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

}
