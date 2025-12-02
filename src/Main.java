import backend.UserManager;
import ui.LoginUI;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        new LoginUI(userManager);
    }
}
