import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame implements ActionListener {

    private JLabel usernameLabel, passwordLabel, titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton,regButton;

    public login() {
        setTitle("car rental system - Login");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);


        titleLabel = new JLabel("Login to car rental  system");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(40, 20, 320, 30);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 70, 80, 25);
        add(usernameLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 110, 80, 25);
        add(passwordLabel);


        usernameField = new JTextField();
        usernameField.setBounds(130, 70, 200, 25);
        add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 110, 200, 25);
        add(passwordField);


        loginButton = new JButton("Login");
        loginButton.setBounds(130, 170, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);
        
        regButton = new JButton("Register");
        regButton.setBounds(250, 170, 100, 30);
        regButton.addActionListener(this);
        add(regButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginUser();
        }else if (e.getSource() == regButton) {
            new register().setVisible(true);
            dispose();
        }
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");


            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                String role = rs.getString("username");
                if (role.equalsIgnoreCase("admin")) {
                    admin admin = new admin();
                    admin.setVisible(true);
                    this.dispose();
                } else {
                    rentcar rentcar = new rentcar();
                    rentcar.setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        login login = new login();
        login.setVisible(true);
    }
}



