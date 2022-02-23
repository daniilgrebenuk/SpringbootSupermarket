package com.project.repository.product;

import com.project.model.product.Product;
import com.project.model.product.helper.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  String FIND_ALL_BY_SUPPLY_ID_SQL =
      "SELECT p.id, p.name, ps.amount " +
      "FROM product p JOIN product_supply ps ON p.id = ps.product_id " +
      "WHERE ps.supply_id = ?1";

  String FIND_ALL_BY_STORAGE_ID_SQL =
      "SELECT p.* " +
      "FROM product p JOIN product_storage ps ON p.id = ps.product_id " +
      "WHERE ps.storage_id = ?1";

  String FIND_ALL_BY_ORDER_ID_SQL =
      "SELECT p.* " +
          "FROM product p JOIN product_order po ON p.id = po.product_id " +
          "WHERE po.storage_id = ?1";

  List<Product> findAllByCategoryId(Long Id);

  @Query(value = FIND_ALL_BY_SUPPLY_ID_SQL, nativeQuery = true)
  List<ProductResponse> findAllBySupplyId(Long id);

  @Query(value = FIND_ALL_BY_STORAGE_ID_SQL, nativeQuery = true)
  List<Product> findAllByStorageId(Long storageId);

  @Query(value = FIND_ALL_BY_ORDER_ID_SQL, nativeQuery = true)
  List<Product> findAllByOrderId(Long orderId);
}
