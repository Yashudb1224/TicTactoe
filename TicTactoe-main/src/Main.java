import backend.UserManager;
import ui.LoginUI;

public class Main {
    public static void main(String[] args) {
        // Set look and feel for better UI
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Use default look and feel
        }

        // Initialize UserManager and start with LoginUI
        UserManager userManager = new UserManager();
        new LoginUI(userManager);
    }
}