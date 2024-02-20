package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.Category;
import org.arjunaoverdrive.newsapp.web.dto.CategoryListResponse;
import org.arjunaoverdrive.newsapp.web.dto.CategoryRequest;
import org.arjunaoverdrive.newsapp.web.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    Category toCategory(Integer id, CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

   default CategoryListResponse toCategoryListResponse(List<Category> categories){
       CategoryListResponse response = new CategoryListResponse();
       response.setCategories(
               categories.stream()
                       .map(this::toCategoryResponse)
                       .toList()
       );
       return response;
   }
}
