package org.arjunaoverdrive.newsapp.dao;

import org.arjunaoverdrive.newsapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
