package web.command;

import entities.*;
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
public class ItemController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @RequestMapping("/item")
    public String addItem(ModelMap model, @RequestParam(value = "id") Long id, @RequestParam(value = "size") Integer size,
                          @RequestParam(value = "quantity") Integer quantity, HttpSession session) {

        Product product = productService.get(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = CommandUtils.getUserByAuth(auth, userService);
        Order order;

        List<Order> orders = orderService.getOrdersByUserAndStatus(user, "new");
        Map<Integer, Integer> sizeAndQuantity = product.getSizeAndQuantity();
        int maxQuantity = 0;

        for (Map.Entry<Integer, Integer> map : sizeAndQuantity.entrySet()) {
            if (map.getKey() == size) {
                maxQuantity = map.getValue();
                if (map.getValue() < quantity) {
                   session.setAttribute("error", "Недостаточно товара, в наличии " + map.getValue());
                    return "redirect:/product?id=" + product.getId();
                }
            }
        }

        if (orders.isEmpty()) {
            try {
                order = orderService.saveNewOrder(user, product, size, quantity);
            } catch (Exception e) {
                session.setAttribute("error", "Ошибка создания заказа");
                return "redirect:/product?id=" + product.getId();
            }
            session.setAttribute("items", 1);
            model.put("productinfo", product);
            return "redirect:/product?id=" + product.getId();
        } else {
            order = orders.get(0);
            List<Item> items = itemService.getItemsByOrder(order);
            for (Item item : items) {
                if (item.getProduct().getId().equals(product.getId()) && item.getProductSize().equals(size)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    try {
                        itemService.update(item);
                    } catch (Exception e) {
                        session.setAttribute("error", "Недостаточно товара, в наличии " + maxQuantity);
                        return "redirect:/product?id=" + product.getId();
                    }
                    session.setAttribute("items", itemService.getItemsByOrder(order).size());
                    model.put("productinfo", product);
                    return "redirect:/product?id=" + product.getId();
                } else {
                    try {
                        itemService.saveNewItem(order, product, size, quantity);
                    } catch (Exception e) {
                        session.setAttribute("error", "Ошибка создания заказа");
                        return "redirect:/product?id=" + product.getId();
                    }
                    session.setAttribute("items", itemService.getItemsByOrder(order).size());
                    model.put("productinfo", product);
                    return "redirect:/product?id=" + product.getId();
                }
            }
        }
        return "redirect:/product?id=" + product.getId();
    }


}

