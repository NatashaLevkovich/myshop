package web.command;

import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.ProductService;


@Controller
public class ProductController{

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public String productInfo(ModelMap model, @RequestParam(value = "id") Long id) {
        Product product = productService.get(id);
        model.put("productinfo", product);
       return "product";
    }
}
