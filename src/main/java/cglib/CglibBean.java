package cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.util.Map;

public class CglibBean<T> {


    /**
     * 生成的对象
     */
    private T object;

    /**
     * 属性map
     */
    private BeanMap beanMap = null;

    public CglibBean() { }


    /**
     * 创建bean对象操作句柄
     *
     * @param propertyMap  属性名称以及属性类型
     * @return  CglibBean
     */
    public static CglibBean create(Map<String, Class> propertyMap) {

        CglibBean<Object> cglibBean = new CglibBean<>();
        Object object = cglibBean.generateBean(propertyMap);

        cglibBean.setObject(object);
        cglibBean.setBeanMap(BeanMap.create(object));

        return cglibBean;
    }


    /**
     * 创建bean对象操作句柄
     *
     * @param propertyMap  属性名称以及属性类型
     * @param superClass   父类class
     * @param <T>          父类类型
     * @return  CglibBean
     */
    public static <T> CglibBean<T> create(Map<String, Class> propertyMap, Class<T> superClass) {

        CglibBean<T> cglibBean = new CglibBean<>();
        T t = cglibBean.generateBean(propertyMap, superClass);

        cglibBean.setObject(t);
        cglibBean.setBeanMap(BeanMap.create(t));

        return cglibBean;
    }

    /**
     * 给bean属性赋值
     * @param property 属性名
     * @param value 值
     */
    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 通过属性名得到属性值
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public String getStringValue(String property) {
        return String.valueOf(beanMap.get(property));
    }

    public <T> T getValue(String property, Class<T> clazz) {
        return clazz.cast(beanMap.get(property));
    }



    public T getObject() {
        return  object;
    }


    private void setObject(T object) {
        this.object = object;
    }


    private void setBeanMap(BeanMap beanMap) {
        this.beanMap = beanMap;
    }

    /**
     * 创建Object
     *
     * @param  propertyMap  属性类型
     * @return object      生成的对象
     */
    private Object generateBean(Map<String, Class> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        for (Map.Entry<String, Class> entry : propertyMap.entrySet()) {
            generator.addProperty(entry.getKey(), entry.getValue());
        }

        return generator.create();
    }


    /**
     * 创建Object
     *
     * @param propertyMap  属性类型
     * @param superClass  父类class
     * @param <T>         父类类型
     * @return T         生成的对象
     */
    private <T> T generateBean(Map<String, Class> propertyMap, Class<T> superClass) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superClass);
        for (Map.Entry<String, Class> entry : propertyMap.entrySet()) {
            generator.addProperty(entry.getKey(), entry.getValue());
        }

        return superClass.cast(generator.create());

    }
}