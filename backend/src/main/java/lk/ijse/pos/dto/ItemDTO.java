package lk.ijse.pos.dto;

import lk.ijse.pos.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    public ItemDTO(String code, String description, int qty, double unitPrice) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    String code;
    String description;
    int qty;
    double unitPrice;
    List<OrderDetailDTO> orderDetailList;
}
