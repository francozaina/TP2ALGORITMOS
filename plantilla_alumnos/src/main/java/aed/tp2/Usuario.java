package aed.tp2;

public class Usuario {
    // Identificador único
    private int id;

    // Métricas de millas
    private int millasTotales;     // Millas acumuladas históricamente
    private int millasDisponibles; // Saldo actual para canjear
    private int millasGastadas;    // Millas canjeadas históricamente

    // Control de transferencias para paresTransferidoresExclusivos
    // -1 indica que aún no transfirió/recibió de nadie
    // -2 indica que transfirió/recibió de MÁS DE UN usuario
    private int transferidoA;
    private int recibidoDe;

    public Usuario(int id) {
        this.id = id;
        this.millasTotales = 0;
        this.millasDisponibles = 0;
        this.millasGastadas = 0;
        this.transferidoA = -1;
        this.recibidoDe = -1;
    }

    // --- Getters ---
    public int getId() {
        return this.id;
    }

    public int getMillasTotales() {
        return this.millasTotales;
    }

    public int getMillasDisponibles() {
        return this.millasDisponibles;
    }

    public int getMillasGastadas() {
        return this.millasGastadas;
    }

    public int getTransferidoA() {
        return this.transferidoA;
    }

    public int getRecibidoDe() {
        return this.recibidoDe;
    }

    // --- Métodos de modificación en O(1) ---

    // Acumular millas por vuelo o promoción
    public void acumularMillas(int cantidad) {
        this.millasTotales += cantidad;
        this.millasDisponibles += cantidad;
    }

    // Canjear millas acumuladas
    public void canjearMillas(int cantidad) {
        this.millasDisponibles -= cantidad;
        this.millasGastadas += cantidad;
    }

    // Registrar una transferencia saliente
    public void registrarTransferenciaEnviada(int idDestino, int cantidad) {
        this.millasDisponibles -= cantidad;
        if (this.transferidoA == -1) {
            this.transferidoA = idDestino;
        } else if (this.transferidoA != idDestino) {
            this.transferidoA = -2; // Más de un destino distinto
        }
    }

    // Registrar una transferencia entrante
    public void registrarTransferenciaRecibida(int idOrigen, int cantidad) {
        this.millasTotales += cantidad;
        this.millasDisponibles += cantidad;
        if (this.recibidoDe == -1) {
            this.recibidoDe = idOrigen;
        } else if (this.recibidoDe != idOrigen) {
            this.recibidoDe = -2; // Más de un origen distinto
        }
    }

    // Cálculo de categoría en O(1)
    public int consultarCategoria() {
        if (this.millasTotales < 10000) {
            return 1; // Bronce / Estándar
        } else if (this.millasTotales < 50000) {
            return 2; // Plata
        } else {
            return 3; // Oro
        }
    }
}
