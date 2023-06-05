package com.example.breakout_game.lab12_breakout_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GraphicsItem {

    public Paddle(){
        y = canvasHeight * .9;
        x = (canvasWidth-width) / 2;

        height = canvasHeight * .02;
        width = canvasWidth * .2;
    }

    @Override
    public void draw(GraphicsContext graphicContext) {
        graphicContext.setFill(Color.RED);
        graphicContext.fillRect(x,y,width,height);
    }

    public static double clamp(double val, double min, double max){
        return Math.max(min, Math.min(max, val));
    }

    public void setPosition(double x){
        this.x = clamp(x - width/2, 0, canvasWidth - width);
    }
}
