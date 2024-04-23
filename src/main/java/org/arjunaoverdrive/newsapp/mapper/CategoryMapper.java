package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.Category;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryListResponse;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryRequest;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    default Category toCategory(Integer id, CategoryRequest request){
        Category category = toCategory(request);
        category.setId(id);
        return category;
    };

    @Mapping(target = "news", expression = "java(category.getNews().size())")
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
