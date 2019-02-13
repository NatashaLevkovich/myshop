package services;

import entities.Order;
import entities.Product;
import entities.User;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    Order saveNewOrder(User user, Product product, int productSize, int quantity) throws Exception;

    List<Order> getAll();

    List<Order> getOrdersByUser(User user);

    List<Order> getOrdersByUserAndStatus(User user, String status);

}
