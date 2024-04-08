package org.arjunaoverdrive.newsapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import org.arjunaoverdrive.newsapp.validation.annotations.NewsFilterValid;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;

public class NewsFilterValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {

    @Override
    public boolean isValid(NewsFilter newsFilter, ConstraintValidatorContext constraintValidatorContext) {
        return !ObjectUtils.allNull(newsFilter.getAuthorId(), newsFilter.getCategoryId());
    }
}
