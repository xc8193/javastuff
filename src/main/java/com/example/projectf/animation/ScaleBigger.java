package com.example.projectf.animation;

import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class ScaleBigger {
    private Scale scale;

    public void scale(Node node) {
        scale = new Scale(1.5, 1.5);
        node.getTransforms().add(scale);


    }
}
