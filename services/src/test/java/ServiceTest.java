import entities.Item;
import entities.Order;
import entities.Product;
import entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.ItemService;
import services.OrderService;
import services.ProductService;
import services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-test.xml")
@Rollback(false)
@Transactional
public class ServiceTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private User user = new User();
    private Product product1 = new Product();
    private Product product2 = new Product();
    private Product product3 = new Product();
    private Order order;
    private User newUser;

    @Before
    public void save() throws Exception{
        user.setEmail("e@mail.com");
        user.setPassword("123");
        product1.setName("майка");
        product1.setPrice(5.25);
        product1.setDiscount(0.0);
        Map<Integer, Integer> sizes1 = new HashMap<>();
        sizes1.put(100, 5);
        sizes1.put(110, 5);
        product1.setSizeAndQuantity(sizes1);
        product2.setName("майка для девочки");
        product2.setPrice(1.2);
        product2.setDiscount(0.1);
        Map<Integer, Integer> sizes2 = new HashMap<>();
        sizes2.put(100, 5);
        sizes2.put(110, 5);
        product2.setSizeAndQuantity(sizes2);
        product3.setName("MM");
        product3.setPrice(1.2);
        product3.setSizeAndQuantity(sizes2);
        product3.setDiscount(0.1);
        Map<Integer, Integer> sizes3 = new HashMap<>();
        sizes3.put(100, 5);
        sizes3.put(110, 5);
        product3.setSizeAndQuantity(sizes3);
        userService.save(user);
        productService.save(product1);
        productService.save(product2);
        order = orderService.saveNewOrder(user, product1, 100, 2);
    }

    @Test
    public void test() throws Exception{
        Item item = itemService.saveNewItem(order, product2, 110, 2);
        System.out.println(itemService.getItemsByOrder(order));
        List<Product> products = productService.findByNameContaining("майка");
        products.forEach(System.out::println);
        User newUser = userService.getUserByEmail("e@mail.com");
        Order newOrder = orderService.get(order.getId());
        int listSize = newOrder.getItems().size();
        itemService.delete(item);
        int listSizeAfterDelete = orderService.get(newOrder.getId()).getItems().size();
        Assert.assertEquals(user, newUser);
        Assert.assertEquals(listSize, listSizeAfterDelete);
        itemService.delete(item);
    }

    @Test
    @Transactional
    public void pageTest(){
        productService.getPageProduct(0, 2, null).forEach(System.out::println);
    }

    @After
    public void delete(){
        List<Item> items = itemService.getItemsByOrder(order);
        for (int i = 0; i < items.size(); i++){
            itemService.delete(items.get(i));
        }
 //       orderService.delete(order);
    }
}
