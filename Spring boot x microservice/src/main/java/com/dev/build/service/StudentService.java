package com.dev.build.service;

import com.dev.build.dto.School;
import com.dev.build.dto.StudentResponse;
import com.dev.build.model.Student;
import com.dev.build.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> createStudent(Student student){
        try{
            return new ResponseEntity<Student>(studentRepository.save(student), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> fetchStudentById(String id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            int extractedSchoolId = student.get().getSchoolId();
            String url = "http://localhost:8082/school/" + extractedSchoolId;
            System.out.println("URL: " + url);
            School school = restTemplate.getForObject(url, School.class);
            //School school = restTemplate.getForObject("http://localhost:8081/school/" + student.get().getSchoolId(), School.class);
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(id);
            studentResponse.setName(student.get().getName());
            studentResponse.setAge(student.get().getAge());
            studentResponse.setGender(student.get().getGender());
            studentResponse.setSchool(school);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Student Found",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fetchStudents(){
        List<Student> students = studentRepository.findAll();
        if(!students.isEmpty()){
            return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Students",HttpStatus.NOT_FOUND);
        }
    }



}
