package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.bo.util.FactoryConfiguration;
import lk.ijse.pos.dao.DAOType;
import lk.ijse.pos.dao.FactoryDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) FactoryDAO.getInstance().getDAO(DAOType.ITEM);
    Session session;
    Transaction transaction;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {
        openSession();
        itemDAO.save(session, modelMapper.map(itemDTO, Item.class));
        closeSession();
        return itemDTO;
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {
        openSession();
        itemDAO.update(session, modelMapper.map(itemDTO, Item.class));
        closeSession();
        return itemDTO;
    }

    @Override
    public boolean deleteItem(String id) {
        openSession();
        boolean delete = itemDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public ItemDTO searchItem(String id) {
        openSession();
        ItemDTO itemDTO = modelMapper.map(itemDAO.search(session, id), ItemDTO.class);
        closeSession();
        return itemDTO;
    }

    @Override
    public List<ItemDTO> getAllItem() {
        openSession();
        List<ItemDTO> list = modelMapper.map(itemDAO.getAll(session), new TypeToken<List<ItemDTO>>() {
        }.getType());
        closeSession();
        return list;
    }

    @Override
    public void openSession() {
        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
    }

    @Override
    public void closeSession() {
        transaction.commit();
        session.close();
    }
}
