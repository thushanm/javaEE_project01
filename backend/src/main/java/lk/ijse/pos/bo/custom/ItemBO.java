package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.ItemDTO;
import org.hibernate.Session;

import java.util.List;

public interface ItemBO extends SuperBO {
    ItemDTO saveItem(ItemDTO itemDTO);
    ItemDTO updateItem(ItemDTO itemDTO);
    boolean deleteItem(String id);
    ItemDTO searchItem(String id);
    List<ItemDTO> getAllItem();
}
