import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import java.util.TreeMap;

class AddMenu extends JFrame{
    AddMenu(String entity){
        initUI(entity);
    }

    private void initUI(String entity){
        JPanel addMenu;
        JButton addButton, cancelButton;

        String[] Labels = {"ID*: ", "Name: ", "Date of Birth: ", "Email: ", "Phone number: ", "Address: ", (entity.equals("student"))?"Batch: ":"Department: "};
        JTextField[] Fields = new JTextField[Labels.length];

        // Create a panel to hold text fields
        JPanel p0 = new JPanel(new GridLayout(7, 1, 10, 15));
        for(int i=0; i<Labels.length; i++){
            JLabel lb = new JLabel(Labels[i], JLabel.TRAILING);
            p0.add(lb);
            JTextField field = new JTextField(20);
            lb.setLabelFor(field);
            p0.add(field);
            Fields[i] = field;
        }

        // Add everything to the main panel
        addMenu = new JPanel();
        addMenu.add(p0);
        addMenu.add(addButton = new JButton("Add"));
        addMenu.add(cancelButton = new JButton("Cancel"));


        // Clicking on the "Add" button or pressing "Enter" initiates add sequence
        getRootPane().setDefaultButton(addButton);
        addButton.addActionListener(e -> {
            Person person;
            if(entity.equals("student")){
                person = new Student();
            }
            else{
                person = new Lecturer();
            }

            TreeMap<String, Person> List = FullSystem.getListFromFile(entity);

            boolean successful = true;
            JFrame message = new JFrame();

            // Get input: ID
            String id = Fields[0].getText().toUpperCase();
            if(List.containsKey(id)){
                JOptionPane.showMessageDialog(message, "Duplicate ID detected", "ID must be unique",
                        JOptionPane.ERROR_MESSAGE);
                successful = false;
            }
            else{
                try{
                    person.setId(id);
                }
                catch(IllegalArgumentException | NullPointerException ex){
                    JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid ID format", JOptionPane.ERROR_MESSAGE);
                    successful = false;
                }
            }

            // Get input: Name
            String name = Fields[1].getText();
            try {
                person.setName(name);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid name format", JOptionPane.ERROR_MESSAGE);
                successful = false;
            }

            // Get input: DoB
            String dob = Fields[2].getText();
            try {
                person.setDob(dob);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid date format", JOptionPane.ERROR_MESSAGE);
                successful = false;
            }

            // Get input: Email
            String email = Fields[3].getText();
            try {
                person.setEmail(email);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(message, ex.getMessage(),
                        "Invalid email format", JOptionPane.ERROR_MESSAGE);
                successful = false;
            }

            // Get input: Phone number
            String phone = Fields[4].getText();
            try {
                person.setPhone(phone);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid phone number format", JOptionPane.ERROR_MESSAGE);
                successful = false;
            }

            // Get input: Address
            String address = Fields[5].getText();
            person.setAddress(address);

             // Get input: Batch
            String batch = Fields[6].getText();
            try {
                person.setBatch(batch);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(message, ex.getMessage(), "Invalid " + ((entity.equals("student"))?"batch":"department"), JOptionPane.ERROR_MESSAGE);
                successful = false;
            }

            if(successful){
                List.put(id, person);

                FullSystem.writeListToFile(List, entity);
                JOptionPane.showMessageDialog(message, "New "+ entity + " added", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
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

        setContentPane(addMenu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add " + entity);
        setSize(500, 330);
        setLocationRelativeTo(null);
    }
}
