package services.impl;

import dao.repository.ItemRepository;
import dao.repository.ProductRepository;
import entities.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.ItemService;
import services.OrderService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Slf4j
@Transactional
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Item saveNewItem(Order order, Product product, int productSize, int quantity) throws Exception {
        Map<Integer, Integer> sizes = updateQuantity(product, productSize, quantity);
        product.setSizeAndQuantity(sizes);
        product = productRepository.save(product);
        order = orderService.get(order.getId());
        Item item = new Item(null, order, product, productSize, quantity, product.getPrice(), product.getDiscount());
        item = itemRepository.save(item);
        orderService.update(order);
        return item;
    }

    @Override
    public Item save(Item item){
        return itemRepository.save(item);
    }

    @Override
    public Item get(Serializable id) {
        Item item = itemRepository.getOne((Long) id);
        item.getQuantity();
        return item;
    }

    @Override
    public Item update(Item item) throws Exception {
        Item savedItem = itemRepository.getOne(item.getId());
        int quantity = item.getQuantity() - savedItem.getQuantity();
        Product product = item.getProduct();
        Map<Integer, Integer> sizes = product.getSizeAndQuantity();
        for (Map.Entry<Integer, Integer> entry : sizes.entrySet()) {
            if (entry.getKey() == item.getProductSize()) {
                if (entry.getValue() - quantity >= 0) {
                    sizes.put(item.getProductSize(), entry.getValue() - quantity);
                } else {
                    throw new ServiceException("Недостаточно товара");
                }
            }
        }
        product.setSizeAndQuantity(sizes);
        productRepository.save(product);
        itemRepository.save(item);
        orderService.update(item.getOrder());
        return item;
    }

    @Override
    public void delete(Item item) {
        itemRepository.deleteById(item.getId());
        try {
            orderService.update(item.getOrder());
        } catch (Exception e) {
            log.error("Ошибка удаления");
        }
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByOrder(Order order) {
        return itemRepository.findByOrder(order);
    }

    public static Map<Integer,Integer> updateQuantity(Product product, int productSize, int quantity){
        Map<Integer, Integer> sizes = product.getSizeAndQuantity();
        for (Map.Entry<Integer, Integer> entry : sizes.entrySet()) {
            if (entry.getKey() == productSize) {
                if (quantity <= entry.getValue()) {
                    sizes.put(productSize, entry.getValue() - quantity);
                } else {
                    throw new ServiceException("Недостаточно товара");
                }
            }
        }
        return sizes;
    }
}
