package ru.blog.validation.validators;

import org.springframework.data.util.ReflectionUtils;
import ru.blog.validation.annotations.NotSameNames;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class NamesValidator implements ConstraintValidator<NotSameNames, Object> {

    private String[] fields;

    @Override
    public void initialize(NotSameNames constraintAnnotation) {
        this.fields = constraintAnnotation.names();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        List<String> fieldValues = new ArrayList<>();

        for (String fieldName : fields) {
            try {
                Field field = ReflectionUtils.findRequiredField(object.getClass(), fieldName);
                field.setAccessible(true);
                fieldValues.add((String) field.get(object));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return fieldValues.size() == fieldValues.stream().distinct().count();
    }
}
