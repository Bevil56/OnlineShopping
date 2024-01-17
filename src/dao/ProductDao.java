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
public class ProductDao {

    Connection connection = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select max(pid) from product");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public int numCategories() {
        int total = 0;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from category");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public String[] getCategories() {
        int num = numCategories();
        String[] categories = new String[num];
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select * from category");
            int i = 0;
            while (rs.next()) {
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public boolean isProductNameExist(String pname) {
        try {
            ps = connection.prepareStatement("SELECT * FROM product WHERE pname = ?");
            ps.setString(1, pname);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isProductIdExist(int id) {
        try {
            ps = connection.prepareStatement("SELECT * FROM product WHERE pid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isProductAndCategoryExist(String pname, String cname) {
        try {
            ps = connection.prepareStatement("SELECT * FROM product WHERE pname = ? AND cname = ?");
            ps.setString(1, pname);
            ps.setString(2, cname);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(String pname, String cname, int qty, double price) {
        String sql = "INSERT INTO product (pname, cname, pqty, pprice) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, cname);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Product added successfully!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, String pname, String cname, int qty, double price) {
        String sql = "UPDATE product SET pname = ?,cname =?, pqty = ?, pprice = ? WHERE pid = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, cname);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            ps.setInt(5, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Product data successfully updated!", "Warning", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this product?", "Delete Product", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            String sql = "DELETE FROM product WHERE pid = ?";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Product deleted!", "Warning", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getProductsInfo(JTable table, String search) {
        String sql = "SELECT * FROM product WHERE concat(pid, pname,cname) LIKE ? order BY pid DESC";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
