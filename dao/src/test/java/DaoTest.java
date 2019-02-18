import dao.ProductDao;
import dao.repository.*;
import entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Rollback(false)
public class DaoTest {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Product product1 = new Product();
    private Product product2 = new Product();
    private Product product3 = new Product();
    private Order order = new Order();
    private Item item = new Item();
    private User user = new User();

    @Before
    @Transactional
    public void test() throws SQLException {
        product1.setName("майка");
        product1.setPrice(5.25);
        product1.setDiscount(0.0);
        Map<Integer, Integer> sizes1 = new HashMap<>();
        sizes1.put(100, 5);
        sizes1.put(110, 5);
        product1.setSizeAndQuantity(sizes1);
        product2.setName("майка для девочки");
        product2.setPrice(1.8);
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
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        user.setFirstName("Ira");
        userRepository.save(user);
        order.setUser(user);
        order.setOrderDate(Calendar.getInstance());
        order.setStatus("new");
        orderJpaRepository.save(order);
        item.setProduct(product1);
        item.setOrder(order);
        itemRepository.save(item);
    }

    @Test
    @Transactional
    public void repositoryTest() {
        Item item1 = itemRepository.findById(item.getId()).get();
        System.out.println(item1);
//        itemRepository.deleteById(3L);
//        System.out.println(itemRepository.existsById(3L));
        List<Item> items = itemRepository.findByOrder(order);
        System.out.println(items.get(0));
        orderJpaRepository.findByUser(user).forEach(System.out::println);
        System.out.println(orderJpaRepository.findOne(Example.of(new Order(null, null, null, null, "new", null))));
//        productDao.getProductDto(order.getId()).forEach(System.out::println);
//        productRepository.findAll().forEach(System.out::println);
        productRepository.getProductDto(order).forEach(System.out::println);
    }

    @Test
    @Transactional
    public void pagesTest(){
        productRepository.findAll(PageRequest.of(0, 2, Sort.Direction.ASC, "price"))
        .forEach(System.out::println);
        productRepository.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "price"))
                .forEach(System.out::println);
    }
}
