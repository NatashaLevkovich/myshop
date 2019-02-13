package web.command;

import entities.Order;
import entities.User;
import entities.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.OrderService;
import services.ProductService;
import services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping("/orders")
    public String getOrders(ModelMap model, @RequestParam(value = "orderId", required = false) Long id) {

        if (id != null) {
            Order order = orderService.get(id);
            order.setStatus("COMPLETED");
            orderService.save(order);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = CommandUtils.getUserByAuth(auth, userService);

        List<Order> orders = orderService.getOrdersByUser(user);
        Map<Long, List<ProductDto>> map = new HashMap<>();
        model.put("orders", orders);
        for (Order o : orders) {
            map.put(o.getId(), productService.getDto(o.getId()));
        }
        model.put("productDto", map);
        return "orders";
    }
}
