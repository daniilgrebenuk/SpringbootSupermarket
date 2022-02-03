package com.project.repository.product;

import com.project.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  String FIND_ALL_BY_SUPPLY_ID_SQL =
      "SELECT p.* " +
      "FROM product p JOIN product_supply ps ON p.id = ps.product_id " +
      "WHERE ps.supply_id = ?1";

  String FIND_ALL_BY_STORAGE_ID_SQL =
      "SELECT p.* " +
      "FROM product p JOIN product_storage ps ON p.id = ps.product_id " +
      "WHERE ps.storage_id = ?1";

  List<Product> findAllByCategoryId(Long Id);

  @Query(value = FIND_ALL_BY_SUPPLY_ID_SQL, nativeQuery = true)
  List<Product> findAllBySupplyId(Long id);

  @Query(value = FIND_ALL_BY_STORAGE_ID_SQL, nativeQuery = true)
  List<Product> findAllByStorageId(Long storageId);
}
