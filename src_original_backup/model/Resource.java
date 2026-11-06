package model;

import java.util.LinkedList;
import java.util.Queue;

public class Resource {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACIDAD = 5;

    public synchronized void producir(int valor) throws InterruptedException {
        while (buffer.size() == CAPACIDAD) {
            wait(); // Espera si el buffer está lleno
        }
        buffer.add(valor);
        System.out.println("Producido: " + valor);
        notifyAll(); // Notifica al consumidor
    }

    public synchronized int consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Espera si el buffer está vacío
        }
        int valor = buffer.poll();
        System.out.println("Consumido: " + valor);
        notifyAll(); // Notifica al productor
        return valor;
    }
}