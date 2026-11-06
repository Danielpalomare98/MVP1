package view;

import presenter.IPresenter;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    private final JButton btnIniciar = new JButton("Iniciar");
    private final JButton btnDetener = new JButton("Detener");

    public ControlPanel(IPresenter presenter) {
        add(btnIniciar);
        add(btnDetener);

        btnIniciar.addActionListener(e -> {
            if (presenter != null) presenter.iniciarProceso();
        });

        btnDetener.addActionListener(e -> {
            if (presenter != null) presenter.detenerProceso();
        });
    }
}