package web.command;


import entities.Order;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.ItemService;
import services.OrderService;
import services.ProductService;
import services.UserService;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String getMainPage(ModelMap model) {
        model.put("products", productService.getAll());
        model.put("saleproducts", productService.getProductsByDiscount(0.2));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                User user = userService.getUserByEmail(userDetails.getUsername());
                List<Order> orders = orderService.getOrdersByUserAndStatus(user, "new");
                if (!orders.isEmpty()) {
                    model.put("items", itemService.getItemsByOrder(orders.get(0)).size());
                }
            }
        }
        return "main";
    }

}