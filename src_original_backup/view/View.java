package view;

import presenter.IPresenter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class View extends JFrame implements IView, Runnable {
    private IPresenter presenter;
    private DataPanel dataPanel;
    private ControlPanel controlPanel;
    private Viewer viewer;

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        initUI();
    }

    private void initUI() {
        setTitle("Patrón MVP con Hilos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dataPanel = new DataPanel();
        controlPanel = new ControlPanel(presenter);
        viewer = new Viewer();

        add(dataPanel, BorderLayout.NORTH);
        add(viewer, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        System.out.println("Hilo de la vista en ejecución...");
    }

    @Override
    public void mostrarDato(int dato) {
        SwingUtilities.invokeLater(() -> {
            dataPanel.actualizarDato(dato);
            viewer.agregarLog("Procesado dato: " + dato);
        });
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> viewer.agregarLog(mensaje));
    }
}