package view;

import presenter.IPresenter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class View extends JFrame implements IView, Runnable {
    private IPresenter presenter;
    private DataPanel dataPanel;
    private ConfigPanel configPanel;
    private ControlPanel controlPanel;
    private Viewer viewer;

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        initUI();
    }

    private void initUI() {
        setTitle("Productor - Consumidor (MVP con hilos)");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        dataPanel = new DataPanel();
        configPanel = new ConfigPanel(presenter);
        controlPanel = new ControlPanel(presenter);
        viewer = new Viewer();

        add(dataPanel, BorderLayout.NORTH);
        add(configPanel, BorderLayout.WEST);
        add(viewer, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        mostrarMensaje("Hilo de la vista en ejecución. Pulsa 'Iniciar' para empezar.");
    }

    @Override
    public void mostrarStock(int stock, int min, int max, int totalProducido, int totalConsumido) {
        SwingUtilities.invokeLater(() -> dataPanel.actualizarStock(stock, min, max, totalProducido, totalConsumido));
    }

    @Override
    public void mostrarEstadoProductor(String estado) {
        SwingUtilities.invokeLater(() -> dataPanel.actualizarProductor(estado));
    }

    @Override
    public void mostrarEstadoConsumidor(String estado) {
        SwingUtilities.invokeLater(() -> dataPanel.actualizarConsumidor(estado));
    }

    @Override
    public void mostrarEnMarcha(boolean enMarcha) {
        SwingUtilities.invokeLater(() -> {
            dataPanel.actualizarEnMarcha(enMarcha);
            controlPanel.actualizarEnMarcha(enMarcha);
        });
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> viewer.agregarLog(mensaje));
    }

    @Override
    public void mostrarError(String error) {
        SwingUtilities.invokeLater(() -> {
            viewer.agregarLog("ERROR: " + error);
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
}
