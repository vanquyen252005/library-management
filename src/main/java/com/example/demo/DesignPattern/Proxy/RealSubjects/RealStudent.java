package com.example.demo.DesignPattern.Proxy.RealSubjects;

import com.example.demo.DesignPattern.Proxy.Interface.Student;

public class RealStudent implements Student {
    private String studentName;

    public RealStudent(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public void accessBook() {
        System.out.println(studentName + " is accessing the book.");
        // Giả lập quyền truy cập của sinh viên
    }
}
