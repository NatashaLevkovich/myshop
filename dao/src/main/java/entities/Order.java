package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @Column
    private Double totalPrice;

    @Column
    private String status;

    @Column
    private Calendar orderDate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderDate=" + orderDate.get(Calendar.DAY_OF_MONTH) +
                "." + (orderDate.get(Calendar.MONTH) + 1) +
                "." + orderDate.get(Calendar.YEAR) +
                '}';
    }
}
