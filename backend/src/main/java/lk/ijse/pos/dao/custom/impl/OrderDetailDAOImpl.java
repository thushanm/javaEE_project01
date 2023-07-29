package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public OrderDetail save(Session session, OrderDetail entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public OrderDetail update(Session session, OrderDetail entity) {
        session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session, String s) {
        session.delete(session.get(OrderDetail.class,s));
        return session.get(OrderDetail.class,s)==null;
    }

    @Override
    public OrderDetail search(Session session, String s) {
       return session.get(OrderDetail.class,s);
    }

    @Override
    public List<OrderDetail> getAll(Session session) {
        Query query = session.createQuery("FROM OrderDetail ");
        query.setCacheable(true);
        return (List<OrderDetail>) query.getResultList();
    }
}
