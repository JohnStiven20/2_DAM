package pizzeria.Modelo;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date fecha;
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = { CascadeType.MERGE, CascadeType.PERSIST , CascadeType.REMOVE})
    private List<LineaPedido> listaLineaPedidos;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "pagable_id")
    private Pagable pagable;


    public Pedido() {

    }

    public Pedido(Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos, Pagable pagable) {
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
        this.pagable = pagable;
    }

    public Pedido(int id, Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos,
            Cliente cliente, Pagable pagable) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
        this.cliente = cliente;
        this.pagable = pagable;
    }

    public Pedido(Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos) {
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
    }

    public Pedido(int id, Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos, Pagable pagable) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
    }

    public Pedido(Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos,
            Cliente cliente, Pagable pagable) {
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
        this.cliente = cliente;
        this.pagable = pagable;
    }

    // public Pedido(Date fecha, EstadoPedido estado, List<LineaPedido>
    // listaLineaPedidos , Pagable pagable) {
    // this.fecha = fecha;
    // this.estado = estado;
    // this.listaLineaPedidos = listaLineaPedidos;
    // this.pagable = pagable;
    // }

    public Pedido(Date fecha, EstadoPedido estado, List<LineaPedido> listaLineaPedidos,
            Cliente cliente) {
        this.fecha = fecha;
        this.estado = estado;
        this.listaLineaPedidos = listaLineaPedidos;
        this.cliente = cliente;
    }

    public boolean AñadirLineaPedido(LineaPedido lineaPedido) {
        this.listaLineaPedidos.add(lineaPedido);
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<LineaPedido> getListaLineaPedidos() {
        return listaLineaPedidos;
    }

    public void setListaLineaPedidos(List<LineaPedido> listaLineaPedidos) {
        this.listaLineaPedidos = listaLineaPedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagable getPagable() {
        return pagable;
    }

    public void setPagable(Pagable pagable) {
        this.pagable = pagable;
    }

}
