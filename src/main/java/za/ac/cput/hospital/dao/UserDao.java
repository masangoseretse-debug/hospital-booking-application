package za.ac.cput.hospital.dao;

import za.ac.cput.hospital.config.Database;
import za.ac.cput.hospital.model.User;
import za.ac.cput.hospital.util.SecurityUtil;
import java.sql.*;
import java.util.*;

public class UserDao {
    public Optional<User> authenticate(String email, String password) throws SQLException {
        String sql = "SELECT user_id, full_name, email, role, is_active FROM users WHERE email=? AND password_hash=?";
        try (Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email.toLowerCase()); p.setString(2,SecurityUtil.hash(password));
            try(ResultSet r=p.executeQuery()){ return r.next()?Optional.of(map(r)):Optional.empty(); }
        }
    }
    public int createPatient(String name,String email,String password,String phone,String address) throws SQLException {
        String sql="INSERT INTO users(full_name,email,password_hash,role,phone,address) VALUES(?,?,?,'PATIENT',?,?)";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,name);p.setString(2,email.toLowerCase());p.setString(3,SecurityUtil.hash(password));p.setString(4,phone);p.setString(5,address);p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){r.next();return r.getInt(1);}
        }
    }
    public List<User> search(String query) throws SQLException {
        String sql="SELECT user_id,full_name,email,role,is_active FROM users WHERE full_name LIKE ? OR email LIKE ? ORDER BY full_name";
        List<User> out=new ArrayList<>(); try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            String q="%"+(query==null?"":query)+"%";p.setString(1,q);p.setString(2,q);try(ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}
        } return out;
    }
    public void setActive(int id, boolean active) throws SQLException { try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("UPDATE users SET is_active=? WHERE user_id=?")){p.setBoolean(1,active);p.setInt(2,id);p.executeUpdate();} }
    private User map(ResultSet r)throws SQLException{return new User(r.getInt("user_id"),r.getString("full_name"),r.getString("email"),r.getString("role"),r.getBoolean("is_active"));}
}
