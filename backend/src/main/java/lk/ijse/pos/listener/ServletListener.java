package lk.ijse.pos.listener;

import lk.ijse.pos.bo.util.FactoryConfiguration;
import org.hibernate.Session;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Listener implement");
        Session session = FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("context Destroy");
    }
}
