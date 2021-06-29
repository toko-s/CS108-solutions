package manager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ContextManager implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductManager p = ProductManager.getInstance();
        servletContextEvent.getServletContext().setAttribute("manager", p);
        servletContextEvent.getServletContext().setAttribute("data", p.getData());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
