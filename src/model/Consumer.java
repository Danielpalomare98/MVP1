package model;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    private final Resource resource;
    private final Model model;
    private volatile boolean running = true;

    public Consumer(Resource resource, Model model) {
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
                // Cantidad aleatoria entre el mínimo y el máximo de consumo
                int cantidad = ThreadLocalRandom.current().nextInt(p.minConsumo, p.maxConsumo + 1);
                model.estadoConsumidor("Consumiendo " + cantidad + " uds");
                if (resource.consumir(cantidad)) {
                    model.estadoConsumidor("Consumió " + cantidad + " uds");
                    Thread.sleep(model.getParametros().velocidadConsumo);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        model.estadoConsumidor("Detenido");
    }
}
