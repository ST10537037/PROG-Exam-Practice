import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

    public class MovieTicketsGUI extends JFrame {

        private JComboBox<String> movieComboBox;
        private JTextField ticketsField;
        private JTextField priceField;
        private JTextArea reportArea;

        private final MovieTickets movieTickets = new MovieTickets();

        public MovieTicketsGUI() {
            setTitle("MOVIE TICKETS");
            setSize(470, 430);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(new Color(215, 219, 225));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            movieComboBox = new JComboBox<>(
                    new String[]{"Napoleon", "Oppenheimer", "Damsel"}
            );

            ticketsField = new JTextField(15);
            priceField = new JTextField(15);

            reportArea = new JTextArea(7, 30);
            reportArea.setEditable(false);
            reportArea.setLineWrap(true);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("MOVIE:"), gbc);

            gbc.gridx = 1;
            panel.add(movieComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("NUMBER OF TICKETS:"), gbc);

            gbc.gridx = 1;
            panel.add(ticketsField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("TICKET PRICE:"), gbc);

            gbc.gridx = 1;
            panel.add(priceField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            panel.add(new JLabel("TICKET REPORT:"), gbc);

            gbc.gridy = 4;
            panel.add(new JScrollPane(reportArea), gbc);

            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenuItem exitItem = new JMenuItem("Exit");

            exitItem.addActionListener(e -> System.exit(0));
            fileMenu.add(exitItem);

            JMenu toolsMenu = new JMenu("Tools");
            JMenuItem processItem = new JMenuItem("Process");
            JMenuItem clearItem = new JMenuItem("Clear");

            processItem.addActionListener(e -> processTickets());
            clearItem.addActionListener(e -> clearFields());

            toolsMenu.add(processItem);
            toolsMenu.add(clearItem);

            menuBar.add(fileMenu);
            menuBar.add(toolsMenu);
            setJMenuBar(menuBar);

            add(panel);
        }

        private void processTickets() {
            try {
                String movie = (String) movieComboBox.getSelectedItem();
                int number = Integer.parseInt(ticketsField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                MovieTicketData data =
                        new MovieTicketData(movie, price, number);

                if (!movieTickets.ValidateData(data)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid ticket details."
                    );
                    return;
                }

                double total =
                        movieTickets.CalculateTotalTicketPrice(number, price);

                String report = String.format(
                        "MOVIE NAME: %s%n" +
                                "MOVIE TICKET PRICE: R %.1f%n" +
                                "NUMBER OF TICKETS: %d%n" +
                                "TOTAL TICKET PRICE: R %.1f",
                        movie, price, number, total
                );

                reportArea.setText(report);

                saveReport(report);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid whole number for tickets " +
                                "and a valid numeric ticket price."
                );
            }
        }

        private void saveReport(String report) {
            try (FileWriter writer = new FileWriter("report.txt")) {
                writer.write("MOVIE TICKET REPORT\n");
                writer.write("***********************************\n");
                writer.write(report);
                writer.write("\n***********************************\n");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error saving report: " + ex.getMessage()
                );
            }
        }

        private void clearFields() {
            movieComboBox.setSelectedIndex(0);
            ticketsField.setText("");
            priceField.setText("");
            reportArea.setText("");
        }


}
