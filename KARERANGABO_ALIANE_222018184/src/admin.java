import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class admin extends JFrame implements ActionListener {

    private JTable viewrentingsTable;
    private JButton categoryButton, carsformButton, contactusButton,upButton,delButton;

    public admin() {
        setTitle("Admin Dashboard");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);


        String[] columnNames = {"ID", "First Name", "Last Name", "details", "Contact", "renting_date", "car"};


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rentals inner join users ON users.userid=rentals.userid");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("userid"), rs.getString("firstname"), rs.getString("lastname"),rs.getString("details"), rs.getString("phone_number"),
                        rs.getString("date"), rs.getString("car")};
                model.addRow(row);
            }


            viewrentingsTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(viewrentingsTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }

        // Create buttons
        JPanel buttonPanel = new JPanel();
        categoryButton = new JButton("categories");
        categoryButton.addActionListener(this);
        buttonPanel.add(categoryButton);

        carsformButton = new JButton("cars");
        carsformButton.addActionListener(this);
        buttonPanel.add(carsformButton);

        contactusButton = new JButton("feedbacks");
        contactusButton.addActionListener(this);
        buttonPanel.add(contactusButton);
        
        upButton = new JButton("Update");
        upButton.addActionListener(this);
        buttonPanel.add(upButton);
        
       delButton = new JButton("Delete");
        delButton.addActionListener(this);
        buttonPanel.add(delButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == categoryButton) {

            category category = new category();
            category.setVisible(true);
            dispose();

        } else if (e.getSource() == carsformButton) {

            cars cars = new cars();
            cars.setVisible(true);
            dispose();

        } else if (e.getSource() == contactusButton) {

            viewfeed viewfeed = new viewfeed();
            viewfeed.setVisible(true);
            dispose();

        }else if (e.getSource() == upButton) {
        	
        	 int selectedRow = viewrentingsTable.getSelectedRow();
             if (selectedRow != -1) {
                 int userId = (int) viewrentingsTable.getValueAt(selectedRow, 0);
                 //String firstName = (String) viewrentingsTable.getValueAt(selectedRow, 1);
                 //String lastName = (String) viewrentingsTable.getValueAt(selectedRow, 2);
                 String details = (String) viewrentingsTable.getValueAt(selectedRow, 3);
                 //String contact = (String) viewrentingsTable.getValueAt(selectedRow, 4);
                 String rentingDate = (String) viewrentingsTable.getValueAt(selectedRow, 5);
                 String car = (String) viewrentingsTable.getValueAt(selectedRow, 6);

                 // Prompt user to enter updated information
                 //String updatedFirstName = JOptionPane.showInputDialog(this, "Enter updated first name:", firstName);
                 //String updatedLastName = JOptionPane.showInputDialog(this, "Enter updated last name:", lastName);
                 String updatedDetails = JOptionPane.showInputDialog(this, "Enter updated details:", details);
                 //String updatedContact = JOptionPane.showInputDialog(this, "Enter updated contact:", contact);
                 String updatedRentingDate = JOptionPane.showInputDialog(this, "Enter updated renting date:", rentingDate);
                 String updatedCar = JOptionPane.showInputDialog(this, "Enter updated car:", car);
                 
                 

                 // Perform update in the database
                 try {
                     Class.forName("com.mysql.cj.jdbc.Driver");
                     Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE rentals SET  details = ?,  date = ?, car = ? WHERE userid = ?");
                     
                     stmt.setString(1, updatedDetails);
                     
                     stmt.setString(2, updatedRentingDate);
                     stmt.setString(3, updatedCar);
                     stmt.setInt(4, userId);
                     int rowsAffected = stmt.executeUpdate();
                     if (rowsAffected > 0) {
                         JOptionPane.showMessageDialog(this, "Record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                         // Refresh the table after update
                         refreshTable();
                     } else {
                         JOptionPane.showMessageDialog(this, "Failed to update record.", "Error", JOptionPane.ERROR_MESSAGE);
                     }
                     conn.close();
                 } catch (ClassNotFoundException | SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                 }
             } else {
                 JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
             }
        }else if (e.getSource() == delButton) {

        	int selectedRow = viewrentingsTable.getSelectedRow();
            if (selectedRow != -1) {
                int userId = (int) viewrentingsTable.getValueAt(selectedRow, 0);
                String firstName = (String) viewrentingsTable.getValueAt(selectedRow, 1);
                String lastName = (String) viewrentingsTable.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the record for " + firstName + " " + lastName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM rentals WHERE userid = ?");
                        stmt.setInt(1, userId);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            // Refresh the table after deletion
                            
                            refreshTable();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to delete record.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        conn.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        }
    
    
    private void refreshTable() {
        
        DefaultTableModel model = (DefaultTableModel) viewrentingsTable.getModel();
        model.setRowCount(0); // Clear existing rows
        // Populate the table with updated data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rentals inner join users ON users.userid=rentals.userid");

            while (rs.next()) {
                Object[] row = {rs.getInt("userid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("details"), rs.getString("phone_number"), rs.getString("date"), rs.getString("car")};
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        admin admin = new admin();
        admin.setVisible(true);
    }

}

