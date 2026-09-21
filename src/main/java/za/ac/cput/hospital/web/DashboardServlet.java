package za.ac.cput.hospital.web;
import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import za.ac.cput.hospital.dao.AppointmentDao;import za.ac.cput.hospital.model.User;import java.io.IOException;
@WebServlet("/dashboard") public class DashboardServlet extends HttpServlet{
 private final AppointmentDao dao=new AppointmentDao();
 protected void doGet(HttpServletRequest q,HttpServletResponse s)throws ServletException,IOException{try{User u=(User)q.getSession().getAttribute("user");var list=dao.findAll(u.id(),u.role(),null,"ALL");q.setAttribute("appointments",list);q.setAttribute("total",list.size());q.setAttribute("pending",list.stream().filter(a->a.status().equals("PENDING")).count());q.setAttribute("completed",list.stream().filter(a->a.status().equals("COMPLETED")).count());q.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(q,s);}catch(Exception e){throw new ServletException(e);}}
}
