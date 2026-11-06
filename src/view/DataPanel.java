package view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/** Panel superior: stock actual, límites, totales y estado de cada hilo. */
public class DataPanel extends JPanel {
    private final JLabel lblStock = new JLabel("Stock: -", JLabel.CENTER);
    private final JProgressBar barraStock = new JProgressBar();
    private final JLabel lblMin = new JLabel("Stock mínimo: -");
    private final JLabel lblMax = new JLabel("Stock máximo: -");
    private final JLabel lblProducido = new JLabel("Total producido: 0");
    private final JLabel lblConsumido = new JLabel("Total consumido: 0");
    private final JLabel lblProductor = new JLabel("Productor: Detenido");
    private final JLabel lblConsumidor = new JLabel("Consumidor: Detenido");
    private final JLabel lblEstado = new JLabel("Proceso: DETENIDO");

    public DataPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Estado del almacén"));

        lblStock.setFont(lblStock.getFont().deriveFont(Font.BOLD, 22f));
        barraStock.setStringPainted(true);

        JPanel arriba = new JPanel(new BorderLayout(5, 5));
        arriba.add(lblStock, BorderLayout.NORTH);
        arriba.add(barraStock, BorderLayout.CENTER);

        JPanel datos = new JPanel(new GridLayout(3, 3, 10, 4));
        datos.add(lblMin);
        datos.add(lblProducido);
        datos.add(lblProductor);
        datos.add(lblMax);
        datos.add(lblConsumido);
        datos.add(lblConsumidor);
        datos.add(lblEstado);

        lblEstado.setForeground(Color.RED.darker());
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD));

        add(arriba, BorderLayout.NORTH);
        add(datos, BorderLayout.CENTER);
    }

    public void actualizarStock(int stock, int min, int max, int totalProducido, int totalConsumido) {
        lblStock.setText("Stock: " + stock + " uds");
        lblMin.setText("Stock mínimo: " + min);
        lblMax.setText("Stock máximo: " + max);
        lblProducido.setText("Total producido: " + totalProducido);
        lblConsumido.setText("Total consumido: " + totalConsumido);

        barraStock.setMinimum(min);
        barraStock.setMaximum(max);
        barraStock.setValue(Math.max(min, Math.min(max, stock)));
        barraStock.setString(stock + " / " + max + "  (mín. " + min + ")");

        if (stock >= max) barraStock.setForeground(new Color(200, 120, 0));      // lleno
        else if (stock <= min) barraStock.setForeground(new Color(190, 40, 40)); // en el mínimo
        else barraStock.setForeground(new Color(40, 140, 60));                   // normal
    }

    public void actualizarProductor(String estado) {
        lblProductor.setText("Productor: " + estado);
    }

    public void actualizarConsumidor(String estado) {
        lblConsumidor.setText("Consumidor: " + estado);
    }

    public void actualizarEnMarcha(boolean enMarcha) {
        lblEstado.setText(enMarcha ? "Proceso: EN MARCHA" : "Proceso: DETENIDO");
        lblEstado.setForeground(enMarcha ? new Color(40, 140, 60) : Color.RED.darker());
    }
}
