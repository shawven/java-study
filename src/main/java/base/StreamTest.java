package base;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author WQB
 * @since 2019-03-19 11:24
 */
public class StreamTest {

    public static void main(String[] args) {
        Student[] students;
        students = new Student[100];
        for (int i=0;i<30;i++){
            Student student = new Student("user1",i);
            students[i] = student;
        }
        for (int i=30;i<60;i++){
            Student student = new Student("user2",i);
            students[i] = student;
        }
        for (int i=60;i<100;i++){
            Student student = new Student("user3",i);
            students[i] = student;
        }

        Integer[] arr1 = {1, 2, 3, 4};
        Integer[] arr2 = {1, 2, 3, 4};
        Integer[] arr3 = {1, 2, 3, 4};
    }
}
class Student {
    private String name;
    private Integer score;

    public Student(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
