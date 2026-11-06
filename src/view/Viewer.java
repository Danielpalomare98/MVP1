package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Panel central: registro de todo lo que pasa (sustituye a la consola). */
public class Viewer extends JPanel {
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final JTextArea areaLog = new JTextArea(12, 40);

    public Viewer() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro"));
        areaLog.setEditable(false);
        add(new JScrollPane(areaLog), BorderLayout.CENTER);

        JButton btnLimpiar = new JButton("Limpiar registro");
        btnLimpiar.addActionListener(e -> areaLog.setText(""));
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.add(btnLimpiar);
        add(sur, BorderLayout.SOUTH);
    }

    public void agregarLog(String mensaje) {
        areaLog.append("[" + LocalTime.now().format(HORA) + "] " + mensaje + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength()); // auto-scroll
    }
}
