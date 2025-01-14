package pizzeria.Modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * LineaPedido
 */
@Entity
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cantidad;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public LineaPedido() {

    }

    public LineaPedido(int id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public LineaPedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public void IncremetoCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "LineaPedido [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + "]";
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}