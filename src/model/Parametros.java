package model;

/**
 * Parámetros de la simulación productor-consumidor.
 * Todos los límites se configuran desde el panel de la vista.
 */
public class Parametros {
    // Cantidad de productos que el productor crea en cada ciclo (aleatoria entre min y max)
    public final int minProduccion;
    public final int maxProduccion;
    // Cantidad de productos que el consumidor gasta en cada ciclo (aleatoria entre min y max)
    public final int minConsumo;
    public final int maxConsumo;
    // Límites del stock: nunca baja del mínimo ni supera el máximo
    public final int stockMinimo;
    public final int stockMaximo;
    // Tiempo entre ciclos (ms)
    public final int velocidadProduccion;
    public final int velocidadConsumo;

    public Parametros(int minProduccion, int maxProduccion,
                      int minConsumo, int maxConsumo,
                      int stockMinimo, int stockMaximo,
                      int velocidadProduccion, int velocidadConsumo) {
        this.minProduccion = minProduccion;
        this.maxProduccion = maxProduccion;
        this.minConsumo = minConsumo;
        this.maxConsumo = maxConsumo;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.velocidadProduccion = velocidadProduccion;
        this.velocidadConsumo = velocidadConsumo;
    }

    public static Parametros porDefecto() {
        return new Parametros(1, 5, 1, 4, 5, 30, 800, 1200);
    }

    /**
     * Comprueba que los parámetros tienen sentido.
     * @return null si son válidos, o el mensaje de error.
     */
    public String validar() {
        if (minProduccion < 1) return "La producción mínima debe ser al menos 1.";
        if (maxProduccion < minProduccion) return "La producción máxima no puede ser menor que la mínima.";
        if (minConsumo < 1) return "El consumo mínimo debe ser al menos 1.";
        if (maxConsumo < minConsumo) return "El consumo máximo no puede ser menor que el mínimo.";
        if (stockMinimo < 0) return "El stock mínimo no puede ser negativo.";
        if (stockMaximo <= stockMinimo) return "El stock máximo debe ser mayor que el stock mínimo.";
        int margen = stockMaximo - stockMinimo;
        if (maxProduccion > margen)
            return "La producción máxima (" + maxProduccion + ") no cabe entre el stock mínimo y máximo (margen " + margen + ").";
        if (maxConsumo > margen)
            return "El consumo máximo (" + maxConsumo + ") supera el margen entre stock mínimo y máximo (" + margen + ").";
        if (velocidadProduccion < 100 || velocidadConsumo < 100) return "Las velocidades deben ser de al menos 100 ms.";
        return null;
    }
}
