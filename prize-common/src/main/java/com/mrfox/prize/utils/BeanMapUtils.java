package com.mrfox.prize.utils;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * map和实体互相转换
 *
 * @author : MrFox
 * @version : v1.0
 * @date : 2019-09-18 16:39
 **/
@Slf4j
public class BeanMapUtils {

    /**
     * 将对象属性转化为map结合-包含父类属性
     */
    public static <T> Map<String, Object> beanToMapByReflux(T bean) {
        Map<String, Object> map = null;
        if (bean != null) {
            Field[] fields = getAllFields(bean);
            map = new HashMap<>(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(bean) != null) {
                        map.put(field.getName(), field.get(bean));
                    }
                } catch (IllegalAccessException e) {
                    log.info(Throwables.getStackTraceAsString(e));
                }
            }
        }
        return map;
    }


    /**
     * 将对象属性转化为map结合-不包含父类的属性,可忽略属性
     */
    public static <T> Map<String, Object> beanToMapByRefluxNoParentClass(T bean, String... ignores) {
        List<String> strings = new ArrayList<>();
        strings.add("serialVersionUID");
        if (Objects.nonNull(ignores) && ignores.length > 0) {
            List<String> strings1 = Arrays.asList(ignores);
            strings.addAll(strings1);
        }
        Map<String, Object> map = null;
        if (bean != null) {
            Field[] fields = bean.getClass().getDeclaredFields();
            map = new HashMap<>(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(bean) != null && !strings.contains(field.getName())) {
                        map.put(field.getName(), field.get(bean));
                    }
                } catch (IllegalAccessException e) {
                    log.info(Throwables.getStackTraceAsString(e));
                }
            }
        }
        return map;
    }

    /**
     * 将对象属性转化为map结合-不包含父类的属性,可忽略属性
     */
    public static <T> Map<String, Object> beanToMapByRefluxNoParentClassAndIgnoreList(T bean, List ignores) {
        if(Objects.isNull(ignores)){
            ignores = new ArrayList(1);
        }
        ignores.add("serialVersionUID");
        Map<String, Object> map = null;
        if (bean != null) {
            Field[] fields = bean.getClass().getDeclaredFields();
            map = new HashMap<>(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(bean) != null && !ignores.contains(field.getName())) {
                        map.put(field.getName(), field.get(bean));
                    }
                } catch (IllegalAccessException e) {
                    log.info(Throwables.getStackTraceAsString(e));
                }
            }
        }
        return map;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBeanByReflux(Map<String, Object> map, Class<T> clazz) {
        if (map.isEmpty()) {
            return null;
        }
        T bean = null;
        try {
            bean = clazz.newInstance();
            Field[] fields = getAllFields(bean);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (null != map.get(field.getName())) {
                        field.set(bean, field.getType().cast(map.get(field.getName())
                        ));
                    }
                } catch (IllegalAccessException e) {
                    log.info(Throwables.getStackTraceAsString(e));
                }
            }
        } catch (Exception e) {
            return bean;
        }
        return bean;
    }


    /******
     * 根据Field转类型赋值
     * @param
     * @return
     *****/
    public static <T> T setValueByField(T t, Field field, Object object, boolean resolveDateFlag) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type == Long.class) {
            field.set(t, Long.parseLong(object.toString()));
        } else if (type == Integer.class) {
            field.set(t, Integer.parseInt(object.toString()));
        } else if (type == Date.class) {
            //如果实体类的属性包含time的自动转换为Date
            if (field.getName().contains("Time")&& resolveDateFlag) {
                field.set(t, new Date(Long.parseLong(object.toString())));
            } else {
                field.set(t, object);
            }
        }else if (type == LocalDateTime.class) {
            //如果实体类的属性包含time的自动转换为Date
            if (field.getName().contains("Time")&& resolveDateFlag) {
                field.set(t, LocalDateTimeUtils.defaultConvertStringToLDT(object.toString()));
            } else {
                field.set(t, object);
            }
        } else if (type == String.class) {
            //field.set(t, object.toString().substring(1, object.toString().length() - 1));
            field.set(t, object.toString());
        }else {
            field.set(t, object);
        }
        return t;
    }

    /**
     * 将map集合中的数据强制转化为指定对象的同名属性(依赖class的属性)中,并且带有"Date"属性的将由Long转为Date
     *
     * @param
     */
    public static <T> T mapToBeanByForceReflux(Map<String, Object> map, Class<T> clazz, boolean resolveDateFlag,List ignores) {
        if(Objects.isNull(ignores)){
            ignores = new ArrayList(1);
        }
        ignores.add("serialVersionUID");
        if (map.isEmpty()) {
            return null;
        }
        T bean = null;
        try {
            bean = clazz.newInstance();
            Field[] fields = getAllFields(bean);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (null != map.get(field.getName()) && !ignores.contains(field.getName()) ) {
                        setValueByField(bean, field, map.get(field.getName()), resolveDateFlag);
                        //field.set(bean, field.getType().cast(map.get(field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    log.info(Throwables.getStackTraceAsString(e));
                }
            }
        } catch (Exception e) {
            return bean;
        }
        return bean;
    }


    /**
     * 获取所有属性和值-包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


}