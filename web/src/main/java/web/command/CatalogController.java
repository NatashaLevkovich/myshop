package web.command;

import entities.Product;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.ProductService;

import java.util.List;


@Controller
public class CatalogController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/catalog", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCatalog(ModelMap model, @RequestParam(value = "cat", required = false) String category) {
        if (category == null) {
            model.put("products", productService.getAll());
        } else {
            model.put("products", productService.getProductsBySubcategory(category));
        }
        return "catalog";
    }

    @RequestMapping(value = "/catalog/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCatalogSearch(ModelMap model, @RequestParam(value = "search") String name) {
        model.put("products", productService.findByNameContaining(name));
        return "catalog";
    }
}
