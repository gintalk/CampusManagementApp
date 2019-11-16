import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

class MainMenu extends JFrame{
    private JPanel mainPanel;

    MainMenu(){
        initUI();
    }

    private void initUI(){
        JButton manageStudentsButton, manageLecturersButton, exitButton;

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex){}

        // Create main panel to hold buttons
        mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(manageStudentsButton = new JButton("Manage students"));
        mainPanel.add(manageLecturersButton = new JButton("Manage lecturers"));
        mainPanel.add(exitButton = new JButton("Exit"));

        manageStudentsButton.addActionListener(e -> {
            setContentPane(getSecondMainMenu("student"));
            SwingUtilities.updateComponentTreeUI(this);
        });

        manageLecturersButton.addActionListener(e -> {
            setContentPane(getSecondMainMenu("lecturer"));
            SwingUtilities.updateComponentTreeUI(this);
        });

        // Clicking on the "Cancel" button or pressing "Escape" closes the window
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        exitButton.addActionListener(e ->
            System.exit(0)
        );

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Campus System");
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private JPanel getSecondMainMenu(String entity){
        JButton addNewButton, viewAllButton, searchButton, deleteButton, updateButton, backToMainMenuButton;

        // Create a panel to hold buttons
        JPanel p0 = new JPanel(new GridLayout(6, 1, 1, 1));
        p0.add(addNewButton = new JButton("Add new " + entity));
        p0.add(viewAllButton = new JButton("View all " + entity + "s"));
        p0.add(searchButton = new JButton("Search " + entity));
        p0.add(deleteButton = new JButton("Delete " + entity));
        p0.add(updateButton = new JButton("Update " + entity));
        p0.add(backToMainMenuButton = new JButton("Back to main menu"));

        // Add panels to frame
        JPanel secondMainPanel = new JPanel(new GridLayout(1, 1));
        secondMainPanel.add(p0, BorderLayout.CENTER);

        addNewButton.addActionListener(a ->
                new AddMenu(entity).setVisible(true)
        );

        viewAllButton.addActionListener(a ->
                new ViewAllMenu(entity).setVisible(true)
        );

        searchButton.addActionListener(a ->
                new SearchMenu(entity).setVisible(true)
        );

        deleteButton.addActionListener(a ->
                new DeleteMenu(entity).setVisible(true)
        );

        updateButton.addActionListener(a ->
                new UpdateMenu(entity).setVisible(true)
        );

        backToMainMenuButton.addActionListener(a -> {
            setContentPane(mainPanel);
            SwingUtilities.updateComponentTreeUI(this);
        });

        return secondMainPanel;
    }
}
