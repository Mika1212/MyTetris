import java.awt.*;

public class NewShape {
    private Point[][] shapeCoordinates;
    private int currentRotation = 0;
    private final Color currentColor;
    private Point shift = new Point(5, 0);

    public void setShift(int x, int y) {
        if (x < 11 && x >= 0 && y >= 0 && y < 20) {
            shift.x = x;
            shift.y = y;
        }
    }

    public Point getShift() {
        return shift;
    }

    public void setCurrentRotation(int k) {
        if (k >= 0 && k < 4)
            currentRotation = k;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public Point[][] getShapeCoordinates() {
        return shapeCoordinates;
    }

    public void setShapeCoordinates(Point[][] value) {
        shapeCoordinates = value;
    }

    public NewShape(int number) {
        this.shapeCoordinates = Shapes.shapes[number];
        this.currentColor = Shapes.shapesColors[number];
    }

    public NewShape (NewShape currentShape) {
        this.currentRotation = currentShape.currentRotation;
        this.shapeCoordinates = currentShape.shapeCoordinates.clone();
        this.currentColor = currentShape.currentColor;
        this.shift = new Point(currentShape.getShift().x, currentShape.getShift().y);
    }

    public int futurePosition(Color[][] matrix) {
        int minY = 100;
        for (Point point: shapeCoordinates[currentRotation]) {
            int y = 0;
            for (int j = point.y + shift.y + 1; j < 20; j++) {
                if (matrix[point.x + shift.x][j] == Color.BLACK) {
                    y++;
                } else break;
            }
            if (y < minY) minY = y;
        }
        return minY;
    }

    public void upShapeRotate() {
        if (currentRotation + 1 > 3) currentRotation = 0;
        else currentRotation++;
    }

    public void downShapeRotate() {
        if (currentRotation - 1 < 0) currentRotation = 3;
        else currentRotation--;
    }

    public void shapeMove(int k) {
        setShift(shift.x + k, shift.y);
    }
}