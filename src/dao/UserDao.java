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

/**
 *
 * @author Vinh
 */
public class UserDao {

    Connection connection = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select max(uid) from [user]");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public boolean isEmailExist(String email) {
        try {
            ps = connection.prepareStatement("SELECT * FROM [user] WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isPhoneExist(String phone) {
        try {
            ps = connection.prepareStatement("SELECT * FROM [user] WHERE uphone = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(String username, String email, String pass, String phone, String question, String answer, String address) {
        String sql = "INSERT INTO [user] (uname, uemail, upassword, uphone, usecqus, uans, uaddress) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, question);
            ps.setString(6, answer);
            ps.setString(7, address);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User added successfully!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] getUserInfo(int id) {
        String[] info = new String[8];
        try {
            ps = connection.prepareStatement("SELECT * FROM [user] WHERE uid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                for (int i = 0; i < 8; i++) {
                    info[i] = rs.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public int getUserId(String email) {
        int id = 0;
        try {
            ps = connection.prepareStatement("SELECT uid  FROM [user] WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void update(int id, String username, String email, String pass, String phone, String question, String answer, String address) {
        String sql = "UPDATE [user] SET uname = ?, uemail = ?, upassword = ?,uphone = ?, usecqus = ?, uans = ?, uaddress = ? where uid = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, question);
            ps.setString(6, answer);
            ps.setString(7, address);
            ps.setInt(8, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User data successfully updated!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this account?", "Delete Account", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            String sql = "DELETE FROM [user] where uid = ?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Account deleted!", "Warning", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
