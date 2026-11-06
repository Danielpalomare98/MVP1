package model;

import presenter.IPresenter;

public interface IModel {
    void setPresenter(IPresenter presenter);
    void iniciar();
    void detener();
    boolean estaEnMarcha();
    Parametros getParametros();
    void configurar(Parametros parametros);
    boolean producirManual(int cantidad);
    boolean consumirManual(int cantidad);
}
