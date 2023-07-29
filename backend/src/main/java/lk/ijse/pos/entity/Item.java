package lk.ijse.pos.entity;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Cacheable
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item implements SuperEntity {
    @Id
    String code;
    String description;
    int qty;
    double unitPrice;
    @OneToMany(orphanRemoval = true, mappedBy = "item", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<OrderDetail> orderDetailList;

}
