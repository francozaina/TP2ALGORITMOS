package aed.tp2;

public class InfoPromo {
    private double factor;     // Multiplicador de millas (ej. 1.5, 2.0)
    private boolean tieneTope; // Indica si es una promoción con tope
    private double tope;       // Límite máximo de millas que puede otorgar la promo
    private double acumulado;  // Millas totales acumuladas hasta el momento

    // Constructor para promociones simples (sin tope)
    public InfoPromo(double factor) {
        this.factor = factor;
        this.tieneTope = false;
        this.tope = 0.0;
        this.acumulado = 0.0;
    }

    // Constructor para promociones con tope
    public InfoPromo(double factor, double tope) {
        this.factor = factor;
        this.tieneTope = true;
        this.tope = tope;
        this.acumulado = 0.0;
    }

    // --- Getters ---
    public double getFactor() {
        return this.factor;
    }

    public boolean tieneTope() {
        return this.tieneTope;
    }

    public double getTope() {
        return this.tope;
    }

    public double getAcumulado() {
        return this.acumulado;
    }

    // --- Métodos de modificación y consulta en O(1) ---

    // Registra millas otorgadas por esta promoción y devuelve las millas reales otorgadas
    public double acumularMillas(double millasGanadas) {
        if (!this.tieneTope) {
            return millasGanadas;
        }

        // Si tiene tope, verificamos que no supere el límite restante
        double espacioRestante = this.tope - this.acumulado;
        if (millasGanadas > espacioRestante) {
            millasGanadas = espacioRestante;
        }

        this.acumulado += millasGanadas;
        return millasGanadas;
    }

    // Verifica si la promoción ya alcanzó su tope máximo
    public boolean alcanzaTope() {
        return this.tieneTope && (this.acumulado >= this.tope);
    }
}
