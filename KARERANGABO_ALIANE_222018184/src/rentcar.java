import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class rentcar extends JFrame implements ActionListener {
    JLabel titleLabel,useridLabel,rentingdateLabel, detailsLabel,durationLabel,carLabel;
    JTextField useridTextField, rentingdateTextField,detailsTextField,durationTextField,carTextField;
    JButton submitButton, viewusersButton, contactusButton,viewcarsButton;
    JTextArea carsTextArea,usersTextArea;
    private JTable dataTable;

    rentcar() {
    	
    	 String[] columnNames = {"ID", "Name", "Description", "Price"};
         displaycars(columnNames);
         
         
         JScrollPane scrollPane = new JScrollPane(dataTable);
         // Set the bounds for the scroll pane to position it on the frame
         scrollPane.setBounds(350, 20, 400, 300);

         
         add(scrollPane);
         
         titleLabel = new JLabel("BOOK ANY CAR AVAILABLE");
 	    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
 	    titleLabel.setBounds(60, 10, 320, 20);
    	
        useridLabel = new JLabel("user id:");
        useridLabel.setBounds(20, 30, 80, 30);
        useridTextField = new JTextField();
        useridTextField.setBounds(100, 30, 200, 30);

        rentingdateLabel = new JLabel("Date:");
        rentingdateLabel.setBounds(20, 70, 80, 30);
        rentingdateTextField = new JTextField();
        rentingdateTextField.setBounds(100, 70, 200, 30);

        detailsLabel = new JLabel("Details:");
        detailsLabel.setBounds(20, 110, 80, 30);
        detailsTextField = new JTextField();
        detailsTextField.setBounds(100, 110, 200, 30);

        durationLabel = new JLabel("duration:");
        durationLabel.setBounds(20, 150, 80, 30);
        durationTextField = new JTextField();
        durationTextField.setBounds(100, 150, 200, 30);

        carLabel = new JLabel("Car:");
        carLabel.setBounds(20, 190, 80, 30);
        carTextField = new JTextField();
        carTextField.setBounds(100, 190, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 230, 80, 30);
        submitButton.addActionListener(this);

        viewusersButton = new JButton("users ");
        viewusersButton.setBounds(20, 350, 100, 30);
        viewusersButton.addActionListener(this);

        contactusButton = new JButton("contact us");
        contactusButton.setBounds(140, 350, 100, 30);
        contactusButton.addActionListener(this);

        viewcarsButton = new JButton("VIEW CARS");
        viewcarsButton.setBounds(260, 350, 100, 30);
        viewcarsButton.addActionListener(this);

        

        usersTextArea = new JTextArea();
        usersTextArea.setBounds(20, 260, 380, 200);
        
        

        add(useridLabel);
        add(useridTextField);
        add(rentingdateLabel);
        add(rentingdateTextField);
        add(detailsLabel);
        add(detailsTextField);
        add(durationLabel);
        add(durationTextField);
        add(carLabel);
        add(carTextField);
        add(submitButton);
        add(viewusersButton);
        add(contactusButton);
        add(viewcarsButton);
        add(titleLabel);
       // add(dataTable);
        //add(carsTextArea);
        //add(usersTextArea);

        setSize(1000, 600);
        setLayout(null);
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
    }
    
    
    private void displaycars(String[] columnNames) {
    	  try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection con = DriverManager.getConnection(
//                      "jdbc:mysql://localhost:3306/carrental", "222018184", "222018184");
              Statement stmt = con.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT * FROM cars");
              
              DefaultTableModel model = new DefaultTableModel(columnNames, 0);
              model.setRowCount(0); // Clear existing data from the table

              while (rs.next()) {
                  Object[] row = {
                      rs.getInt("cat_id"),
                      rs.getString("Name"),
                     // rs.getInt("userid"),
                     
                      rs.getString("description"),
                      rs.getInt("price"),
                      
                  };
                  model.addRow(row);
              }
              
              dataTable = new JTable(model);
              // Add the JTable to a JScrollPane
                 JScrollPane scrollPane = new JScrollPane(dataTable);

                 // Add the JScrollPane to the JFrame
                 add(scrollPane, BorderLayout.CENTER);
                 
              con.close();
          } catch (Exception ex) {
              System.out.println(ex);
          }
    	
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userid = useridTextField.getText();
            String rentingdate =rentingdateTextField.getText();
            String details = detailsTextField.getText();
            String duration = durationTextField.getText();
            String car = carTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/carrental", "222018184", "222018184");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO rentals (userid, date,details,duration,car) VALUES (?, ?,?,?,?)");
                stmt.setString(1, userid);
                stmt.setString(2, rentingdate);
                stmt.setString(3, details);
                stmt.setString(4, duration);
                stmt.setString(5, car);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Request submitted successfully!");
                new rentcar().setVisible(true);
                dispose();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == viewusersButton) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/carrental", "222018184", "222018184");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT userid, username FROM users");
                
                StringBuilder result = new StringBuilder();
                
                while (rs.next()) {
                    result.append(rs.getString(1) + " " + rs.getString(2) + "\n");
                }
                
                con.close();
                
                JOptionPane.showMessageDialog(null, result.toString(), "Database Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == viewcarsButton) {
        	new rentcar();
        	dispose();
            
        	
      
        } else if (e.getSource() == contactusButton) {
            new contactus().setVisible(true);
            dispose();

        }
    }

    public static void main(String[] args) {
        new rentcar();
    }


    
}

