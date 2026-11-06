
import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IView view = new View();
            IModel model = new Model();
            IPresenter presenter = new Presenter(view, model);

            // Inicia el hilo de la vista según especifica el diagrama UML
            new Thread((Runnable) view).start();
        });
    }
}