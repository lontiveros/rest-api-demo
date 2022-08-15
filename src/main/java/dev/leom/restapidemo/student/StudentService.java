package dev.leom.restapidemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        Optional<Student> studentExists = studentRepository.findStudentByEmail(student.getEmail());

        if (studentExists.isPresent()) {
            throw new EntityExistsException("Student already exists");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean studentExists = studentRepository.existsById(id);

        if (! studentExists) {
            throw new IllegalStateException("Student with Id " + id + " doesn't exists.");
        }

        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with Id " + id + " doesn't exists."));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> emailExists = studentRepository.findStudentByEmail(email);

            if (emailExists.isPresent()) {
                throw new EntityExistsException("Student already exists");
            }

            student.setEmail(email);
        }
    }
}
