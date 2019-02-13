package services.impl;

import dao.*;
import dao.impl.*;
import dao.repository.ItemRepository;
import dao.repository.OrderJpaRepository;
import dao.repository.ProductRepository;
import dao.repository.UserRepository;
import entities.*;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.OrderService;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Order saveNewOrder(User user, Product product, int productSize, int quantity) throws Exception{
        Order order = new Order();
        order.setOrderDate(Calendar.getInstance());
        order.setUser(user);
        order.setStatus("new");
        Map<Integer, Integer> sizes = ItemServiceImpl.updateQuantity(product,productSize, quantity);
        product.setSizeAndQuantity(sizes);
        product = productRepository.save(product);
        order.setTotalPrice((product.getPrice() - (product.getPrice() * product.getDiscount())) * quantity);
        order = orderJpaRepository.save(order);
        Item item = new Item(null, order, product, productSize, quantity, product.getPrice(), product.getDiscount());
        itemRepository.save(item);
        order = orderJpaRepository.save(order);
        return order;
    }

    @Override
    public Order save(Order order){
        return orderJpaRepository.save(order);
    }

    @Override
    public Order get(Serializable id) {
        Order order = orderJpaRepository.getOne((Long) id);
        order.setItems(itemRepository.findByOrder(order));
        return order;
    }

    @Override
    public Order update(Order order) {
        List<Item> items = itemRepository.findByOrder(order);
        double totalPrice = 0;
        for (Item item : items) {
            Double price = (item.getPrice() - (item.getPrice() * item.getDiscount())) * item.getQuantity();
            totalPrice += price;
        }
        order.setTotalPrice(totalPrice);
        orderJpaRepository.save(order);
        if (items.isEmpty()){
            orderJpaRepository.delete(order);
        }
        return order;
    }

    @Override
    public void delete(Order order) {
        orderJpaRepository.delete(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> list = orderJpaRepository.findAll();
        for (Order order : list) {
            order.setItems(itemRepository.findByOrder(order));
        }
        return list;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        List<Order> list = orderJpaRepository.findByUser(user);
        for (Order order : list) {
            order.setItems(itemRepository.findByOrder(order));
        }
        return list;
    }

    @Override
    public List<Order> getOrdersByUserAndStatus(User user, String status) {
        List<Order> list = orderJpaRepository.findByUserAndStatus(user, status);
        for (Order order : list) {
            order.setItems(itemRepository.findByOrder(order));
        }
        return list;
    }
}
