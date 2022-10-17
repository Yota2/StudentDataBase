package appPackage.services.implementations;

import appPackage.data.Course;
import appPackage.data.Student;
import appPackage.repositories.StudentRepository;
import appPackage.services.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    private StudentRepository studentRepository;



    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentByUsername(String username) {


        return studentRepository.getOne(username);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllStudentsByCourse(Course course) {
        return studentRepository.findByCourse(course);
    }

    public List<Student> creatStudents(Course course) {
        return studentRepository.findByCourse(course);
    }

    //TODO: create a student. If the username already exists, the student is not made. Make sure you encrypt the password
    @Override

    public Student createStudent(Student student) {
        studentRepository.save(student);
       return student;
    }


}
