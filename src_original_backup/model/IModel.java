package model;


import presenter.IPresenter;

public interface IModel {
    void setPresenter(IPresenter presenter);
    void iniciar();
    void detener();
}
