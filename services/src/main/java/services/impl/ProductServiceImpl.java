package services.impl;

import dao.ProductDao;
import dao.repository.ProductRepository;
import entities.Product;
import entities.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.ProductService;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Serializable id) {
        Product product = productRepository.getOne((Long) id);
        product.getName();
        return product;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByDiscount(double discount) {
        return productRepository.findByDiscountGreaterThanEqual(discount);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsBySubcategory(String subcategory) {
        return productRepository.findBySubcategory(subcategory);
    }

    @Override
    public List<ProductDto> getDto(Serializable orderId) {
        return productDao.getProductDto(orderId);
    }

    public List<Product> getPageProduct(int page, int size, String sort) {

        if (sort != null && sort.equals("name")) {
            return productRepository.findAll(PageRequest.of(page - 1, size, Sort.Direction.ASC, "name")).getContent();
        }

        if (sort != null && sort.equalsIgnoreCase("priceAsc")) {
            return productRepository.findAll(PageRequest.of(page - 1, size, Sort.Direction.ASC, "price")).getContent();
        }

        if (sort != null && sort.equalsIgnoreCase("priceDesc")) {
            return productRepository.findAll(PageRequest.of(page - 1, size, Sort.Direction.DESC, "price")).getContent();
        }

        return productRepository.findAll(PageRequest.of(page - 1, size)).getContent();

    }

    public List<Product> getPageProductBySubcategory(int page, int size, String sort, String subcategory) {

        if (sort != null && sort.equals("name")) {
            return productRepository.findAllBySubcategory(subcategory, PageRequest.of(page - 1, size, Sort.Direction.ASC, "name")).getContent();
        }

        if (sort != null && sort.equalsIgnoreCase("priceAsc")) {
            return productRepository.findAllBySubcategory(subcategory, PageRequest.of(page - 1, size, Sort.Direction.ASC, "price")).getContent();
        }

        if (sort != null && sort.equalsIgnoreCase("priceDesc")) {
            return productRepository.findAllBySubcategory(subcategory, PageRequest.of(page - 1, size, Sort.Direction.DESC, "price")).getContent();
        }

        return productRepository.findAllBySubcategory(subcategory, PageRequest.of(page - 1, size)).getContent();

    }
}
