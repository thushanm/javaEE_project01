package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Customer save(Session session, Customer entity) {
        session.save(entity);
        return entity;

    }

    @Override
    public Customer update(Session session, Customer entity) {
       session.update(entity);
       return entity;
    }

    @Override
    public boolean delete(Session session, String s) {
        session.delete( session.get(Customer.class,s));
        return session.get(Customer.class,s)==null;
    }

    @Override
    public Customer search(Session session, String s) {
        return session.get(Customer.class,s);
    }

    @Override
    public List<Customer> getAll(Session session) {
        Query query = session.createQuery("FROM Customer");
        query.setCacheable(true);
      return (List<Customer>)query.getResultList();

    }
}
