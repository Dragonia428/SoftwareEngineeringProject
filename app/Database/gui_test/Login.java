import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.ActionEvent;
import java.awt.ActionListener;
import java.awt.FlowLayout;


class Login extends JFrame {
	
    private final JButton loginButton, createAccountButton, registerButton;
    private final Jlabel fNameLabel, lNameLabel, emailLabel, usernameLabel, passwordLabel, rePasswordLabel;
    private final JTextField fNameField, lNameField, emailField, usernameField;
    private final JPasswordField passwordField, rePasswordField;

    public Login() {
        super("Login");
        setLayout(new FlowLayout());
        fNameLabel = new JLabel("First Name");
        lNameLabel = new JLabel("Last Name");
        emailLabel = new JLabel("Email");
        usernameLabel = new JLabel("Username");
        passwordlabel = new JLabel("Password");
        rePasswordLabel = new JLabel("Retype Password");
        registerButton = new JButton("Register");
        fNameField = new JTextField(20);
        lNameField = new JTextField(20);
        emailField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        rePasswordField = new JTextField(20);
        
        prepareFrameForLogin();
    }

    private void prepareFrameForLogin() {
        this.getContentPane().removeAll()
        add( usernameLabel );
        add( usernameField );
        add( passwordLabel );
        add( passwordField );
        validate();
        repaint();
    }
    
    private void prepareFrameForRegistration() {
        getContentPane().removeAll();
		

        validate();
        repaint();
    }


    

} // end class Login
