package presenter;

import model.IModel;
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
        view.mostrarMensaje("--> Proceso iniciado");
        model.iniciar();
    }

    @Override
    public void detenerProceso() {
        model.detener();
        view.mostrarMensaje("--> Proceso detenido");
    }

    @Override
    public void notificarDatoConsumido(int dato) {
        view.mostrarDato(dato);
    }
}