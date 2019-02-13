package dao;

import entities.Product;
import entities.ProductDto;

import java.io.Serializable;
import java.util.List;

public interface ProductDao {
    List<ProductDto> getProductDto(Serializable orderId);
}
