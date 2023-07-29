package lk.ijse.pos.dto;

import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO implements SuperEntity {
    OrderDTO order;
    int qty;
    int discount;
    double unitPrice;
    ItemDTO item;

}
