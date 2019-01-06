package base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@interface MyAnntation {
    Class<?>[] value() default {};
}


class Test {

    @MyAnntation(Apple.class)
    public Object fruits;

    public Object drink;

    public Object getObject() {
        return fruits;
    }

    public void setObject(Object fruits) {
        this.fruits = fruits;
    }
}

class Factory {
    public static <T> T getInstance() {
        Field[] declaredFields = Test.class.getDeclaredFields();



        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(MyAnntation.class)) {
                MyAnntation annotation = field.getAnnotation(MyAnntation.class);
                annotation.annotationType();
            }


        }




        return null;
    }


}


public class Annotation {

    public static void main(String[] args) {

        Test test = new Test();
        Object instance = Factory.getInstance();
    }
}

class Apple {
    public void  eat() {
        System.out.println("我在吃苹果");
    }
}
