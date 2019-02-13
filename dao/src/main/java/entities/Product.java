package entities;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(exclude = "items")
@ToString(exclude = "items")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private Double discount;

    @Column
    private String category;

    @Column
    private String subcategory;

    @Column
    private String image;

    @Column
    private String manufacturer;

    @Column
    private String material;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Integer, Integer> sizeAndQuantity;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;
  }
