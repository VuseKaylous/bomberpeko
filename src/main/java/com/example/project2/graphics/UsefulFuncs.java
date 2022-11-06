package com.example.project2.graphics;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class UsefulFuncs {
    public static void paintRect(Rectangle rect, GraphicsContext graphicsContext, Color color) {
        if (rect == null) {
            return;
        }
        graphicsContext.setStroke(Paint.valueOf(String.valueOf(color)));
        graphicsContext.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public static void renderText(GraphicsContext graphicsContext, String str, Color color, int xi, int yi, int fontSize) {
        renderText(graphicsContext, str, color, xi, yi, fontSize, false);
    }

    public static void renderText(GraphicsContext graphicsContext, String str, Color color, int xi, int yi, int fontSize, boolean italic) {
        if (!italic) {
            graphicsContext.setFont(new Font("Comic Sans MS", fontSize));
        } else {
            graphicsContext.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, fontSize));
        }
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFill(color);
        graphicsContext.fillText(str, xi, yi);
    }
}
