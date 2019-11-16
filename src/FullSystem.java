import javax.swing.*;
import java.io.*;
import java.util.TreeMap;

public class FullSystem {
    public static void main(String arg[]) {
        new MainMenu().setVisible(true);
    }

    static String getOutputFormat(){
        return "%-10s|%-20s|%-15s|%-30s|%-15s|%-20s|%-12s\n";
    }

    static void writeListToFile(TreeMap List, String entity){
        File file;
        if(entity.equals("student")){
            file = new File(new Student().getFilePath());
        }
        else{
            file = new File(new Lecturer().getFilePath());
        }

        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(List);
        }
        catch(IOException ex){
            JFrame message = new JFrame();
            JOptionPane.showMessageDialog(message, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    static TreeMap<String, Person> getListFromFile(String entity){
        File file;
        if(entity.equals("student")){
            file = new File(new Student().getFilePath());
        }
        else{
            file = new File(new Lecturer().getFilePath());
        }

        if(file.length()>0) {
            try(FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis))
            {
                return (TreeMap) ois.readObject();
            }
            catch(IOException | ClassNotFoundException ex){
                JFrame message = new JFrame();
                JOptionPane.showMessageDialog(message, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);

                System.exit(1);
            }
        }
        return new TreeMap<>();
    }
}

