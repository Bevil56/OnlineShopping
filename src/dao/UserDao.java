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
}
