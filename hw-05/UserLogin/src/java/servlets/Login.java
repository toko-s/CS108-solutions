package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("head","Welcome to Homework 5");
        req.setAttribute("status", "Please log in.");
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String,String> map = (HashMap<String,String>)req.getServletContext().getAttribute("data");
        String username = req.getParameter("username");
        String password = map.get(username);
        if(password != null && password.equals(req.getParameter("password"))){
            req.setAttribute("name",username);
            req.getRequestDispatcher("/WEB-INF/view/hello.jsp").forward(req,resp);
        }
        else{
            req.setAttribute("head", "Please Try Again");
            req.setAttribute("status","Either your username or password is incorrect. Please try again");
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req,resp);
        }
    }
}
