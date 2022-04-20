
import dao.daoutil.DaoContext;
import dao.impl.StudentDao;
import model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StudentDao studentDao = DaoContext.autowired("StudentDao", "singleton");

        List<Student> listStudent = new ArrayList();
        Student student = new Student("Nur", "Dyikanbaev", "123456789120", "nur@gmail.com", LocalDate.parse("1990-12-12"));
        Student student2 = new Student("Nurik", "Doe", "123456789121", "nurik@gmail.com", LocalDate.parse("1990-11-05"));
        Student student3 = new Student("John", "Dyikanbaev", "123456789122", "john@gmail.com", LocalDate.parse("1984-09-23"));
        Student student4 = new Student("Nazar", "Temirov", "123456789123", "nazar@gmail.com", LocalDate.parse("1992-03-25"));
        Student student5 = new Student("Nurbek", "Belekov", "123456789124", "nurbek@gmail.com", LocalDate.parse("2000-03-14"));
        Student student6 = new Student("Melis", "Sharsheev", "123456789125", "melis@gmail.com", LocalDate.parse("1998-12-13"));
        Student student7 = new Student("Aibek", "Aibekov", "123456789126", "aibek@gmail.com", LocalDate.parse("1991-01-01"));
        Student student8 = new Student("Nargiza", "Dyikanbaeva", "123456789127", "nargiza@gmail.com", LocalDate.parse("1994-02-02"));
        Student student9 = new Student("Mirbek", "Mirbekov", "123456789128", "mirbek@gmail.com", LocalDate.parse("1970-12-17"));
        Student student10 = new Student("Melis", "Sharsheev", "123456789129", "melis_kg@gmail.com", LocalDate.parse("1980-03-03"));

        listStudent.add(student);
        listStudent.add(student3);
        listStudent.add(student2);
        listStudent.add(student4);
        listStudent.add(student5);
        listStudent.add(student6);
        listStudent.add(student7);
        listStudent.add(student8);
        listStudent.add(student9);
        listStudent.add(student10);

        studentDao.save(listStudent);
        System.out.println(studentDao.findAll());

    }
}
