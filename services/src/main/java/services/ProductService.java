package services;

import entities.Product;
import entities.ProductDto;
import java.io.Serializable;
import java.util.List;

public interface ProductService extends BaseService<Product>{

    List<Product> getAll();

    List<Product> findByNameContaining(String name);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsBySubcategory(String subcategory);

    List<Product> getProductsByDiscount(double discount);

    List<ProductDto> getDto(Serializable orderId);

    List<Product> getPageProduct(int page, int size, String sort);

    List<Product> getPageProductBySubcategory(int page, int size, String sort, String subcategory);
}
