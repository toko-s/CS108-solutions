package servlet;

import manager.Item;
import manager.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductManager manager = (ProductManager) (req.getServletContext().getAttribute("manager"));
        Item i = manager.getByID(req.getParameter("id"));
        req.setAttribute("item", i);
        req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

    }
}
