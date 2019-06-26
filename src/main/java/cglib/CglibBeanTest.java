package cglib;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class CglibBeanTest {


    public static void main(String[] args) throws IntrospectionException {
        System.out.println(new Test2().getName());


//        Algorithm test2 = new Test2();
//        BeanInfo beanInfo = Introspector.getBeanInfo(test2.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//            System.out.println(propertyDescriptor.getName() +  "=>"+ propertyDescriptor.getReadMethod());
//
//        }


    }

}

class Test1 {
    public String name = "33";

    protected Boolean bool = false;




    public String getName() {
        System.out.println(getBool());
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBool() {
        return bool;
    }
}

 class Test2 extends Test1 {
     private String sex;
     public Boolean bool = true;

     public String getSex() {
         return sex;
     }

     public void setSex(String sex) {
         this.sex = sex;
     }

     @Override
     public Boolean getBool() {
         return bool;
     }
 }
