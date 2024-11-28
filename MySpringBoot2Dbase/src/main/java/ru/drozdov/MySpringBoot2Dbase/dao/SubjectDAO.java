package ru.drozdov.MySpringBoot2Dbase.dao;

import ru.drozdov.MySpringBoot2Dbase.entity.Subject;

import java.util.List;

public interface SubjectDAO {
    List<Subject> getAllSubjects();
    Subject saveSubject(Subject subject);
    Subject getSubject(int id);
    void deleteSubject(int id);
}
