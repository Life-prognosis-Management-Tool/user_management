package models;

public class Admin extends User{


    public Admin(String f_name, String l_name, String user_email, String hashedPassword, String UUID) {
        super(f_name, l_name, user_email, hashedPassword, UUID);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
