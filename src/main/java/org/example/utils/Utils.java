package org.example.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Метод копирует не null поля sourse в target при помощи Beanutils
     *
     * @param sourse объект, откуда необходимо скопировать свойства
     * @param target объект, куда необхоимо засетить поля
     */
    public static void copyNotNullProperties(Object sourse, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(sourse, target, getNullProperties(sourse));
    }

    /**
     * Получает имена полей объекта и если value == null добавляет в Haset/<String/>
     *
     * @param source объект - источник полей для нового объекта
     * @return String[] массив с именами полей == null
     */
    private static String[] getNullProperties(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            if (beanWrapper.getPropertyValue(pd.getName()) == null ||
                    beanWrapper.getPropertyValue(pd.getName()).toString().equalsIgnoreCase("false")) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }

    /**
     * Обработчик ошибок при валидации объекта
     *
     * @param bindingResult объект, генеририуемы при ошибки валидации
     * @return мапу с именем поля и сообщением валидации
     */
    public static Map<String, String> getBindingResultErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField(),
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    public static Map<String, String> getExceptionErors(Exception exception) {
        Map<String, String> errors = new HashMap<>();
        if (exception instanceof IOException){
            errors.put("errors", "Не удалось загрузить файл: " + exception.getMessage());
        }else{
            String message = exception.getMessage();
            String fieldName = message.substring(message.indexOf("(") + 1, message.indexOf(")"));
//		for(String fieldName : message.split(",")){
            errors.put(fieldName.toLowerCase(), "Такая запись уже есть в бд");
//		}
        }
        return errors;
    }
}