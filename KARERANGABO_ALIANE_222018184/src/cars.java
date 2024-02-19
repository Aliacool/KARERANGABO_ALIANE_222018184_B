import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cars extends JFrame implements ActionListener {
    JLabel catidLabel,nameLabel,descriptionLabel,priceLabel;
    JTextField catidTextField,nameTextField, descriptionTextField, priceTextField;
    JButton submitButton, backButton;


    cars() {
        catidLabel = new JLabel("cat id:");
        catidLabel.setBounds(20, 20, 80, 40);
        catidTextField = new JTextField();
        catidTextField.setBounds(100, 20, 200, 40);

        nameLabel = new JLabel("Car name:");
        nameLabel.setBounds(20, 100, 80, 40);
        nameTextField = new JTextField();
        nameTextField.setBounds(100, 100, 200, 40);

        descriptionLabel = new JLabel("description:");
        descriptionLabel.setBounds(20, 180, 80, 40);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(100, 180, 200, 40);

        priceLabel = new JLabel("price:");
        priceLabel.setBounds(20, 260, 80, 40);
        priceTextField = new JTextField();
        priceTextField.setBounds(100, 260, 200, 40);



        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 320, 80, 30);
        submitButton.addActionListener(this);


        backButton = new JButton("Back");
        backButton.setBounds(190, 320, 80, 30);
        backButton.addActionListener(this);


        add(catidLabel);
        add(catidTextField);
        add(nameLabel);
        add(nameTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(priceLabel);
        add(priceTextField);

        add(submitButton);
        add(backButton);


        setTitle("CARS");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {

            String cat_id = catidTextField.getText();
            String name = nameTextField.getText();
            String description = descriptionTextField.getText();
            String price = priceTextField.getText();


            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/carrental", "222018184", "222018184");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO cars (cat_id,name,description,price) VALUES (?,?,?,?)");
                stmt.setString(1, cat_id);
                stmt.setString(2, name);
                stmt.setString(3, description);
                stmt.setString(4, price);

                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "New car submitted successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == backButton) {
            new admin().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        new cars();
    }
}



