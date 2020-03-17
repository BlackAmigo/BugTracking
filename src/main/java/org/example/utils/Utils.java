package org.example.utils;

import org.example.services.ValidationResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utils {

    /**
     * Метод копирует, не null поля и не одинаковые значения полей source объекта в target объект
     *
     * @param source - объект, откуда необходимо скопировать свойства
     * @param target - объект, куда необходимо скопировать свойства
     * @return ValidationResult<Entity> результат копирования объекта
     */
    public static <Entity> ValidationResult<Entity> copyProperties(Entity source, Entity target) {
        Map<String, String> errorsMap = new HashMap<>();
        if (target != null && source != null) {
            copyNotNullNotSameProperties(source, target);
            errorsMap = getValidationError(target);
            return new ValidationResult<>(target, errorsMap);
        } else {
            errorsMap.put("source", "isNull=" + (source != null));
            errorsMap.put("target", "isNull=" + (target != null));
            return new ValidationResult<>(null, errorsMap);
        }
    }

    /**
     * Метод копирует, не null поля и не одинаковые значения полей source объекта в target объект
     *
     * @param source - объект, откуда необходимо скопировать свойства
     * @param target - объект, куда необходимо скопировать свойства
     */
    public static void copyNotNullNotSameProperties(Object source, Object target) {
        String[] arr1 = getNullProperties(source);
        String[] arr2 = getSameProperties(source, target);
        String[] arr3 = new String[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            arr3[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            arr3[i + arr1.length] = arr2[i];
        }
        BeanUtils.copyProperties(source, target, arr3);
    }

    /**
     * Метод получает массив полей с одинаковыми значениями объектов object_1 и object_2
     *
     * @param object_1 - объект, для сравнения полей
     * @param object_2 - объект, для сравнения полей
     * @return String[] полей с одинаковыми значениями объектов object_1 и object_2
     */
    private static String[] getSameProperties(Object object_1, Object object_2) {
        final BeanWrapper beanWrapper_1 = new BeanWrapperImpl(object_1);
        final BeanWrapper beanWrapper_2 = new BeanWrapperImpl(object_2);
        PropertyDescriptor[] pdArr_1 = beanWrapper_1.getPropertyDescriptors();
        PropertyDescriptor[] pdArr_2 = beanWrapper_2.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();

        for (int i = 0; i < pdArr_1.length; i++) {
            Object sourceValue = beanWrapper_1.getPropertyValue(pdArr_1[i].getName());
            Object targetValue = beanWrapper_2.getPropertyValue(pdArr_2[i].getName());
            if (sourceValue != null && targetValue != null) {
                if (sourceValue.toString().equals(targetValue.toString())) {
                    emptyNames.add(pdArr_1[i].getName());
                }
            }
        }

        return emptyNames.toArray(new String[emptyNames.size()]);
    }


    /**
     * Получает имена полей объекта source, и если value == null добавляет в HashSet/<String/>
     *
     * @param source - объект у которого необходимо получить поля со значением null
     * @return String[] массив с именами полей == null
     */
    private static String[] getNullProperties(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            if (beanWrapper.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }

    /**
     * Получает имена полей с сообщением ошибки при валидации
     *
     * @param bindingResult объект, генеририуемый при ошибке валидации
     * @return Map<String, String> с именем поля и сообщением ошибки валидации
     */
    public static Map<String, String> getBindingResultErrors(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return map;
    }

    /**
     * Получает объект с параметрами, при котором была ошибка валидации,
     * а так же получает имена полей с сообщением ошибки валидации
     *
     * @param object        объект валидации
     * @param bindingResult объект, генеририуемый при ошибке валидации
     * @return Map<String, Object> с именем поля и сообщением ошибки валидации
     */
    public static Map<String, Object> getBindingResultErrors(Object object, BindingResult bindingResult) {
        return getResultErrors(object, getBindingResultErrors(bindingResult));
    }

    /**
     * @param object объект при котором произошла ошибка
     * @param errors Map с именами полей и сообщением ошибки валидации
     * @return Map<String, Object> с объектом и сообщениями ошибок валидации
     */
    public static Map<String, Object> getResultErrors(Object object, Map<String, String> errors) {
        Map<String, Object> map = new HashMap<>();
        map.put(object.getClass().getSimpleName(), object);
        map.put("errors", errors);
        return map;
    }

    /**
     * Валидирует объект
     *
     * @param entity объект валидации
     * @return Map<String, String> с именем поля и сообщением ошибки валидации
     */
    public static <Entity> Map<String, String> getValidationError(Entity entity) {
        Map<String, String> map = new HashMap<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        for (ConstraintViolation cv : violations) {
            map.put(cv.getPropertyPath().toString(), cv.getMessage());
        }
        return map;
    }
}