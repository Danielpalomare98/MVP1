package model;

public class Producer implements Runnable {
    private final Resource resource;
    private volatile boolean running = true;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        int contador = 1;
        while (running) {
            try {
                resource.producir(contador++);
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}