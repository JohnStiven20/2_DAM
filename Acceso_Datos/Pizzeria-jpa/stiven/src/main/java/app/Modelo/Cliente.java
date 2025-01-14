package app.Modelo;

import java.util.List;

import app.Interfaces.Pagable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Cliente implements  Pagable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="dni", unique=true, nullable=false)
    private String  dni;
    private String nombre;
    @Column(unique=true, nullable=false)
    private String telefono;
    @Column(unique=true, nullable=false)
    private String email;
    private String password;
    private Boolean admin;
    private String direccion;
    private String apellidos;
    
    @OneToMany(mappedBy="cliente", cascade=CascadeType.ALL, orphanRemoval=true) 
    private List<Pedido> listaPedidos;

    public Cliente(String dni, String nombre, String telefono, String email,String direccion , String password, List<Pedido> listaPedidos, Boolean admin, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.listaPedidos = listaPedidos;
        this.admin = admin;
        this.direccion = direccion;
        this.apellidos = apellidos;    
    }

    public Cliente(int id, String dni, String nombre, String telefono, String email,String direccion , String password, List<Pedido> listaPedidos, Boolean admin, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.listaPedidos = listaPedidos;
        this.admin = admin;
        this.direccion = direccion;
        this.apellidos = apellidos;
        this.id = id;   
    }

    public Cliente(int id, String dni, String nombre, String telefono, String email, String password,List<Pedido> listaPedidos, String direccion) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.listaPedidos = listaPedidos;
        this.direccion = direccion;
        this.admin = false;
    }

    
    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public Boolean getAdmin() {
        return admin;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", telefono=" + telefono + ", email="
                + email + ", password=" + password + ", admin=" + admin + ", direccion=" + direccion + "]";
    }

    @Override
    public void pagar(double cantidad) {
        System.out.println("Pagar");
    }


    public String getDireccion() {
        return direccion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
