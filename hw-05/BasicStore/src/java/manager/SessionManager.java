package manager;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionManager implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("user-cart",new UserCart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
