package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.bo.util.FactoryConfiguration;
import lk.ijse.pos.dao.DAOType;
import lk.ijse.pos.dao.FactoryDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    OrderDAO orderDAO=(OrderDAO) FactoryDAO.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO= (OrderDetailDAO) FactoryDAO.getInstance().getDAO(DAOType.ORDER_DETAIL);
    Session session;
    Transaction transaction;
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public OrderDTO saveOrder( OrderDTO orderDTO) {
        openSession();
        orderDAO.save(session,modelMapper.map(orderDTO, Order.class));
        closeSession();
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder( OrderDTO orderDTO) {
        openSession();
        orderDAO.update(session,modelMapper.map(orderDTO, Order.class));
        closeSession();
        return orderDTO;
    }

    @Override
    public boolean deleteOrder( String id) {
        openSession();
        boolean delete = orderDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public OrderDTO searchOrder( String id) {
        openSession();
        OrderDTO orderDTO = modelMapper.map(orderDAO.search(session, id), OrderDTO.class);
        closeSession();
        return orderDTO;

    }

    @Override
    public List<OrderDTO> getAllOrders() {
        openSession();
      List<OrderDTO> list= modelMapper.map(orderDAO.getAll(session),new TypeToken<List<OrderDTO>>(){}.getType());
        closeSession();
        return list;
    }

    @Override
    public void openSession() {
        session= FactoryConfiguration.getInstance().getSession();
        transaction=session.beginTransaction();
    }

    @Override
    public void closeSession() {
        transaction.commit();
        session.close();
    }
}
