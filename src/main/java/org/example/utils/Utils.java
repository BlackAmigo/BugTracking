package org.example.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utils {

    /**
     * Метод копирует, не null поля source объекта в target объект, при помощи BeanUtils
     *
     * @param source - объект, откуда необходимо скопировать свойства
     * @param target - объект, куда необходимо скопировать свойства
     */
    public static void copyNotNullProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullProperties(source));
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
     * @param object объект валидации
     * @param bindingResult объект, генеририуемый при ошибке валидации
     * @return Map<String, Object> с именем поля и сообщением ошибки валидации
     */
    public static Map<String, Object> getBindingResultErrors(Object object, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        map.put(object.getClass().getSimpleName(), object);
        map.put("errors", getBindingResultErrors(bindingResult));
        return map;
    }
}