package praktikum.user;

public class LoginUser {
    private String email;
    private String password;

    public LoginUser(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public static LoginUser from(User user) {
        return new LoginUser(user.getEmail(), user.getPassword());

    }

    public LoginUser(User user) {

        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
