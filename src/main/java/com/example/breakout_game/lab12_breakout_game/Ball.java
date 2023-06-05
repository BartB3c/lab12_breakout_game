package com.example.breakout_game.lab12_breakout_game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {
    private Point2D moveVector = new Point2D(1,-1).normalize();
    private double velocity = 400;
    double lastX;
    double lastY;

    public Ball(){
        x = -100;
        y = -100;
        width = height = canvasHeight * 0.015;
    }
    @Override
    public void draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillOval(x,y,width, height);
    }

    public void setPosition(Point2D point){
        this.x = point.getX() - width/2;
        this.y = point.getY() - height/2;
    }

    public void updatePosition(double diff){
        lastX=x;
        lastY=y;
        x += moveVector.getX() * velocity * diff;
        y += moveVector.getY() * velocity * diff;
    }

    public Point2D[] borderPoints(){
        Point2D[] points = new Point2D[4];
        points[0] = new Point2D(x,y-height/2);
        points[1] = new Point2D(x+width,y-height/2);
        points[2] = new Point2D(x+width/2,y);
        points[3] = new Point2D(x+width/2,y-height);
        return points;


    }
    void bounceHorizontally(){
        moveVector = new Point2D(-moveVector.getX(), moveVector.getY()).normalize();
    }
    void bounceVertically(){
        moveVector = new Point2D(moveVector.getX(), -moveVector.getY()).normalize();
    }
//    public void bounceFromPaddle(double num){
//        moveVector = new Point2D(num, -moveVector.getY()).normalize();
//    }
}
