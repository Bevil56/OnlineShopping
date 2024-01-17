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
import user.ForgotPassword;

public class ForgotPasswordDao {

    Connection connection = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public boolean isEmailExist(String email) {
        try {
            ps = connection.prepareStatement("SELECT * FROM [user] WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                ForgotPassword.jTextField2.setText(rs.getString(6));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Email adress isn't exist.\nPlease try again!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean getAnswer(String email, String newAns) {
        try {
            ps = connection.prepareStatement("SELECT * FROM [user] WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(7);
                if (newAns.equals(oldAns)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Your answer didn't match.\nPlease try again!");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Email adress isn't exist.\nPlease try again!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void setPassword(String email, String pass) {
        String sql = "UPDATE [user] set upassword = ? where uemail = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, email);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Password successfully updated!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
