package com.example.demo.DesignPattern.Command;

import java.util.Stack;

public class NavigationController {
    private Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command command) {

        command.execute();
        commandHistory.push(command);
//        System.out.println(command);
//        System.out.println(commandHistory.size());
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
//            System.out.println(lastCommand);
            lastCommand.undo();
        }
    }
}

