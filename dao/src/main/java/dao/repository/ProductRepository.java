package dao.repository;

import entities.Order;
import entities.Product;
import entities.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    List<Product> findByCategory(String category);

    List<Product> findBySubcategory(String subcategory);

    List<Product> findByDiscountGreaterThanEqual(Double discount);

    Page<Product> findAllBySubcategory(String subcategory, Pageable pageable);

    @Query("SELECT new entities.ProductDto (p.name, p.price, p.image, p.manufacturer, p.material, i.productSize, i.quantity, i.discount, i.id) FROM Item i JOIN Product p ON p = i.product WHERE i.order = :order")
    List<ProductDto> getProductDto(@Param("order") Order order);

}
