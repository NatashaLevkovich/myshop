import dao.ProductDao;
import dao.repository.*;
import entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
//@Rollback(false)
public class DaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Product product = new Product();
    private Order order = new Order();
    private Item item = new Item();
    private User user = new User();

    @Before
    @Transactional
    public void test() throws SQLException {
        product.setName("P1");
        product.setPrice(5.25);
        Map<Integer, Integer> sizes = new HashMap<>();
        sizes.put(100, 5);
        sizes.put(110, 10);
        product.setSizeAndQuantity(sizes);
        productRepository.save(product);
        user.setFirstName("Ira");
        userRepository.save(user);
        order.setUser(user);
        order.setOrderDate(Calendar.getInstance());
        order.setStatus("new");
        orderJpaRepository.save(order);
        item.setProduct(product);
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
        productDao.getProductDto(order.getId()).forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);
    }
}
