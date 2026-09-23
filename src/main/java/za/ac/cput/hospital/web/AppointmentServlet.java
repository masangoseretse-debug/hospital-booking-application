package za.ac.cput.hospital.web;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;
import za.ac.cput.hospital.dao.AppointmentDao;import za.ac.cput.hospital.model.User;import za.ac.cput.hospital.util.Validation;
import java.io.IOException;import java.time.*;import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/appointments/*") public class AppointmentServlet extends HttpServlet{
 private final AppointmentDao dao=new AppointmentDao();
 protected void doGet(HttpServletRequest q,HttpServletResponse s)throws ServletException,IOException{try{User u=(User)q.getSession().getAttribute("user");q.setAttribute("appointments",dao.findAll(u.id(),u.role(),q.getParameter("search"),q.getParameter("status")));q.setAttribute("doctors",dao.doctors());q.getRequestDispatcher("/WEB-INF/views/appointments.jsp").forward(q,s);}catch(Exception e){throw new ServletException(e);}}
 protected void doPost(HttpServletRequest q,HttpServletResponse s)throws ServletException,IOException{User u=(User)q.getSession().getAttribute("user");String action=q.getParameter("action");try{
   if("create".equals(action)){LocalDate date=LocalDate.parse(q.getParameter("date"));if(!Validation.bookableDate(date))throw new IllegalArgumentException("Appointment date must be in the future.");dao.create(u.id(),Integer.parseInt(q.getParameter("doctorId")),date,LocalTime.parse(q.getParameter("time")),Validation.clean(q.getParameter("reason")));flash(q,"success","Appointment created successfully.");}
   else if("update".equals(action)){if(u.role().equals("PATIENT"))throw new SecurityException("Only hospital staff may update an appointment.");dao.update(Integer.parseInt(q.getParameter("id")),q.getParameter("status"),LocalDate.parse(q.getParameter("date")),LocalTime.parse(q.getParameter("time")),Validation.clean(q.getParameter("reason")),Validation.clean(q.getParameter("cancellationReason")),u.id(),u.role());flash(q,"success","Appointment updated.");}
   else if("delete".equals(action)){dao.delete(Integer.parseInt(q.getParameter("id")),u.id(),u.role());flash(q,"success","Appointment deleted.");}
}catch(SQLIntegrityConstraintViolationException e){
    flash(q,"error","This doctor is already booked for that date and time. Please choose another time.");
}catch(Exception e){
    flash(q,"error",e.getMessage()==null?"Operation failed.":e.getMessage());
}
s.sendRedirect(q.getContextPath()+"/appointments/");}
 private void flash(HttpServletRequest q,String type,String message){q.getSession().setAttribute("flashType",type);q.getSession().setAttribute("flash",message);}
}
