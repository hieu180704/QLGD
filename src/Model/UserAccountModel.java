package Model;

public class UserAccountModel {
    private String username;
    private String password;
    private String email;

    public UserAccountModel() {}

    public UserAccountModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getter/setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
  
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
