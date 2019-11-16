import java.io.*;
import java.util.regex.Pattern;
import javax.swing.*;

abstract class Person implements Serializable{
    protected String name;
    protected String dob;
    protected String email;
    protected String phone;
    protected String address;

    protected abstract void setId(String id);
    protected abstract String getId();
    protected abstract void setBatch(String batch);
    protected abstract String getBatch();
    protected abstract String getFilePath();

    void setName(String name){
        String nameRegex = "([A-Za-z]+ ?)*";
        if(!Pattern.matches(nameRegex, name)){
            throw new IllegalArgumentException("Alphabetic characters only");
        }

        this.name = (name.length()>0)?name:"";
    }
    String getName(){
        return this.name;
    }

    void setDob(String dob){
        String dobRegex = "(\\d{1,2}/\\d{1,2}/\\d{4})?";
        if(!Pattern.matches(dobRegex, dob)){
            throw new IllegalArgumentException("Date of birth must be in format mm/dd/yyyy");
        }

        this.dob = (dob.length()>0)?dob:"";
    }
    String getDob(){
        return this.dob;
    }

    void setEmail(String email){
        String emailRegex = "((\\w+)([.-]?\\w+)*@\\w+([-]?\\w+)?\\.(\\w+([-]?\\w+)?)(\\.\\w+([-]?\\w+)?)*)?";
        if(!Pattern.matches(emailRegex, email)){
            throw new IllegalArgumentException("Invalid email format");
        }

        this.email = (email.length()>0)?email:"";
    }
    String getEmail(){
        return this.email;
    }

    void setPhone(String phone){
        String phoneRegex = "(\\d{4}[ -]?\\d{3}[ -]?\\d{3})?";
        if(!Pattern.matches(phoneRegex, phone)){
            throw new IllegalArgumentException("Phone number must be either xxxx-xxx-xxx or xxxx xxx xxx");
        }

        this.phone = (phone.length()>0)?phone:"";
    }
    String getPhone(){
        return this.phone;
    }

    void setAddress(String address){
        this.address = (address.length()>0)?address:"";
    }
    String getAddress(){
        return this.address;
    }
}

class Student extends Person{
    private String sid;
    private String batch;

    @Override
    public void setId(String sid){
        String sidRegex = "[Gg][TtCc]\\d{5}";
        if(!Pattern.matches(sidRegex, sid)){
            throw new IllegalArgumentException("Student ID must be GTxxxxx");
        }
        if(sid.length()==0){
            throw new NullPointerException("ID cannot be left blank");
        }
        this.sid = sid;
    }
    @Override
    public String getId(){
        return this.sid;
    }

    @Override
    public void setBatch(String batch){
        String batchRegex = "([A-Za-z]{2}\\d{4})?";
        if(!Pattern.matches(batchRegex, batch)){
            throw new IllegalArgumentException("Student batch must be XXxxxx");
        }
        this.batch = (batch.length()>0)?batch:"";
    }
    @Override
    public String getBatch(){
        return this.batch;
    }

    @Override
    public String getFilePath(){
        try{
            File dataFolder = new File("Data");
            if(!dataFolder.exists()){
                if(!dataFolder.mkdirs()){
                    throw new IOException("Failed to create folder");
                }
            }

            File dataFile = new File("Data\\students_list.cps");
            if(!dataFile.exists()){
                if(!dataFile.createNewFile()) {
                    throw new IOException("Failed to create file");
                }
            }
            return dataFile.getAbsolutePath();
        }
        catch(IOException ex){
            JFrame message = new JFrame();
            JOptionPane.showMessageDialog(message, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
        return null;
    }
}

class Lecturer extends Person{
    private String lid;
    private String dept;

    @Override
    public void setId(String lid){
        String sidRegex = "\\d{8}";
        if(!Pattern.matches(sidRegex, lid)){
            throw new IllegalArgumentException("Lecturer ID must be xxxxxxxx");
        }
        if(lid.length()==0){
            throw new NullPointerException("ID cannot be left blank");
        }
        this.lid = lid;
    }
    @Override
    public String getId(){
        return this.lid;
    }

    @Override
    public void setBatch(String dept){
        String batchRegex = "([A-Za-z]+)?";
        if(!Pattern.matches(batchRegex, dept)){
            throw new IllegalArgumentException("Invalid department");
        }
        this.dept = (dept.length()>0)?dept:"";
    }
    @Override
    public String getBatch(){
        return this.dept;
    }

    @Override
    public String getFilePath(){
        try{
            File dataFolder = new File("Data");
            if(!dataFolder.exists()){
                if(!dataFolder.mkdirs()){
                    throw new IOException("Failed to create folder");
                }
            }

            File dataFile = new File("Data\\lecturers_list.cps");
            if(!dataFile.exists()){
                if(!dataFile.createNewFile()) {
                    throw new IOException("Failed to create file");
                }
            }
            return dataFile.getAbsolutePath();
        }
        catch(IOException ex){
            JFrame message = new JFrame();
            JOptionPane.showMessageDialog(message, ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
        return null;
    }
}