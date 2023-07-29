package lk.ijse.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Cacheable
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderDetail implements SuperEntity{
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderID")
    Order order;
    int qty;
    int discount;
    double unitPrice;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemCode")
    Item item;

}
