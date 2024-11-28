package ru.drozdov.MySpringBoot2Dbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.drozdov.MySpringBoot2Dbase.dao.StudentDAO;
import ru.drozdov.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public boolean updateStudent(Student student) {
        if (studentDAO.getStudent(student.getId()) != null) {
            studentDAO.saveStudent(student);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteStudent(int id) {
        if (studentDAO.getStudent(id) != null) {
            studentDAO.deleteStudent(id);
            return true;
        }
        return false;
    }
}
