package com.alxkls.eshop_backend.repository.category;

import com.alxkls.eshop_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
