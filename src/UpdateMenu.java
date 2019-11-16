import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;

class UpdateMenu extends JFrame {
    private JScrollPane textArea;

    UpdateMenu(String entity){
        initUI(entity);
    }

    private void initUI(String entity){
        JPanel updateMenu;
        JTextField textField;
        JLabel label;
        JButton searchButton, cancelButton;

        // Create main panel
        updateMenu = new JPanel();

        // Create text field and label
        textField = new JTextField(20);
        label = new JLabel("ID: ", JLabel.TRAILING);
        label.setLabelFor(textField);

        // Add stuff to main panel
        updateMenu.add(label);
        updateMenu.add(textField);
        updateMenu.add(searchButton = new JButton("Search first"));
        updateMenu.add(cancelButton = new JButton("Cancel"));

        // Clicking on the "Search first" button or pressing "Enter" initiates search sequence
        getRootPane().setDefaultButton(searchButton);
        searchButton.addActionListener(e -> {
            String key = textField.getText().toUpperCase();
            TreeMap<String, Person> List = FullSystem.getListFromFile(entity);
            JFrame message = new JFrame();

            if (List.containsKey(key)) {
                JButton updateButton, subCancelButton;

                Person p = List.get(key);
                List.remove(p.getId());
                String[] fields = new String[6];
                fields[0] = p.getName();
                fields[1] = p.getDob();
                fields[2] = p.getEmail();
                fields[3] = p.getPhone();
                fields[4] = p.getAddress();
                fields[5] = p.getBatch();

                String[] Labels = {"Name: ", "Date of Birth: ", "Email: ", "Phone: ", "Address: ", (entity.equals("student")) ? "Batch: " : "Department: "};
                JTextField[] textFields = new JTextField[Labels.length];

                // Create a panel to hold text fields
                JPanel p0 = new JPanel(new GridLayout(7, 1, 10, 15));
                JTextField field;
                for (int i = 0; i < Labels.length; i++) {
                    JLabel lb = new JLabel(Labels[i], JLabel.TRAILING);
                    p0.add(lb);
                    field = new JTextField(fields[i], 20);
                    lb.setLabelFor(field);
                    p0.add(field);
                    textFields[i] = field;
                }
                textArea = new ViewAllMenu(entity, key).getViewPanel();

                // Add everything to the main panel
                JPanel panel = new JPanel();
                panel.add(p0);
                panel.add(updateButton = new JButton("Update"));
                panel.add(subCancelButton = new JButton("Cancel"));
                panel.add(textArea);

                JFrame frame = new JFrame();
                frame.setContentPane(panel);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setTitle("Update " + entity);
                frame.setSize(500, 450);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                final TreeMap<String, Person> newList = List;
                // Clicking on the "Update" button or pressing "Enter" initiates update sequence
                frame.getRootPane().setDefaultButton(updateButton);
                updateButton.addActionListener(ae -> {
                    for (int i = 0; i < textFields.length; i++) {
                        String newEntry = textFields[i].getText();
                        newEntry = (newEntry.length() > 0) ? newEntry : "";

                        switch (i) {
                            case 0:
                                if (!newEntry.equals(p.getName())) {
                                    try {
                                        p.setName(newEntry);
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid name format", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                break;
                            case 1:
                                if (!newEntry.equals(p.getDob())) {
                                    try {
                                        p.setDob(newEntry);
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid date format", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                break;
                            case 2:
                                if (!newEntry.equals(p.getEmail())) {
                                    try {
                                        p.setEmail(newEntry);
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid email format", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                break;
                            case 3:
                                if (!newEntry.equals(p.getPhone())) {
                                    try {
                                        p.setPhone(newEntry);
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid phone number format", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                break;
                            case 4:
                                if (!newEntry.equals(p.getAddress())) {
                                    p.setAddress(newEntry);
                                }
                                break;
                            case 5:
                                if (!newEntry.equals(p.getBatch())) {
                                    try {
                                        p.setBatch(newEntry);
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid " + ((entity.equals("student")) ? "batch" : "department"), JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                break;
                        }
                    }
                    newList.put(p.getId(), p);

                    FullSystem.writeListToFile(newList, entity);
                    JOptionPane.showMessageDialog(message, entity.substring(0, 1).toUpperCase() + entity.substring(1) + " updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                    UpdateMenu.this.dispose();

                    panel.remove(textArea);
                    textArea = new ViewAllMenu(entity, key).getViewPanel();
                    panel.add(textArea);
                    SwingUtilities.updateComponentTreeUI(frame);
                });

                // Clicking on the "Cancel" button or pressing "Escape" closes the window
                KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
                Action escapeAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                };
                frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
                frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
                subCancelButton.addActionListener(ae ->
                        frame.dispose()
                );
            } else {
                JOptionPane.showMessageDialog(message, "No " + entity + " match your search", "Search results", JOptionPane.ERROR_MESSAGE);
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

        setContentPane(updateMenu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update " + entity);
        setSize(500, 100);
        setLocationRelativeTo(null);
    }
}
