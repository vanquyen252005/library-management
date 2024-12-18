package com.example.demo.admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.List;

public class HomeController extends MenuController {
    @FXML
    private PieChart pieChart;

    //initialize pie chart of request
    @FXML
    public void initialize() {
        super.initialize();
        List<Request> requests = Request.getRequest();
        double pendingCount = 0;
        double approvedCount = 0;
        double rejecteCount = 0;
        for (Request r:requests) {
            if (r.getStatus().equals("pendingCount")) {
                pendingCount++;
            } else if (r.getStatus().equals("approvedCount")) {
                approvedCount++;
            } else {
                rejecteCount++;
            }
        }
        double sum = pendingCount + approvedCount + rejecteCount;

        System.out.println(sum);
        System.out.println("pen"+pendingCount);
        System.out.println("app"+approvedCount);

        double[] targetValues = {
                rejecteCount / sum * 100,
                pendingCount / sum * 100,
                approvedCount / sum * 100
        };

        String[] labels = { "Approved", "Rejected", "Pending" };

// Tạo dữ liệu ban đầu
        pieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data(labels[1], 0),
                new PieChart.Data(labels[2], 0),
                new PieChart.Data(labels[0], 0)
        ));

        pieChart.setTitle("Your request pie chart");

        double totalValue = 100.0; // Tổng giá trị: 25 + 30 + 20 + 25 = 100
        final double[] currentProgress = {0}; // Tỷ lệ quét hiện tại (bắt đầu từ 0)

        // Gắn Tooltip để hiển thị phần trăm trên mỗi phần
        for (PieChart.Data data : pieChart.getData()) {
            Tooltip toolTip = new Tooltip();
            Tooltip.install(data.getNode(), toolTip); // Gắn Tooltip vào phần
            data.pieValueProperty().addListener((observable, oldValue, newValue) -> {
                double percentagePart = (newValue.doubleValue() / totalValue) * 100;
                toolTip.setText(String.format("%s: %.1f%%", data.getName(), percentagePart));
            });
        }

        // Tạo hiệu ứng quét
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(20), event -> {
            // Tăng dần tỷ lệ quét
            currentProgress[0] += 1; // Tăng 1% mỗi lần cập nhật

            double accumulated = 0; // Tổng giá trị đã được hiển thị
            for (int i = 0; i < pieChart.getData().size(); i++) {
                PieChart.Data slice = pieChart.getData().get(i);
                double target = targetValues[i];

                if (accumulated + target <= currentProgress[0]) {
                    // Phần này đã quét đầy đủ
                    slice.setPieValue(target);
                } else if (accumulated < currentProgress[0]) {
                    // Phần này đang được quét
                    slice.setPieValue(currentProgress[0] - accumulated);
                } else {
                    // Phần này chưa được quét
                    slice.setPieValue(0);
                }
                accumulated += target;
            }

            // Dừng khi tỷ lệ quét đạt 100%
            if (currentProgress[0] >= totalValue) {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }
}
