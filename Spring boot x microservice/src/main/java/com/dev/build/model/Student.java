package com.dev.build.model;


import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    private String id;
    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    private String name;
    private int age;
    private String gender;
    private Integer schoolId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
