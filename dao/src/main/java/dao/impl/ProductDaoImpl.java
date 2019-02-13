package dao.impl;

import dao.ProductDao;
import entities.Product;
import entities.ProductDto;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    @Getter
    private EntityManager em;


    public List<ProductDto> getProductDto(Serializable orderId) {
        Session unwrap = em.unwrap(Session.class);
        List<ProductDto> productDtos = unwrap.createSQLQuery("SELECT PRODUCT.NAME, PRODUCT.PRICE, PRODUCT.IMAGE, PRODUCT.MANUFACTURER, PRODUCT.MATERIAL, ITEM.PRODUCTSIZE, " +
                "ITEM.QUANTITY, ITEM.DISCOUNT, ITEM.ID FROM ITEM  JOIN PRODUCT ON PRODUCT.ID = PRODUCT_ID WHERE ORDER_ID = :orderId")
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("price", StandardBasicTypes.DOUBLE)
                .addScalar("image", StandardBasicTypes.STRING)
                .addScalar("manufacturer", StandardBasicTypes.STRING)
                .addScalar("material", StandardBasicTypes.STRING)
                .addScalar("productsize", StandardBasicTypes.INTEGER)
                .addScalar("quantity", StandardBasicTypes.INTEGER)
                .addScalar("discount", StandardBasicTypes.DOUBLE)
                .addScalar("id", StandardBasicTypes.LONG)
                .setParameter("orderId", orderId)
                .setResultTransformer(new ResultTransformer() {
                    //                    private static final long serialVersionUID = -5815434828170704822L;
                    public Object transformTuple(Object[] arg0, String[] arg1) {
                        ProductDto dto = new ProductDto();
                        dto.setName((String) arg0[0]);
                        dto.setPrice((Double) arg0[1]);
                        dto.setImage((String) arg0[2]);
                        dto.setManufacturer((String) arg0[3]);
                        dto.setMaterial((String) arg0[4]);
                        dto.setSize((Integer) arg0[5]);
                        dto.setQuantity((Integer) arg0[6]);
                        dto.setDiscount((Double) arg0[7]);
                        dto.setItemId((Long) arg0[8]);
                        return dto;
                    }
                    @SuppressWarnings("unchecked")
                    public List transformList(List arg0) {
                        return arg0;
                    }
                }).getResultList();
        return productDtos;
    }
}