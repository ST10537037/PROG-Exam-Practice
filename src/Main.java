//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.import javax.swing.SwingUtilities;

import javax.swing.*;

public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                MovieTicketsGUI gui = new MovieTicketsGUI();
                gui.setVisible(true);
            });
        }


}
