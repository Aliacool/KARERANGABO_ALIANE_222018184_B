import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class contactus extends JFrame implements ActionListener {
    JLabel nameLabel,emailLabel,messageLabel;
    JTextField nameTextField,emailTextField,messageTextField;
    JButton submitButton, viewButton, backButton;


    contactus() {
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 30);
        nameTextField = new JTextField();
        nameTextField.setBounds(100, 20, 200, 30);

        emailLabel = new JLabel("email:");
        emailLabel.setBounds(20, 80, 80, 30);
        emailTextField = new JTextField();
        emailTextField.setBounds(100, 80, 200, 30);



        messageLabel = new JLabel("message:");
        messageLabel.setBounds(20, 140, 80, 30);
        messageTextField = new JTextField();
        messageTextField.setBounds(100, 140, 200, 100);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 260, 80, 30);
        submitButton.addActionListener(this);



        backButton = new JButton("Back");
        backButton.setBounds(100, 300, 80, 30);
        backButton.addActionListener(this);
        
        /*viewButton = new JButton("view");
        viewButton.setBounds(320, 70, 80, 30);
        viewButton.addActionListener(this);*/



        add(nameLabel);
        add(nameTextField);
        add(emailLabel);
        add(emailTextField);

        add(messageLabel);
        add(messageTextField);
        add(submitButton);
        //add(viewButton);
        add(backButton);


        setSize(500, 400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("CONTACT US");
        setLayout(new BorderLayout());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText();
            String email =emailTextField.getText();

            String message = messageTextField.getText();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO contactus (name,email,message) VALUES (?,?,?)");
                stmt.setString(1, name);
                stmt.setString(2, email);

                stmt.setString(3, message);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == backButton) {
            new rentcar();
            dispose();
        }
    }

    public static void main(String[] args) {
        new contactus();
    }



    
}
