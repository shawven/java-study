package cglib;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Cglib测试类
 * @author cuiran
 * @version 1.0
 */


public class CglibTest {


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ClassNotFoundException {

        // 设置类成员属性
        HashMap<String, Class> propertyMap = new HashMap();

//        propertyMap.put("id", Integer.class);
//
//        propertyMap.put("name", String.class);
//
//        propertyMap.put("address", String.class);

        // 生成动态 Bean
        CglibBean<SuperClass> bean = CglibBean.create(propertyMap, SuperClass.class);

        // 给 Bean 设置值
        bean.setValue("id", 123);

        bean.setValue("name", "454");

//        bean.setValue("address", "789");

        // 从 Bean 中获取值，当然了获得值的类型是 Object

        System.out.println("  >> id      = " + bean.getValue("id", Integer.class));

        System.out.println("  >> name    = " + bean.getValue("name", String.class));

        System.out.println("  >> address = " + bean.getValue("address",  String.class));

        // 获得bean的实体
        SuperClass object = bean.getObject();


        // 通过反射查看所有方法名

        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }


        try {

            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals("class")) continue;
                Method readMethod = pd.getReadMethod();
                System.out.println("propertyType:" + pd.getPropertyType());
                //System.out.println("class:" + readMethod.getDeclaringClass());
                System.out.println(pd.getName() + " : " + readMethod.invoke(object).toString());
            }

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
class SuperClass {
    private Integer id;
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}