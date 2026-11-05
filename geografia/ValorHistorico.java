package geografia;

public class ValorHistorico {
    // Atributos de la clase
    private int mes;
    private double acumuladoMedio;

    // Constructor
    public ValorHistorico (int mes, double acumuladoMedio) {
        this.mes = mes;
        this.acumuladoMedio = acumuladoMedio;
    }

    // Getters
    public int getMes() { return this.mes; }
    public double getAcumuladoMedio() { return this.acumuladoMedio; }
}
