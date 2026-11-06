package presenter;

import model.Parametros;

public interface IPresenter {
    // Acciones que pide la vista
    void iniciarProceso();
    void detenerProceso();
    void aplicarParametros(Parametros parametros);
    void producirManual(int cantidad);
    void consumirManual(int cantidad);
    Parametros getParametros();

    // Avisos que manda el modelo
    void notificarStock(int stock, int min, int max, int totalProducido, int totalConsumido);
    void notificarEstadoProductor(String estado);
    void notificarEstadoConsumidor(String estado);
    void notificarMensaje(String mensaje);
}
