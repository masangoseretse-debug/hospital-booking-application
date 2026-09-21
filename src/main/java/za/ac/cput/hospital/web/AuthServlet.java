package za.ac.cput.hospital.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import za.ac.cput.hospital.dao.UserDao;
import za.ac.cput.hospital.model.User;
import za.ac.cput.hospital.util.Validation;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns={"/login","/register","/logout"})
public class AuthServlet extends HttpServlet {
    private final UserDao users=new UserDao();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(req.getServletPath().equals("/logout")){req.getSession().invalidate();resp.sendRedirect(req.getContextPath()+"/login");return;}
        req.getRequestDispatcher("/WEB-INF/views/"+(req.getServletPath().equals("/register")?"register":"login")+".jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            if(req.getServletPath().equals("/login")){
                User u=users.authenticate(Validation.clean(req.getParameter("email")),req.getParameter("password")).orElse(null);
                if(u==null||!u.active()){req.setAttribute("error","Invalid email/password or inactive account.");doGet(req,resp);return;}
                req.getSession(true).setAttribute("user",u);resp.sendRedirect(req.getContextPath()+"/dashboard");
            }else{
                String name=Validation.clean(req.getParameter("name")),email=Validation.clean(req.getParameter("email")),password=req.getParameter("password");
                if(name.isBlank()||!Validation.validEmail(email)||!Validation.strongEnoughPassword(password)){req.setAttribute("error","Enter a name, valid email and password of at least 8 characters.");doGet(req,resp);return;}
                users.createPatient(name,email,password,Validation.clean(req.getParameter("phone")),Validation.clean(req.getParameter("address")));
                req.getSession().setAttribute("flash","Registration successful. You can now sign in.");resp.sendRedirect(req.getContextPath()+"/login");
            }
        }catch(SQLIntegrityConstraintViolationException e){req.setAttribute("error","That email address is already registered.");doGet(req,resp);}catch(Exception e){throw new ServletException("Database operation failed",e);}
    }
}
