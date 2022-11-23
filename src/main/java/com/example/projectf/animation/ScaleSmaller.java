package com.example.projectf.animation;

import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class ScaleSmaller {
    private Scale scale;

    public void scale(Node node) {
        scale = new Scale(0.666667, 0.666667);
        node.getTransforms().add(scale);


    }
}