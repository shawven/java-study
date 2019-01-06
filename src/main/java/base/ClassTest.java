import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassTest {


    public static void main(String[] args) {
        regEx();
    }


    public static void regEx() {
        String regex="(([a-zA-Z]+)([0-9]+))";

        Pattern pattern=Pattern.compile(regex);

        String input="age45 salary500000 50000 title";

        Matcher matcher=pattern.matcher(input);

        StringBuffer sb=new StringBuffer();

        while(matcher.find()){
            String replacement=matcher.group(1).toUpperCase();
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        System.out.println("替换完的字串为"+sb.toString());
    }

    public static void test1() {
        Student x = new Student(1, "x");
        Student z = new Student(3, "z");
        Student y = new Student(2, "y");
        Student o = new Student(3, "o");
        ArrayList<Student> students = new ArrayList<>();
        students.add(x);
        students.add(z);
        students.add(y);
        students.add(o);
        System.out.println(students);
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                System.out.println("o1:" +o1);
                System.out.println("o2:" +o2);
                if (o1.getId() == o2.getId()) {
                    return 0;
                } else if (o1.getId() > o2.getId()) {
                    return 1;
                }
                return -1;
            }
        });
        System.out.println(students);
    }
}

class Student {
    int id;
    String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}