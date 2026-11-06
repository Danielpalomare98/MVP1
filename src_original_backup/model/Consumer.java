package model;

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
                int dato = resource.consumir();
                model.notificarNuevoDato(dato);
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}