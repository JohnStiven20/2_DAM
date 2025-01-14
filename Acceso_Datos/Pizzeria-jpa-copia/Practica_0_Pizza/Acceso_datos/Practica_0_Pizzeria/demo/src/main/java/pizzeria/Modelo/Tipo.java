package pizzeria.Modelo;

public enum Tipo {

    PIZZA("pizza"),
    PASTA("pasta"),
    BEBIDA("bebida");

    private final String tipo;

    Tipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValue() {
        return tipo;
    }

}
