package view;

import presenter.IPresenter;

public interface IView {
    void setPresenter(IPresenter presenter);
    void mostrarStock(int stock, int min, int max, int totalProducido, int totalConsumido);
    void mostrarEstadoProductor(String estado);
    void mostrarEstadoConsumidor(String estado);
    void mostrarEnMarcha(boolean enMarcha);
    void mostrarMensaje(String mensaje);
    void mostrarError(String error);
}
