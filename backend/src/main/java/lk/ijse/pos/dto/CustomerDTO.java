package lk.ijse.pos.dto;

import lk.ijse.pos.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    public CustomerDTO(String customerID, String name, String address, double salary) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    String customerID;
    String name;
    String address;
    double salary;
@ToString.Exclude
    List<OrderDTO> orderList;
}
