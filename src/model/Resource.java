package model;

/**
 * Recurso compartido (almacén). Controla que el stock nunca
 * baje del mínimo ni supere el máximo.
 */
public class Resource {
    private final Model model;
    private int stock;
    private int stockMinimo;
    private int stockMaximo;
    private int totalProducido = 0;
    private int totalConsumido = 0;

    public Resource(Model model, Parametros p) {
        this.model = model;
        this.stockMinimo = p.stockMinimo;
        this.stockMaximo = p.stockMaximo;
        this.stock = p.stockMinimo; // se empieza con el stock mínimo
    }

    /** Cambia los límites del stock (se puede hacer con el proceso en marcha). */
    public synchronized void configurar(int min, int max) {
        this.stockMinimo = min;
        this.stockMaximo = max;
        notificar();
        notifyAll(); // despierta a los hilos que esperaban para que revisen los nuevos límites
    }

    /**
     * Añade 'cantidad' productos. Si no caben, espera.
     * @return false si con los límites actuales la cantidad ya no puede caber nunca
     *         (el productor elegirá otra cantidad).
     */
    public synchronized boolean producir(int cantidad) throws InterruptedException {
        boolean avisado = false;
        while (stock + cantidad > stockMaximo) {
            if (stockMinimo + cantidad > stockMaximo) return false;
            if (!avisado) {
                model.estadoProductor("Esperando: stock lleno (quiere añadir " + cantidad + ")");
                avisado = true;
            }
            wait();
        }
        stock += cantidad;
        totalProducido += cantidad;
        model.registrar("Producidas " + cantidad + " uds  ->  stock = " + stock);
        notificar();
        notifyAll();
        return true;
    }

    /**
     * Retira 'cantidad' productos sin bajar del stock mínimo. Si no hay suficientes, espera.
     * @return false si con los límites actuales la cantidad ya no se puede consumir nunca.
     */
    public synchronized boolean consumir(int cantidad) throws InterruptedException {
        boolean avisado = false;
        while (stock - cantidad < stockMinimo) {
            if (stockMaximo - cantidad < stockMinimo) return false;
            if (!avisado) {
                model.estadoConsumidor("Esperando: recursos insuficientes (quiere " + cantidad + ")");
                avisado = true;
            }
            wait();
        }
        stock -= cantidad;
        totalConsumido += cantidad;
        model.registrar("Consumidas " + cantidad + " uds  ->  stock = " + stock);
        notificar();
        notifyAll();
        return true;
    }

    /** Producción manual desde el botón: no espera, solo dice si se pudo. */
    public synchronized boolean intentarProducir(int cantidad) {
        if (stock + cantidad > stockMaximo) return false;
        stock += cantidad;
        totalProducido += cantidad;
        notificar();
        notifyAll();
        return true;
    }

    /** Consumo manual desde el botón: no espera, solo dice si se pudo. */
    public synchronized boolean intentarConsumir(int cantidad) {
        if (stock - cantidad < stockMinimo) return false;
        stock -= cantidad;
        totalConsumido += cantidad;
        notificar();
        notifyAll();
        return true;
    }

    public synchronized int getStock() {
        return stock;
    }

    public synchronized void notificar() {
        model.actualizarStock(stock, stockMinimo, stockMaximo, totalProducido, totalConsumido);
    }
}
