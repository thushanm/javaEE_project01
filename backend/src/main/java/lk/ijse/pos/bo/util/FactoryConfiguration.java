package lk.ijse.pos.bo.util;

import lk.ijse.pos.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;
    private FactoryConfiguration(){

            Properties properties=new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration configuration=new Configuration().
                addAnnotatedClass(Customer.class).
                addAnnotatedClass(Order.class).
                addAnnotatedClass(Item.class).
                addAnnotatedClass(OrderDetail.class);
        configuration.addProperties(properties);
        sessionFactory=configuration.buildSessionFactory();


    }
    public static FactoryConfiguration getInstance(){
        return factoryConfiguration==null ? new FactoryConfiguration():factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
