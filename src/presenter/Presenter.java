package presenter;

import model.IModel;
import model.Parametros;
import view.IView;

public class Presenter implements IPresenter {
    private final IView view;
    private final IModel model;

    public Presenter(IView view, IModel model) {
        this.view = view;
        this.model = model;

        this.view.setPresenter(this);
        this.model.setPresenter(this);
    }

    @Override
    public void iniciarProceso() {
        if (model.estaEnMarcha()) {
            view.mostrarMensaje("El proceso ya está en marcha.");
            return;
        }
        view.mostrarMensaje("--> Proceso iniciado");
        model.iniciar();
        view.mostrarEnMarcha(true);
    }

    @Override
    public void detenerProceso() {
        if (!model.estaEnMarcha()) {
            view.mostrarMensaje("El proceso no está en marcha.");
            return;
        }
        model.detener();
        view.mostrarMensaje("--> Proceso detenido");
        view.mostrarEnMarcha(false);
    }

    @Override
    public void aplicarParametros(Parametros p) {
        String error = p.validar();
        if (error != null) {
            view.mostrarError(error);
            return;
        }
        model.configurar(p);
        view.mostrarMensaje("--> Parámetros aplicados: producción " + p.minProduccion + "-" + p.maxProduccion
                + ", consumo " + p.minConsumo + "-" + p.maxConsumo
                + ", stock " + p.stockMinimo + "-" + p.stockMaximo);
    }

    @Override
    public void producirManual(int cantidad) {
        if (model.producirManual(cantidad)) {
            view.mostrarMensaje("[Manual] Producidas " + cantidad + " uds");
        } else {
            view.mostrarError("No se pueden producir " + cantidad + " uds: se superaría el stock máximo ("
                    + model.getParametros().stockMaximo + ").");
        }
    }

    @Override
    public void consumirManual(int cantidad) {
        if (model.consumirManual(cantidad)) {
            view.mostrarMensaje("[Manual] Consumidas " + cantidad + " uds");
        } else {
            view.mostrarError("No se pueden consumir " + cantidad + " uds: se bajaría del stock mínimo ("
                    + model.getParametros().stockMinimo + ").");
        }
    }

    @Override
    public Parametros getParametros() {
        return model.getParametros();
    }

    @Override
    public void notificarStock(int stock, int min, int max, int totalProducido, int totalConsumido) {
        view.mostrarStock(stock, min, max, totalProducido, totalConsumido);
    }

    @Override
    public void notificarEstadoProductor(String estado) {
        view.mostrarEstadoProductor(estado);
    }

    @Override
    public void notificarEstadoConsumidor(String estado) {
        view.mostrarEstadoConsumidor(estado);
    }

    @Override
    public void notificarMensaje(String mensaje) {
        view.mostrarMensaje(mensaje);
    }
}
