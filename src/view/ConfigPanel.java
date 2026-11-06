package view;

import model.Parametros;
import presenter.IPresenter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.ParseException;

/** Panel izquierdo: parámetros de producción, consumo y stock. */
public class ConfigPanel extends JPanel {
    private final JSpinner spMinProd = spinner(1, 1, 1000, 1);
    private final JSpinner spMaxProd = spinner(5, 1, 1000, 1);
    private final JSpinner spMinCons = spinner(1, 1, 1000, 1);
    private final JSpinner spMaxCons = spinner(4, 1, 1000, 1);
    private final JSpinner spStockMin = spinner(5, 0, 10000, 1);
    private final JSpinner spStockMax = spinner(30, 1, 10000, 1);
    private final JSpinner spVelProd = spinner(800, 100, 10000, 100);
    private final JSpinner spVelCons = spinner(1200, 100, 10000, 100);
    private final JButton btnAplicar = new JButton("Aplicar parámetros");
    private final JButton btnRestaurar = new JButton("Valores por defecto");

    public ConfigPanel(IPresenter presenter) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Parámetros"));

        JPanel campos = new JPanel(new GridLayout(0, 2, 6, 6));
        campos.add(new JLabel("Producción mínima:"));  campos.add(spMinProd);
        campos.add(new JLabel("Producción máxima:"));  campos.add(spMaxProd);
        campos.add(new JLabel("Consumo mínimo:"));     campos.add(spMinCons);
        campos.add(new JLabel("Consumo máximo:"));     campos.add(spMaxCons);
        campos.add(new JLabel("Stock mínimo:"));       campos.add(spStockMin);
        campos.add(new JLabel("Stock máximo:"));       campos.add(spStockMax);
        campos.add(new JLabel("Vel. productor (ms):")); campos.add(spVelProd);
        campos.add(new JLabel("Vel. consumidor (ms):")); campos.add(spVelCons);

        JPanel botones = new JPanel(new GridLayout(2, 1, 4, 4));
        botones.add(btnAplicar);
        botones.add(btnRestaurar);

        add(campos, BorderLayout.NORTH);
        add(botones, BorderLayout.SOUTH);

        if (presenter != null) cargar(presenter.getParametros());

        btnAplicar.addActionListener(e -> {
            if (presenter != null) presenter.aplicarParametros(leer());
        });
        btnRestaurar.addActionListener(e -> {
            Parametros p = Parametros.porDefecto();
            cargar(p);
            if (presenter != null) presenter.aplicarParametros(p);
        });
    }

    private static JSpinner spinner(int valor, int min, int max, int paso) {
        return new JSpinner(new SpinnerNumberModel(valor, min, max, paso));
    }

    private static int valor(JSpinner s) {
        try {
            s.commitEdit(); // por si el usuario ha escrito el número sin pulsar Enter
        } catch (ParseException ignored) {
            // si el texto no es válido se queda el último valor correcto
        }
        return (Integer) s.getValue();
    }

    private Parametros leer() {
        return new Parametros(valor(spMinProd), valor(spMaxProd),
                valor(spMinCons), valor(spMaxCons),
                valor(spStockMin), valor(spStockMax),
                valor(spVelProd), valor(spVelCons));
    }

    private void cargar(Parametros p) {
        spMinProd.setValue(p.minProduccion);
        spMaxProd.setValue(p.maxProduccion);
        spMinCons.setValue(p.minConsumo);
        spMaxCons.setValue(p.maxConsumo);
        spStockMin.setValue(p.stockMinimo);
        spStockMax.setValue(p.stockMaximo);
        spVelProd.setValue(p.velocidadProduccion);
        spVelCons.setValue(p.velocidadConsumo);
    }
}
