package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import org.hibernate.Session;

import java.util.List;

public interface PurchaseOrderBO extends SuperBO {
    OrderDTO saveOrder(OrderDTO itemDTO);
    OrderDTO updateOrder(OrderDTO itemDTO);
    boolean deleteOrder(String id);
    OrderDTO searchOrder(String id);
    List<OrderDTO> getAllOrders();
}
