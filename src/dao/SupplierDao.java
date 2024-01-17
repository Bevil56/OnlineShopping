/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vinh
 */
public class SupplierDao {
    Connection connection = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    public int getMaxRow() {
        int row = 0;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select max(sid) from supplier");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    public boolean isEmailExist(String email) {
        try {
            ps = connection.prepareStatement("SELECT * FROM supplier WHERE semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isPhoneExist(String phone) {
        try {
            ps = connection.prepareStatement("SELECT * FROM supplier WHERE sphone = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isUsernameExist(String name) {
        try {
            ps = connection.prepareStatement("SELECT * FROM supplier WHERE sname = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void insert(String username, String email, String pass, String phone, String address) {
        String sql = "INSERT INTO supplier (sname, semail, spassword, sphone, saddress) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, address);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Supplier added successfully!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] getSuppliersInfo(int id) {
        String[] info = new String[6];
        try {
            ps = connection.prepareStatement("SELECT * FROM supplier WHERE sid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    info[i] = rs.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public int getSupplierId(String email) {
        int id = 0;
        try {
            ps = connection.prepareStatement("SELECT sid  FROM supplier WHERE semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void update(int id, String username, String email, String pass, String phone, String address) {
        String sql = "UPDATE supplier SET sname = ?, semail = ?, spassword = ?,sphone = ?, saddress = ? where sid = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setInt(6, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Supplier data successfully updated!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this account?", "Delete Account", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            String sql = "DELETE FROM supplier WHERE sid = ?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Account deleted!", "Warning", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getSuppliersInfo(JTable table, String search) {
        String sql = "SELECT * FROM supplier WHERE concat(sid, sname, semail) LIKE ? order BY sid DESC";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                for (int i = 1; i < 6; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
