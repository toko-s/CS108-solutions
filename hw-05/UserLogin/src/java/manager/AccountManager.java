package manager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;

@WebListener
public class AccountManager implements ServletContextListener {
    private HashMap<String, String> data;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext sc = servletContextEvent.getServletContext();
        data = new HashMap<>();
        sc.setAttribute("data", data);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
