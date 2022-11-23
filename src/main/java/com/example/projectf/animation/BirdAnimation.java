package com.example.projectf.animation;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class BirdAnimation {

    public void birdAnimation(Node node) {
        node.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            ScaleBigger scaleBigger = new ScaleBigger();
            scaleBigger.scale(node);
        });
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
            ScaleSmaller scaleSmaller = new ScaleSmaller();
            scaleSmaller.scale(node);
        });


    }


}
