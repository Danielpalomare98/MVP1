package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DataPanel extends JPanel {
    private final JLabel lblDato = new JLabel("Último dato: -");

    public DataPanel() {
        add(lblDato);
    }

    public void actualizarDato(int dato) {
        lblDato.setText("Último dato consumido: " + dato);
    }
}