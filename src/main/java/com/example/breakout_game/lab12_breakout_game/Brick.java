package com.example.breakout_game.lab12_breakout_game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    private static int gridRows, gridCols;
    Color color;

    public enum CrushType {
        NoCrush, HorizontalCrush, VerticalCrush;
    }

    public CrushType crushType(Point2D left, Point2D right, Point2D up, Point2D down) {
        if (isInside(left) || isInside(right)){
            return CrushType.HorizontalCrush;
        }
        if (isInside(up) || isInside(down)){
            return CrushType.VerticalCrush;
        }
        return CrushType.NoCrush;
    }

    private boolean isInside(Point2D point) {
        return (point.getX() >= x && point.getX() <= x + width) && (point.getY() >= y && point.getY() <= y + height);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, width, height);
        graphicsContext.setStroke(color.BLACK);
        graphicsContext.setLineWidth(0.5);
        graphicsContext.strokeRect(x,y,width,height);

    }

    public static void setGridRowsAndColumns(int cols, int rows) {
        Brick.gridRows = rows;
        Brick.gridCols = cols;
    }

    public static int getGridCols() {
        return gridCols;
    }

    public Brick(int x, int y, Color color) {
        this.color = color;
        width = canvasWidth / gridCols;
        height = canvasHeight / gridRows;
        this.x = x * width;
        this.y = y * height;
    }
}
