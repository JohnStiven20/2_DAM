package app.Modelo;

import java.util.Date;
import java.util.List;

import app.Interfaces.Pagable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;


/**
 * Pedido
 */
@Entity
public class Pedido {

    public enum EstadoPedido {
        PEDIENTE, ENTREGADO, CANCELADO
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private Date fecha;
    private EstadoPedido estado;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineaPedidos;
    
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Transient
    private Pagable pagable;

    public Pedido() {
    }
   
    public Pedido(int id,EstadoPedido estado, List<LineaPedido> lineaPedidos, Cliente cliente, Pagable pagable) {
        this.id = id;
        this.fecha = new Date();
        this.estado = estado;
        this.lineaPedidos = lineaPedidos;
        this.cliente = cliente;
        this.pagable = pagable;
    }    

    public Pedido(EstadoPedido estado, List<LineaPedido> lineaPedidos, Cliente cliente, Pagable pagable) {
        this.fecha = new Date();
        this.estado = estado;
        this.lineaPedidos = lineaPedidos;
        this.cliente = cliente;
        this.pagable = pagable;

    }

    public void setPagable(Pagable pagable) {
        this.pagable = pagable;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setLineaPedidos(List<LineaPedido> lineaPedidos) {
        this.lineaPedidos = lineaPedidos;
    }

    public List<LineaPedido> getLineaPedidos() {
        return lineaPedidos;
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

    public void setId(int id) {
        this.id = id;
    }

    public Pagable getPagable() {
        return pagable;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido{");
        sb.append("id=").append(id);
        sb.append(", fecha=").append(fecha);
        sb.append(", estado=").append(estado);
        sb.append(", lineaPedidos=").append(lineaPedidos);
        sb.append('}');
        return sb.toString();
    }


}
