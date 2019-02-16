package web.command;

import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.ProductService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CatalogController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/catalog", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCatalog(ModelMap model, @RequestParam(value = "cat", required = false) String subcategory, HttpSession session) {
        if (subcategory == null) {
            session.removeAttribute("subcategory");
            session.removeAttribute("sort");
            session.setAttribute("size", 6);
            session.setAttribute("page", 1);
            List<Product> products = productService.getAll();
            setPageCount(products.size(), 6, model);
            model.put("products", productService.getPageProduct(1, 6, null));
        } else {
            session.removeAttribute("sort");
            session.setAttribute("size", 6);
            session.setAttribute("subcategory", subcategory);
            session.setAttribute("page", 1);
            List<Product> products = productService.getProductsBySubcategory(subcategory);
            setPageCount(products.size(), 6, model);
            model.put("products", productService.getPageProductBySubcategory(1, 6, null, subcategory));
        }
        return "catalog";
    }

    @RequestMapping(value = "/catalog/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCatalogSearch(ModelMap model, @RequestParam(value = "search") String name) {
        model.put("products", productService.findByNameContaining(name));
        return "catalog";
    }

    @RequestMapping("/catalog/size")
    public String getCatalogBySize(ModelMap model, @RequestParam(value = "size", defaultValue = "6") int size, HttpSession session) {
        String sort = (String) session.getAttribute("sort");
        String subcategory = (String) session.getAttribute("subcategory");
        Integer page = (Integer) session.getAttribute("page");
        session.setAttribute("size", size);
        setProducts(page, size, sort, subcategory, model);
        return "catalog";
    }

    @RequestMapping("/catalog/sort")
    public String getCatalogBySort(ModelMap model, @RequestParam(value = "sort") String sort, HttpSession session) {
        Integer size = (Integer) session.getAttribute("size");
        Integer page = (Integer) session.getAttribute("page");
        String subcategory = (String) session.getAttribute("subcategory");
        session.setAttribute("sort", sort);
        setProducts(page, size, sort, subcategory, model);
        return "catalog";
    }

    @RequestMapping("/catalog/page")
    public String getCatalogBySort(ModelMap model, @RequestParam(value = "page") int page, HttpSession session) {
        Integer size = (Integer) session.getAttribute("size");
        String sort = (String) session.getAttribute("sort");
        String subcategory = (String) session.getAttribute("subcategory");
        session.setAttribute("page", page);
        setProducts(page, size, sort, subcategory, model);
        return "catalog";
    }

    private void setPageCount(int productsSize, int size, ModelMap model) {
        int pageCount = productsSize / size;

        if (productsSize % size > 0) {
            pageCount += 1;
        }

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }
        
        model.put("pages", pages);
    }

    private void setProducts(int page, int size, String sort, String subcategory, ModelMap model) {
        if (subcategory != null) {
            List<Product> products = productService.getProductsBySubcategory(subcategory);
            setPageCount(products.size(), size, model);
            model.put("products", productService.getPageProductBySubcategory(page, size, sort, subcategory));
        } else {
            List<Product> products = productService.getAll();
            setPageCount(products.size(), size, model);
            model.put("products", productService.getPageProduct(page, size, sort));
        }
    }
}
