package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("head","Create New Account");
        req.setAttribute("status","Please enter proposed name and password");
        req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String,String> map = (HashMap<String,String>) req.getServletContext().getAttribute("data");
        String name = req.getParameter("username");
        System.out.println(name);
        if(map.containsKey(name)){
            req.setAttribute("head","The Name " + name + " is \n Already in use");
            req.setAttribute("status","Please enter another name and password.");
            req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req,resp);
        }else{
            map.put(name, req.getParameter("password"));
            req.setAttribute("name", name);
            req.getRequestDispatcher("/WEB-INF/view/hello.jsp").forward(req,resp);
        }

    }
}
