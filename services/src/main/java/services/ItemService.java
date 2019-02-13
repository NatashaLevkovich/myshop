package services;

import entities.Item;
import entities.Order;
import entities.Product;

import java.io.Serializable;
import java.util.List;

public interface ItemService extends BaseService<Item> {
    Item saveNewItem(Order order, Product product, int productSize, int quantity) throws Exception;

    List<Item> getAll();

    List<Item> getItemsByOrder(Order order);

    Item update(Item item) throws Exception;
}
