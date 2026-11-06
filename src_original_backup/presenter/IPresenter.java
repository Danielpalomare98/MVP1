package presenter;

public interface IPresenter {
    void iniciarProceso();
    void detenerProceso();
    void notificarDatoConsumido(int dato);
}