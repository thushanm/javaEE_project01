package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAOImpl  implements ItemDAO {
    @Override
    public Item save(Session session, Item entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public Item update(Session session, Item entity) {
        session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session, String s) {
        session.delete(session.get(Item.class,s));
        return session.get(Item.class,s)==null;
    }

    @Override
    public Item search(Session session, String s) {
        return session.get(Item.class,s);
    }

    @Override
    public List<Item> getAll(Session session) {
        Query query = session.createQuery("FROM Item");
        query.setCacheable(true);
       return (List<Item>) query.getResultList();
    }
}
