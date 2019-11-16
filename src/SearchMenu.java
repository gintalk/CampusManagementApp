import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Set;

class SearchMenu extends JFrame {
    private static JScrollPane scrollPane;

    SearchMenu(String entity){
        initUI(entity);
    }

    private void initUI(String entity){
        JPanel searchMenu;
        JLabel label;
        JTextField textField;
        JButton searchButton, cancelButton;

        // Create text field to hold search key
        textField = new JTextField(20);
        label = new JLabel("Find what: ", JLabel.TRAILING);
        label.setLabelFor(textField);

        // Add text field the main panel
        searchMenu = new JPanel();
        searchMenu.add(label);
        searchMenu.add(textField);
        searchMenu.add(searchButton = new JButton("Search"));
        searchMenu.add(cancelButton = new JButton("Cancel"));

        // Clicking on the "Search" button or pressing "Enter" initiates search sequence
        getRootPane().setDefaultButton(searchButton);
        searchButton.addActionListener(e -> {
            JTextArea textArea;
            JPanel showStd;

            TreeMap<String, Person> List = FullSystem.getListFromFile(entity);

            String key = textField.getText().toUpperCase();
            boolean found = false;

            // Create text areas to hold search result
            textArea = new JTextArea(5, 57);
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            String labelLine = String.format(FullSystem.getOutputFormat(), "ID", "NAME", "DATE OF BIRTH", "EMAIL", "PHONE NUMBER",
                    "ADDRESS", (entity.equals("student"))?"BATCH":"DEPARTMENT");
            textArea.append(labelLine);

            Set<String> IDSet = List.keySet();
            for(String ID: IDSet){
                Person s = List.get(ID);
                if(s.getName().toUpperCase().contains(key)){
                    found = true;
                    String each = String.format(FullSystem.getOutputFormat(), s.getId(), s.getName(), s.getDob(), s.getEmail(), s.getPhone(), s.getAddress(), s.getBatch());
                    textArea.append(each);
                }
            }

            if(found){
                JButton okButton;

                // Create a scrollbar for the result panel
                scrollPane = new JScrollPane(textArea);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setBounds(50, 30, 300, 50);

                // Add stuff to the result panel
                showStd = new JPanel();
                showStd.add(scrollPane);
                showStd.add(okButton = new JButton("OK"));

                // Show the panel result regardless of match found
                JFrame frame = new JFrame();
                frame.setContentPane(showStd);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setTitle("Search results");
                frame.setSize(500, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Clicking on the "OK" button or pressing "Escape" closes the window
                frame.getRootPane().setDefaultButton(okButton);
                KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
                Action escapeAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                };
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
                getRootPane().getActionMap().put("ESCAPE", escapeAction);
                okButton.addActionListener(e1 ->
                    frame.dispose()
                );
            }
            else{
                JFrame message = new JFrame();
                JOptionPane.showMessageDialog(message, "No " + entity + "s match your search", "Search results", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clicking on the "Cancel" button or pressing "Escape" closes the window
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        cancelButton.addActionListener(e ->
            dispose()
        );

        setContentPane(searchMenu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search " + entity);
        setSize(500, 100);
        setLocationRelativeTo(null);
    }
}