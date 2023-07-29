package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.OrderDAO;

import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ClientInfoStatus;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public Order save(Session session, Order entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public Order update(Session session, Order entity) {
        session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session, String s) {
        session.delete(session.get(Order.class,s));
        return session.get(Order.class,s)==null;
    }

    @Override
    public Order search(Session session, String s) {
     return   session.load(Order.class,s);

    }

    @Override
    public List<Order> getAll(Session session) {
        Query query = session.createQuery("FROM orders");
        query.setCacheable(true);
      return  (List<Order>) query.getResultList();
    }
}
