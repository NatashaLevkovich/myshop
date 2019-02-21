package web.command;

import entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.OrderService;
import services.ProductService;
import services.UserService;
import web.command.utils.Encoder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/admin")
    public String getAdminPage(ModelMap model) throws IOException {
        getPage(model);
        return "admin";
    }

    @RequestMapping("/admin/redactor")
    public String redactorProduct(ModelMap model, @Valid Product product, BindingResult bindingResult,
                                  @RequestParam(value = "size") String size, @RequestParam(value = "quantity") String quantity) throws Exception {

        if (!bindingResult.hasErrors()) {
            Product findProduct = null;
            Map<Integer, Integer> sizeQuantity = null;

            if (product.getId() != null) {
                findProduct = productService.get(product.getId());
            }

            if (size.length() > 0) {
                String[] sizes = size.split(" ");
                String[] count = quantity.split(" ");
                sizeQuantity = new HashMap<>();
                for (int i = 0; i < sizes.length; i++) {
                    if (i < count.length) {
                        sizeQuantity.put(Integer.valueOf(sizes[i]), Integer.valueOf(count[i]));
                    }
                }
                product.setSizeAndQuantity(sizeQuantity);
            }

            if (product != null && findProduct != null) {
                if (product.getName().length() > 0) {
                    findProduct.setName(product.getName());
                }
                if (product.getPrice() != null) {
                    findProduct.setPrice(product.getPrice());
                }
                if (product.getDiscount() != null) {
                    findProduct.setDiscount(product.getDiscount());
                }
                if (product.getCategory().length() > 0) {
                    findProduct.setCategory(product.getCategory());
                }
                if (product.getSubcategory().length() > 0) {
                    findProduct.setSubcategory(product.getSubcategory());
                }
                if (product.getImage().length() > 0) {
                    findProduct.setImage(product.getImage());
                }
                if (product.getManufacturer().length() > 0) {
                    findProduct.setManufacturer(product.getManufacturer());
                }
                if (product.getMaterial().length() > 0) {
                    findProduct.setMaterial(product.getMaterial());
                }
                if (sizeQuantity != null) {
                    findProduct.setSizeAndQuantity(sizeQuantity);
                }
                productService.update(findProduct);
                getPage(model);
                model.put("redproduct", findProduct);
            } else {
                productService.save(product);
                getPage(model);
                model.put("redproduct", product);
            }
        }

        return "admin";
    }

    @RequestMapping("/admin/redactoruser")
    public String redactorUser(ModelMap model, @Valid User user, BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasErrors()) {
            User findUser = null;
            if (user.getId() != null) {
                findUser = userService.get(user.getId());
            }
            if (user != null && findUser != null) {
                if (user.getFirstName().length() > 0) {
                    findUser.setFirstName(user.getFirstName());
                }
                if (user.getLastName().length() > 0) {
                    findUser.setLastName(user.getLastName());
                }
                if (user.getAddress().length() > 0) {
                    findUser.setAddress(user.getAddress());
                }
                if (user.getPhoneNumber().length() > 0) {
                    findUser.setPhoneNumber(user.getPhoneNumber());
                }
                if (user.getStatus().length() > 0) {
                    findUser.setStatus(user.getStatus());
                }
                userService.update(findUser);
                getPage(model);
                model.put("reduser", findUser);
            } else {
                if (userService.getUserByEmail(user.getEmail()) != null) {
                    model.put("errorName", "Пользователь с таким e-mail уже существует");
                    getPage(model);
                    model.put("reduser", user);
                    return "admin";
                }
                user.setPassword(Encoder.hash(user.getPassword()));
                userService.save(user);
                getPage(model);
                model.put("reduser", user);
            }
        }
        return "admin";
    }

    @RequestMapping("/admin/redactororder")
    public String redactorUser(ModelMap model, @RequestParam(value = "id") Long id, @RequestParam(value = "status") String status) throws Exception {
        Order order = null;
        try {
            order = orderService.get(id);
        } catch (EntityNotFoundException e) {
            model.put("errorOrder", "Заказа с id = " + id + " не существует");
            getPage(model);
            return "admin";
        }
        order.setStatus(status);
        orderService.update(order);
        getPage(model);
        return "admin";
    }


    @RequestMapping("/admin/download")
    protected String getOrderFile(ModelMap model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = req.getParameter("file");
        String filePath = request.getServletContext().getRealPath("/orders/");
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + fileName);

        File downloadFile = new File(filePath + fileName);
        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(downloadFile);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }

        in.close();
        out.flush();

        getPage(model);
        return "admin";
    }


    private void getPage(ModelMap model) throws IOException {
        List<Product> products = productService.getAll();
        List<User> users = userService.getAll();
        List<Order> orders = orderService.getAll();
        for (Order o : orders) {
            String filePath = request.getServletContext().getRealPath("/orders/");
            if (!new File(filePath).exists()) {
                new File(filePath).mkdir();
            }
            File file = new File(filePath + "order" + o.getId() + ".doc");
            List<File> files = new ArrayList<>();
            for (File f : new File(filePath).listFiles()) {
                files.add(f);
            }
            if (!files.contains(file) || o.getStatus().equalsIgnoreCase("new")) {
                FileWriter fw = new FileWriter(file);
                fw.write("Заказ №" + o.getId() + "\n");
                fw.write(o.getOrderDate().getTime() + "\n");
                fw.write("Общая стоимость: " + o.getTotalPrice() + " р. \n");
                for (User u : users) {
                    if (u.getId() == o.getUser().getId()) {
                        fw.write("ID клиента: " + u.getId() + "\n");
                        fw.write("Ф.И.О.: " + u.getFirstName() + " " + u.getLastName() + "\n");
                        fw.write("Адрес: " + u.getAddress() + "\n");
                        fw.write("тел.: " + u.getPhoneNumber() + "\n");
                    }
                }
                for (ProductDto dto : productService.getDto(o)) {
                    fw.write(dto.toString());
                }
                fw.flush();
                fw.close();
            }
        }
        model.put("redproduct", new Product());
        model.put("reduser", new User());
        model.put("products", products);
        model.put("users", users);
        model.put("orders", orders);
    }
}
