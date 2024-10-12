package org.example.per;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;

public class CircleController {

    @FXML

    private Circle mycircle;
    double x;
    double y;

    public void up_function(ActionEvent e) {
        System.out.println("call up function");
        mycircle.setCenterY(y-=3);
    }

    public void down_function(ActionEvent e) {
        System.out.println("call down function");
        mycircle.setCenterY(y+=3);
    }

    public void right_function(ActionEvent e) {
        System.out.println("call right function");
        mycircle.setCenterX(x+=3);
    }

    public void left_function(ActionEvent e) {
        System.out.println("call left function");
        mycircle.setCenterX(x-=3);
    }
}
