package pizzeria.Modelo;


public enum EstadoPedido {
    ENTREGADO("ENTREGADO"),
    PENDIENTE("PENDIENTE"),
    CANCELADO("CANCELADO");

    private final String size;

    EstadoPedido(String size) {
        this.size = size;
    }

    public String getValue() {
        return size;
    }

}
