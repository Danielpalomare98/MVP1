package model;

import presenter.IPresenter;

public class Model implements IModel, Runnable {
    private IPresenter presenter;
    private volatile Parametros parametros = Parametros.porDefecto();
    private final Resource resource = new Resource(this, parametros);
    private Producer producer;
    private Consumer consumer;
    private Thread threadProductor;
    private Thread threadConsumidor;
    private volatile boolean enMarcha = false;

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        resource.notificar(); // muestra el estado inicial en el panel
    }

    @Override
    public synchronized void iniciar() {
        if (enMarcha) return;
        enMarcha = true;
        Thread threadModelo = new Thread(this);
        threadModelo.start();
    }

    @Override
    public void run() {
        producer = new Producer(resource, this);
        consumer = new Consumer(resource, this);

        threadProductor = new Thread(producer, "Productor");
        threadConsumidor = new Thread(consumer, "Consumidor");

        threadProductor.start();
        threadConsumidor.start();
    }

    @Override
    public synchronized void detener() {
        if (!enMarcha) return;
        enMarcha = false;
        if (producer != null) producer.stop();
        if (consumer != null) consumer.stop();
        if (threadProductor != null) threadProductor.interrupt();
        if (threadConsumidor != null) threadConsumidor.interrupt();
    }

    @Override
    public boolean estaEnMarcha() {
        return enMarcha;
    }

    @Override
    public Parametros getParametros() {
        return parametros;
    }

    @Override
    public void configurar(Parametros nuevos) {
        this.parametros = nuevos;
        resource.configurar(nuevos.stockMinimo, nuevos.stockMaximo);
    }

    @Override
    public boolean producirManual(int cantidad) {
        return resource.intentarProducir(cantidad);
    }

    @Override
    public boolean consumirManual(int cantidad) {
        return resource.intentarConsumir(cantidad);
    }

    // ---- Avisos que llegan desde los hilos y se pasan al presentador ----

    void actualizarStock(int stock, int min, int max, int totalProducido, int totalConsumido) {
        if (presenter != null) presenter.notificarStock(stock, min, max, totalProducido, totalConsumido);
    }

    void estadoProductor(String estado) {
        if (presenter != null) presenter.notificarEstadoProductor(estado);
    }

    void estadoConsumidor(String estado) {
        if (presenter != null) presenter.notificarEstadoConsumidor(estado);
    }

    void registrar(String mensaje) {
        if (presenter != null) presenter.notificarMensaje(mensaje);
    }
}
