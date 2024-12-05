package com.example.demo.admin;

import javafx.application.Platform;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    protected static ExecutorService getExecutorService() {
        return executorService;
    }
    public abstract static class FxCallback<T> {
        protected abstract void onSuccess(T result) throws SQLException;
        public void fxSuccess(T result) throws SQLException {
            Platform.runLater(() -> {
                try {
                    onSuccess(result);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        protected abstract void onFailure(Exception e);
        public void fxFailure(Exception e) {
            Platform.runLater(() -> onFailure(e));
        }
    }
}
