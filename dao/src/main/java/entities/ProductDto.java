package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long itemId;
    private String name;
    private Double price;
    private Double discount;
    private String image;
    private String manufacturer;
    private String material;
    private Integer size;
    private Integer quantity;

    @Override
    public String toString() {
        return "Название: " + name +
                ", цена: " + price +
                ", скидка: " + discount +
                ", размер: " + size +
                ", количество: " + quantity + "\n";
    }
}
