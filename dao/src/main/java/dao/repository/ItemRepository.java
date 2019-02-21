package dao.repository;

import entities.Item;
import entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOrder(Order order);
}
