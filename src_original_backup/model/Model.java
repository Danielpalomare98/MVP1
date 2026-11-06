package model;

import presenter.IPresenter;

public class Model implements IModel, Runnable {
    private IPresenter presenter;
    private final Resource resource = new Resource();
    private Producer producer;
    private Consumer consumer;
    private Thread threadProductor;
    private Thread threadConsumidor;

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void iniciar() {
        Thread threadModelo = new Thread(this);
        threadModelo.start();
    }

    @Override
    public void run() {
        producer = new Producer(resource);
        consumer = new Consumer(resource, this);

        threadProductor = new Thread(producer);
        threadConsumidor = new Thread(consumer);

        threadProductor.start();
        threadConsumidor.start();
    }

    @Override
    public void detener() {
        if (producer != null) producer.stop();
        if (consumer != null) consumer.stop();
        if (threadProductor != null) threadProductor.interrupt();
        if (threadConsumidor != null) threadConsumidor.interrupt();
    }

    public void notificarNuevoDato(int dato) {
        if (presenter != null) {
            presenter.notificarDatoConsumido(dato);
        }
    }
}