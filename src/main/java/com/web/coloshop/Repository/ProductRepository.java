package com.web.coloshop.Repository;

import com.web.coloshop.model.Category;
import com.web.coloshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByActivated();
}
