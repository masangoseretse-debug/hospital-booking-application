package za.ac.cput.hospital.dao;

import za.ac.cput.hospital.config.Database;
import za.ac.cput.hospital.model.Appointment;
import java.sql.*;
import java.time.*;
import java.util.*;

public class AppointmentDao {
    private static final String SELECT="""
      SELECT a.appointment_id,a.patient_id,a.doctor_id,p.full_name patient_name,d.full_name doctor_name,
      dep.name department,a.appointment_date,a.appointment_time,a.status,a.reason,a.cancellation_reason
      FROM appointments a JOIN users p ON p.user_id=a.patient_id JOIN doctors dr ON dr.user_id=a.doctor_id
      JOIN users d ON d.user_id=dr.user_id JOIN departments dep ON dep.department_id=dr.department_id """;
    public List<Appointment> findAll(Integer userId,String role,String search,String status) throws SQLException {
        StringBuilder sql=new StringBuilder(SELECT+" WHERE 1=1"); List<Object> args=new ArrayList<>();
        if("PATIENT".equals(role)){sql.append(" AND a.patient_id=?");args.add(userId);} else if("DOCTOR".equals(role)){sql.append(" AND a.doctor_id=?");args.add(userId);}
        if(search!=null&&!search.isBlank()){sql.append(" AND (p.full_name LIKE ? OR d.full_name LIKE ? OR a.reason LIKE ?)");String q="%"+search+"%";args.add(q);args.add(q);args.add(q);}
        if(status!=null&&!status.isBlank()&&!"ALL".equals(status)){sql.append(" AND a.status=?");args.add(status);}
        sql.append(" ORDER BY a.appointment_date DESC,a.appointment_time DESC");List<Appointment> out=new ArrayList<>();
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql.toString())){for(int i=0;i<args.size();i++)p.setObject(i+1,args.get(i));try(ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}}return out;
    }
    public void create(int patient,int doctor,LocalDate date,LocalTime time,String reason)throws SQLException{
        String sql="INSERT INTO appointments(patient_id,doctor_id,appointment_date,appointment_time,status,reason) VALUES(?,?,?,?,'PENDING',?)";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,patient);p.setInt(2,doctor);p.setObject(3,date);p.setObject(4,time);p.setString(5,reason);p.executeUpdate();}
    }
    public void update(int id,String status,LocalDate date,LocalTime time,String reason,String cancellation,int currentUser,String role)throws SQLException{
        String sql="UPDATE appointments SET status=?,appointment_date=?,appointment_time=?,reason=?,cancellation_reason=? WHERE appointment_id=?"+("DOCTOR".equals(role)?" AND doctor_id=?":"");
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,status);p.setObject(2,date);p.setObject(3,time);p.setString(4,reason);p.setString(5,cancellation);p.setInt(6,id);if("DOCTOR".equals(role))p.setInt(7,currentUser);p.executeUpdate();}
    }
    public void delete(int id,int currentUser,String role)throws SQLException{
        String sql="DELETE FROM appointments WHERE appointment_id=?"+("PATIENT".equals(role)?" AND patient_id=?":"");
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);if("PATIENT".equals(role))p.setInt(2,currentUser);p.executeUpdate();}
    }
    public List<Map<String,Object>> doctors()throws SQLException{List<Map<String,Object>>out=new ArrayList<>();String sql="SELECT u.user_id,u.full_name,d.specialization,dep.name department FROM doctors d JOIN users u ON u.user_id=d.user_id JOIN departments dep ON dep.department_id=d.department_id WHERE u.is_active=1 ORDER BY u.full_name";try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){while(r.next()){Map<String,Object>m=new HashMap<>();m.put("id",r.getInt(1));m.put("name",r.getString(2));m.put("specialization",r.getString(3));m.put("department",r.getString(4));out.add(m);}}return out;}
    private Appointment map(ResultSet r)throws SQLException{return new Appointment(r.getInt(1),r.getInt(2),r.getInt(3),r.getString(4),r.getString(5),r.getString(6),r.getObject(7,LocalDate.class),r.getObject(8,LocalTime.class),r.getString(9),r.getString(10),r.getString(11));}
}
