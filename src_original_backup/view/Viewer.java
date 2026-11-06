package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Viewer extends JPanel {
    private final JTextArea areaLog = new JTextArea(8, 25);

    public Viewer() {
        areaLog.setEditable(false);
        add(new JScrollPane(areaLog));
    }

    public void agregarLog(String mensaje) {
        areaLog.append(mensaje + "\n");
    }
}
