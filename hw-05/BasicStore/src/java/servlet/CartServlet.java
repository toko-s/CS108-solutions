package servlet;

import manager.Item;
import manager.ProductManager;
import manager.UserCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCart cart = (UserCart) req.getSession().getAttribute("user-cart");
        ProductManager manager = (ProductManager) req.getServletContext().getAttribute("manager");
        cart.add(manager.getByID(req.getParameter("id")));
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCart cart = (UserCart) req.getSession().getAttribute("user-cart");
        for(Item i : cart.getCopyOfItem()){
            cart.update(i, Integer.parseInt(req.getParameter(i.name)));
        }
        req.getRequestDispatcher("WEB-INF/view/cart.jsp").forward(req,resp);
    }
}
