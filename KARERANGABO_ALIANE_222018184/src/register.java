import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class register extends JFrame implements ActionListener {
    private JLabel titleLabel ;
    private JLabel usernameLabel,emailLabel,firstnameLabel,lastnameLabel,passwordLabel,phoneLAbel;
    private JTextField usernameField,emailField,firstnameField,lastnameField,passwordField,phoneField;



    private JButton registerButton,loginButton;


    public register() {
        setLayout(null);


        setTitle("Registration Form");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
		
        

        titleLabel = new JLabel("Register to car rental  system");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setBounds(40, 20, 320, 30);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 70, 80, 25);
        add(usernameLabel);

        emailLabel = new JLabel("email:");
        emailLabel.setBounds(40, 110, 80, 25);
        add(emailLabel);

        firstnameLabel = new JLabel("firstname:");
        firstnameLabel.setBounds(40, 150, 80, 25);
        add(firstnameLabel);

        lastnameLabel = new JLabel("lastname:");
        lastnameLabel.setBounds(40, 190, 80, 25);
        add(lastnameLabel);

        passwordLabel = new JLabel("password:");
        passwordLabel.setBounds(40, 230, 80, 25);
        add(passwordLabel);

        phoneLAbel = new JLabel("number:");
        phoneLAbel.setBounds(40, 270, 80, 25);
        add(phoneLAbel);




        usernameField = new JTextField();
        usernameField.setBounds(130, 70, 200, 25);
        add(usernameField);

        emailField = new JTextField();
        emailField.setBounds(130, 110, 200, 25);
        add(emailField);

        firstnameField = new JTextField();
        firstnameField.setBounds(130, 150, 200, 25);
        add(firstnameField);

        lastnameField = new JTextField();
        lastnameField.setBounds(130, 190, 200, 25);
        add(lastnameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 230, 200, 25);
        add(passwordField);

        phoneField = new JTextField();
        phoneField.setBounds(130, 270, 200, 25);
        add(phoneField);



        registerButton = new JButton("Register");
        registerButton.setBounds(150, 360, 100, 30);
        registerButton.addActionListener(this);
        add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(300, 360, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);
        
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();

        } else if (e.getSource() == loginButton) {
            new login().setVisible(true);
            dispose();

        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String first_name = firstnameField.getText();
        String last_name = lastnameField.getText();
        String password = passwordField.getText();
        String phone_number = phoneField.getText();



        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");


            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username,email, password,firstname,lastname,phone_number) VALUES (?, ?,?,?,?,?)");
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, first_name);
            stmt.setString(5, last_name);
            stmt.setString(6, phone_number);



            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new login().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }


            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        register form = new register();
        form.setVisible(true);
    }
}


