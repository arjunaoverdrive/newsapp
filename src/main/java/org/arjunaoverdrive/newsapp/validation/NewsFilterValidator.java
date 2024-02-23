package org.arjunaoverdrive.newsapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import org.arjunaoverdrive.newsapp.validation.NewsFilterValid;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;

public class NewsFilterValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {

    @Override
    public boolean isValid(NewsFilter newsFilter, ConstraintValidatorContext constraintValidatorContext) {
        if(ObjectUtils.allNull(newsFilter.getAuthorId(), newsFilter.getCategoryId())) {
            return false;
        }
        return true;

    }
}
