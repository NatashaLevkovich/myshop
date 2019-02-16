package dao.repository;

import entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    List<Product> findByCategory(String category);

    List<Product> findBySubcategory(String subcategory);

    List<Product> findByDiscountGreaterThanEqual(Double discount);

    Page<Product> findAllBySubcategory(String subcategory, Pageable pageable);

}
