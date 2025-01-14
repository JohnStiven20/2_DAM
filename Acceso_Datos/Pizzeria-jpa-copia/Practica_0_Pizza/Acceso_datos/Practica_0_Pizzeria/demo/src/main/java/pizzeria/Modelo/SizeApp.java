package pizzeria.Modelo;

public enum SizeApp {
    PEQUENO("PEQUENO"),
    MEDIANO("MEDIANO"),
    GRANDE("GRANDE");

    private final String size;

    SizeApp(String size) {
        this.size = size;
    }

    public String getValue() {
        return size;
    }

}
