package geografia;

public class ValorHistorico {
    // Atributos de la clase
    private int mes;
    private double acumuladoMedio;
    // Atributo de composición
    private ZonaGeografica zona;

    // Constructor
    public ValorHistorico (int mes, double acumuladoMedio, ZonaGeografica zona) {
        this.mes = mes;
        this.acumuladoMedio = acumuladoMedio;
        this.zona = zona;
    }

    // Getters
    public int getMes() { return this.mes; }
    public double getAcumuladoMedio() { return this.acumuladoMedio; }
}
