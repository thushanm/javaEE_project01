package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.util.FactoryConfiguration;
import lk.ijse.pos.dao.DAOType;
import lk.ijse.pos.dao.FactoryDAO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class CustomerBOImpl implements CustomerBO {

        CustomerDAO customerDAO= (CustomerDAO) FactoryDAO.getInstance().getDAO(DAOType.CUSTOMER);
        Session session;
        Transaction transaction;
        ModelMapper modelMapper = new ModelMapper();
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        openSession();
       customerDAO.save(session,modelMapper.map(customerDTO, Customer.class));
       closeSession();
       return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer( CustomerDTO customerDTO) {
        openSession();
        customerDAO.update(session,modelMapper.map(customerDTO, Customer.class));
        closeSession();
        return customerDTO;
    }

    @Override
    public boolean deleteCustomer( String id) {
        openSession();
        boolean delete = customerDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public CustomerDTO searchCustomer( String id) {
        openSession();
        CustomerDTO customerDTO = modelMapper.map(customerDAO.search(session, id), CustomerDTO.class);
        closeSession();
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        openSession();
        List<CustomerDTO> list =modelMapper.map(customerDAO.getAll(session), new TypeToken<List<CustomerDTO>>(){}.getType());
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
