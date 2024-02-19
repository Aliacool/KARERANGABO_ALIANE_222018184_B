import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class viewfeed extends JFrame implements ActionListener {

    private JTable feedbackTable;
    private JButton deleteButton,  backButton;

    public viewfeed() {
        setTitle("FEEDBACK");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);


        String[] columnNames = {"NAME", "EMAIL", "MESSAGE"};


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contactus");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {
                		rs.getString("name"), 
                		rs.getString("email"),
                		rs.getString("message")};
                model.addRow(row);
            }


            feedbackTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(feedbackTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }


        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        

        backButton = new JButton("back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
        	
        	int selectedRow = feedbackTable.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) feedbackTable.getValueAt(selectedRow, 0);
                String email = (String) feedbackTable.getValueAt(selectedRow, 1);
                //String message = (String) feedbackTable.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the record for " + name + " " + email + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM contactus WHERE name = ?");
                        stmt.setString(1, name);
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


        } else if (e.getSource() == backButton) {

            new admin().setVisible(true);

        }
    }
    
private void refreshTable() {
        
        DefaultTableModel model = (DefaultTableModel) feedbackTable.getModel();
        model.setRowCount(0); // Clear existing rows
        // Populate the table with updated data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karerangabo_aliane_cb", "222018184", "222018184");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contactus");

            while (rs.next()) {
            	Object[] row = {
                		rs.getString("name"), 
                		rs.getString("email"),
                		rs.getString("message")};
                model.addRow(row);
            }
            
            feedbackTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(feedbackTable);
            add(scrollPane, BorderLayout.CENTER);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        viewfeed viewfeed = new viewfeed();
        viewfeed.setVisible(true);
    }

}



