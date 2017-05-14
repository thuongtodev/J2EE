/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author ThuongIT
 */
public class CategoryDAO {
// hiển thị chi tiết sản phẩm
    public Category getCategory(long categoryID) throws SQLException {
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM category WHERE category_id = '" + categoryID + "'";
        PreparedStatement ps = connection.prepareCall(sql);
        ResultSet rs = ps.executeQuery();
        Category category = new Category();
        while (rs.next()) {
            category.setCategoryID(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setCategoryGroup(rs.getString("category_group"));
        }
        return category;
    }
    // get danh sách thể loại
    public ArrayList<Category> getListCategory() throws SQLException {
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM category";
        PreparedStatement ps = connection.prepareCall(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Category> list = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryID(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setCategoryGroup(rs.getString("category_group"));
            list.add(category);
        }
        return list;
    }
    // lấy danh sách sản phẩm
    public ArrayList<Category> getListCategoryPageList(int firstResult, int maxResult) throws SQLException{
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM Category limit ?,?";
        PreparedStatement ps = connection.prepareCall(sql);
        ps.setInt(1, firstResult - 1);
        ps.setInt(2, maxResult);
        ResultSet rs = ps.executeQuery();
        ArrayList<Category> list = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryID(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setCategoryGroup(rs.getString("category_group"));
            list.add(category);
        }
        return list;
    }
    // get danh sách thể loại cho nam
    public ArrayList<Category> getListCategoryForMen() throws SQLException {
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM category where category_group='Nam'";
        PreparedStatement ps = connection.prepareCall(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Category> list = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryID(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            list.add(category);
        }
        return list;
    }
    
    // get danh sách thể loại cho nữ
    public ArrayList<Category> getListCategoryForWomen() throws SQLException {
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT * FROM category where category_group='Nữ'";
        PreparedStatement ps = connection.prepareCall(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Category> list = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryID(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            list.add(category);
        }
        return list;
    }
    
    // thêm mới dữ liệu
    public boolean insertCategory(Category c) {
        Connection connection = DBConnect.getConnection();
        String sql = "INSERT INTO category VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setLong(1, c.getCategoryID());
            ps.setString(2, c.getCategoryName());
            ps.setString(3, c.getCategoryGroup());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // cập nhật dữ liệu
    public boolean updateCategory(Category c) {
        Connection connection = DBConnect.getConnection();
        String sql = "UPDATE category SET category_name = ?, category_group = ? WHERE category_id = ?";
        try {
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getCategoryGroup());
            ps.setLong(3, c.getCategoryID());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // xóa dữ liệu
    public boolean deleteCategory(long category_id) {
        Connection connection = DBConnect.getConnection();
        String sql = "DELETE FROM category WHERE category_id = ?";
        try {
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setLong(1, category_id);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // tính tổng số danh mục
    public int countCategory() throws SQLException{
        Connection connection = DBConnect.getConnection();
        String sql = "SELECT count(category_id) FROM category";
        PreparedStatement ps = connection.prepareCall(sql);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;  
    }
    
    public static void main(String[] args) throws SQLException {
        CategoryDAO dao = new CategoryDAO();
        for (Category ds : dao.getListCategory()) {
            System.out.println(ds.getCategoryID() + " - " + ds.getCategoryName());
        }
    }

}
