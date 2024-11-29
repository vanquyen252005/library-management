package com.example.demo.Command;
import java.util.Stack;
public class OpenPageCommand implements Command {
    private String page;

    public OpenPageCommand(String page) {
        this.page = page;
    }

    @Override
    public void execute() {
        System.out.println("Opening page: " + page);
    }
}
