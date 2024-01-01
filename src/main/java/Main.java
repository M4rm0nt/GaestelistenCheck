import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class Main {

    public static void main(String[] args) {

        // Gästeliste (Beispiel) mit vollständigen Geburtsdaten
        Map<String, LocalDate> guestList = new HashMap<>();
        guestList.put("Meier", LocalDate.of(1985, 6, 15));
        guestList.put("Schmidt", LocalDate.of(1995, 3, 20));
        guestList.put("Müller", LocalDate.of(2000, 1, 5));

        // Erstellung des Hauptfensters
        JFrame frame = new JFrame("Gästelistenchecker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLayout(new BorderLayout(5, 5));

        // Erstellen eines Panels für die Eingabefelder
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid-Layout für Ordnung und Übersichtlichkeit
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // UI-Komponenten
        inputPanel.add(new JLabel("Nachname:"));
        JTextField nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Geburtsjahr:"));
        JTextField yearField = new JTextField(4);
        inputPanel.add(yearField);

        inputPanel.add(new JLabel("Monat:"));
        JTextField monthField = new JTextField(2);
        inputPanel.add(monthField);

        inputPanel.add(new JLabel("Tag:"));
        JTextField dayField = new JTextField(2);
        inputPanel.add(dayField);

        // Button und Ergebnis-Label
        JButton checkButton = new JButton("Überprüfen");
        checkButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkButton.setForeground(Color.WHITE);
        checkButton.setBackground(Color.BLUE);
        checkButton.setToolTipText("Klicken Sie hier, um die Eingabe zu überprüfen");

        JLabel resultLabel = new JLabel("Ergebnis: ");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // BiPredicate zur Überprüfung der Volljährigkeit
        BiPredicate<String, LocalDate> isAllowed = (name, birthDate) -> {
            if (!guestList.containsKey(name)) {
                return false;
            }
            LocalDate listedBirthDate = guestList.get(name);
            return listedBirthDate.equals(birthDate) &&
                    LocalDate.now().minusYears(18).isAfter(listedBirthDate) ||
                    LocalDate.now().minusYears(18).isEqual(listedBirthDate);
        };

        // Ereignisbehandlung für den Button
        checkButton.addActionListener(e -> {
            String name = nameField.getText();
            try {
                int year = Integer.parseInt(yearField.getText());
                int month = Integer.parseInt(monthField.getText());
                int day = Integer.parseInt(dayField.getText());
                LocalDate birthDate = LocalDate.of(year, month, day);
                boolean allowed = isAllowed.test(name, birthDate);
                resultLabel.setText("Ergebnis: " + (allowed ? "Zugelassen" : "Nicht zugelassen"));
            } catch (NumberFormatException | java.time.DateTimeException ex) {
                resultLabel.setText("Ergebnis: Ungültiges Datum");
            }
        });

        // Hinzufügen der Komponenten zum Hauptfenster
        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(checkButton);
        bottomPanel.add(resultLabel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Frame konfigurieren und anzeigen
        frame.setVisible(true);
    }
}
