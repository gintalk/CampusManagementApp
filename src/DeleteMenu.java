import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.TreeMap;

class DeleteMenu extends JFrame {
    private JScrollPane textArea;

    DeleteMenu(String entity){
        initUI(entity);
    }

    private void initUI(String entity){
        JPanel deleteMenu;
        JTextField textField;
        JLabel label;
        JButton deleteButton, cancelButton;

        textArea = new ViewAllMenu(entity).getViewPanel();

        // Create text field to hold search key
        textField = new JTextField(20);
        label = new JLabel("ID: ", JLabel.TRAILING);
        label.setLabelFor(textField);

        // Add everything to the main panel
        deleteMenu = new JPanel();
        deleteMenu.add(label);
        deleteMenu.add(textField);
        deleteMenu.add(deleteButton = new JButton("Delete"));
        deleteMenu.add(cancelButton = new JButton("Cancel"));
        deleteMenu.add(textArea);

        // Clicking on the "Delete" button or pressing "Enter" initiates delete sequence
        getRootPane().setDefaultButton(deleteButton);
        deleteButton.addActionListener(e -> {
            TreeMap<String, Person> List = FullSystem.getListFromFile(entity);

            boolean change = false;
            String key = textField.getText().toUpperCase();
            JFrame message = new JFrame();

            if(List.containsKey(key)){
                List.remove(key);

                JOptionPane.showMessageDialog(message,  entity.substring(0, 1).toUpperCase() + entity.substring(1) + " deleted",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                change = true;
            }
            else{
                JOptionPane.showMessageDialog(message, "No " + entity + " match your search", "Search results", JOptionPane.INFORMATION_MESSAGE);
            }

            if(change){
                FullSystem.writeListToFile(List, entity);

                deleteMenu.remove(textArea);
                textArea = new ViewAllMenu(entity).getViewPanel();
                deleteMenu.add(textArea);
                SwingUtilities.updateComponentTreeUI(deleteMenu);
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

        setContentPane(deleteMenu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delete " + entity);
        setSize(500, 520);
        setLocationRelativeTo(null);
    }
}
