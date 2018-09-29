package thread.MutilThread;

public class ThreadLocal {

    public static void main(String[] args) {

        new Thread(() -> {
            Person person = new Person();
            person.setLanguage("english");
            PersonUtil.set(person);
//            MutilThread.PersonUtilTest.person = person;
            System.out.println("current Thread" + 1);
            new Say().print();
        },"1").start();
        new Thread(() -> {
            Person person = new Person();
            PersonUtil.set(person);
            person.setLanguage("china");
//            MutilThread.PersonUtilTest.person = person;
            System.out.println("current Thread" + 2);
        },"1").start();


    }
}


class Person {
    private String language;

    public void say() {
        System.out.println(language);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

class Say {
    public void print() {
        PersonUtil.get().say();
//        MutilThread.PersonUtilTest.person.say();
    }
}

class PersonUtil {
    private  static  java.lang.ThreadLocal<Person> threadLocal = new java.lang.ThreadLocal<Person>();

    public  static void set(Person person) {
        threadLocal.set(person);
    }

    public static Person get() {
        return  threadLocal.get();
    }
}

class PersonUtilTest {
    public static Person person;
}