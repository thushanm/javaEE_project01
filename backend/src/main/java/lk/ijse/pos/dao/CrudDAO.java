package lk.ijse.pos.dao;

import lk.ijse.pos.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO{
    T save(Session session,T entity);
    T update(Session session,T entity);
    boolean delete(Session session,ID id);
    T search(Session session,ID id);
    List<T> getAll(Session session);
}
