import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;

class ViewAllMenu extends JFrame {
    private static JScrollPane scrollPane;

    ViewAllMenu(String entity, String key){
        initUI(entity, key);
    }

    ViewAllMenu(String entity){
        initUI(entity, null);
    }

    private void initUI(String entity, String  key){
        JPanel viewAllMenu;
        JTextArea textArea;
        JButton okButton;

        // Create main panel to hold text area
        viewAllMenu = new JPanel();
        textArea = new JTextArea((key==null)?23:5, 57);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        String labelLine = String.format(FullSystem.getOutputFormat(), "ID", "NAME", "DATE OF BIRTH",
                "EMAIL", "PHONE NUMBER", "ADDRESS", (entity.equals("student"))?"BATCH":"DEPARTMENT");
        textArea.append(labelLine);

        TreeMap<String, Person> List = FullSystem.getListFromFile(entity);

        Person s;
        if(key==null){
            Set<String> IDSet = List.keySet();
            for(String ID: IDSet){
                s = List.get(ID);

                String each = String.format(FullSystem.getOutputFormat(), s.getId(), s.getName(), s.getDob(), s.getEmail(), s.getPhone(), s.getAddress(), s.getBatch());
                textArea.append(each);
            }
        }
        else{
            s = List.get(key);

            String each = String.format(FullSystem.getOutputFormat(), s.getId(), s.getName(), s.getDob(), s.getEmail(), s.getPhone(), s.getAddress(), s.getBatch());
            textArea.append(each);
        }

        // Create scrollbar
        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 100, 50);

        // Add everything to main panel
        viewAllMenu.add(scrollPane);
        viewAllMenu.add(okButton = new JButton("OK"));

        // Clicking on the "OK" button or pressing "Escape" or "ENTER" closes the window
        getRootPane().setDefaultButton(okButton);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE | KeyEvent.VK_ENTER, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        okButton.addActionListener(e ->
            dispose()
        );

        setContentPane(viewAllMenu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View all " + entity + "s");
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    public JScrollPane getViewPanel(){
        return scrollPane;
    }
}