package view;

import presenter.IPresenter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;
import java.text.ParseException;

/** Panel inferior: iniciar/detener y producir/consumir a mano. */
public class ControlPanel extends JPanel {
    private final JButton btnIniciar = new JButton("Iniciar");
    private final JButton btnDetener = new JButton("Detener");
    private final JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private final JButton btnProducir = new JButton("Producir ahora");
    private final JButton btnConsumir = new JButton("Consumir ahora");

    public ControlPanel(IPresenter presenter) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        setBorder(BorderFactory.createTitledBorder("Control"));

        add(btnIniciar);
        add(btnDetener);
        add(new JLabel("   Cantidad manual:"));
        add(spCantidad);
        add(btnProducir);
        add(btnConsumir);

        btnDetener.setEnabled(false);

        btnIniciar.addActionListener(e -> {
            if (presenter != null) presenter.iniciarProceso();
        });
        btnDetener.addActionListener(e -> {
            if (presenter != null) presenter.detenerProceso();
        });
        btnProducir.addActionListener(e -> {
            if (presenter != null) presenter.producirManual(cantidad());
        });
        btnConsumir.addActionListener(e -> {
            if (presenter != null) presenter.consumirManual(cantidad());
        });
    }

    private int cantidad() {
        try {
            spCantidad.commitEdit();
        } catch (ParseException ignored) {
        }
        return (Integer) spCantidad.getValue();
    }

    public void actualizarEnMarcha(boolean enMarcha) {
        btnIniciar.setEnabled(!enMarcha);
        btnDetener.setEnabled(enMarcha);
    }
}
