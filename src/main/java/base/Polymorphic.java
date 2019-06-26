package base;

/**
 * @author Shoven
 * @since 2019-04-18 10:50
 */
public class Polymorphic {
    public static void main(String[] args) {
        Parent parent = new Parent("a", "b", "c");
        Parent child = new Child();
        Parent parentChild = new Child();

        System.out.println(parent);
        System.out.println(child);
        System.out.println(parentChild);
    }
}


class Parent {

    private String a ;

    protected String b;

    public String c;

    public Parent() {
    }

    public Parent(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}

class Child extends Parent {


    protected String b;
    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

}
