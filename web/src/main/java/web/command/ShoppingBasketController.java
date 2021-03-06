package web.command;

import entities.*;
import entities.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.ItemService;
import services.OrderService;
import services.ProductService;
import services.UserService;
import web.command.utils.CommandUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ShoppingBasketController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @RequestMapping("/shoppingbasket")
    public String getBasket(ModelMap model, @RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "id", required = false) Long id, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = CommandUtils.getUserByAuth(auth, userService);
        Order order = new Order();

        List<Order> orders = orderService.getOrdersByUserAndStatus(user, "new");
        if (!orders.isEmpty()) {
            order = orders.get(0);
        }

        if ("del".equals(action)) {
            Item item = itemService.get(id);
            itemService.delete(item);
            Product product = productService.get(item.getProduct().getId());
            Map<Integer, Integer> productSize = product.getSizeAndQuantity();
            for (Map.Entry<Integer, Integer> map : productSize.entrySet()) {
                if (map.getKey().equals(item.getProductSize())) {
                    productSize.put(item.getProductSize(), map.getValue() + item.getQuantity());
                }
            }
            try {
                productService.update(product);
            } catch (Exception e) {
                return "basket";
            }

            session.setAttribute("items", itemService.getItemsByOrder(order).size());
        }

        orders = orderService.getOrdersByUserAndStatus(user, "new");
        if (!orders.isEmpty()) {
            order = orders.get(0);
            List<ProductDto> productDtoList = productService.getDto(order);
            if (productDtoList != null) {
                model.put("productDto", productDtoList);
            } else {
                orderService.delete(order);
            }
        } else {
            order = new Order();
        }
        model.put("order", order);
        return "basket";
    }

}
