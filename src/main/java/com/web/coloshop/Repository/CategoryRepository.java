package com.web.coloshop.Repository;

import com.web.coloshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Add custom queries if needed
}
