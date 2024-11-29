package com.example.demo.Command;
import java.util.Stack;
public class HistoryManager {
    private static Stack<String> historyStack = new Stack<>();

    // Lưu màn hình hiện tại
    public static void push(String sceneName) {
        historyStack.push(sceneName);
    }

    // Lấy màn hình trước đó
    public static String pop() {
        return historyStack.isEmpty() ? null : historyStack.pop();
    }

    // Kiểm tra nếu còn màn hình trước đó
    public static boolean hasHistory() {
        return !historyStack.isEmpty();
    }
}
