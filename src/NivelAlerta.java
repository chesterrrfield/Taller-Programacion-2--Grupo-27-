public enum NivelAlerta {
    // Definición de las constantes con el valor que tomarán
    VERDE(false),
    AMARILLA(false),
    NARANJA(true),
    ROJA(true);
    /* Solo las alertas NARANJAS y ROJAS necesitan equipo
    No esta especificado el valor que van a tomar en el diagrama asi que use mi logica
    */

    // Atributo interno
    private final boolean requiereEquipo;

    // Constructor
    NivelAlerta(boolean requiereEquipo) {
        this.requiereEquipo = requiereEquipo;
    }

    // Metodo del diagrama
    public boolean requiereEquipo() {
        return this.requiereEquipo;
    }
}