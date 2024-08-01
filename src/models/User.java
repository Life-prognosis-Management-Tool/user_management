package models;

abstract class User {
    private String f_name;
    private String l_name;
    private String user_email;
    private String user_password;


    public User(String f_name, String l_name, String user_email, String user_password) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
//    public abstract int calculateSurvivalAge();

}
