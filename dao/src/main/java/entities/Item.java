package entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "order, product")
@ToString(exclude = "order, product")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Column
    private Integer productSize;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @Column
    private Double discount;
}

