package model;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private final Resource resource;
    private final Model model;
    private volatile boolean running = true;

    public Producer(Resource resource, Model model) {
        this.resource = resource;
        this.model = model;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Parametros p = model.getParametros();
                // Cantidad aleatoria entre el mínimo y el máximo de producción
                int cantidad = ThreadLocalRandom.current().nextInt(p.minProduccion, p.maxProduccion + 1);
                model.estadoProductor("Produciendo " + cantidad + " uds");
                if (resource.producir(cantidad)) {
                    model.estadoProductor("Produjo " + cantidad + " uds");
                    Thread.sleep(model.getParametros().velocidadProduccion);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        model.estadoProductor("Detenido");
    }
}
