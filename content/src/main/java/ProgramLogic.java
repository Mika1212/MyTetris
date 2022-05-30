import java.awt.*;
import java.util.*;

public class ProgramLogic {

    public MySolverGreedy mySolverGreedy;

    final private boolean gameProcess = true;
    private boolean play = false;
    private boolean gameOver = false;
    private boolean pause = false;
    private boolean solve = false;
    private boolean checked = false;
    private ProgramInterface programInterface;

    final public int iBorder = 12;
    final public int jBorder = 21;
    final public int rotationBorder = 4;
    final public int numberOfDifferentShapes = 7;
    final public Color[][] matrix = new Color[iBorder][jBorder];
    public ArrayDeque<Integer> allShapes = new ArrayDeque<>();
    public int clearedLines;

    public NewShape currentShape;
    private MyKeyboardListener myKeyboardListener;

    private boolean mySolverGreedyFlag = false;
    private boolean mySolverGreedyModifiedFlag = false;
    public int amountOfShapes = 1000;
    public ProgramInterface getProgramInterface() {
        return programInterface;
    }

    public NewShape getCurrentShape() {
        return currentShape;
    }

    public int getIBorder() {
        return iBorder;
    }

    public int getJBorder() {
        return jBorder;
    }

    public int getRotationBorder() {
        return rotationBorder;
    }

    public void setMySolverGreedyModifiedFlag(final boolean value) {
        mySolverGreedyModifiedFlag = value;
    }

    public boolean getMySolverGreedyModifiedFlag() {
        return mySolverGreedyModifiedFlag;
    }

    public void startSolveGreedyModified() {
        if (!getMySolverGreedyModifiedFlag()) {
            startSolve();
            setMySolverGreedyFlag(false);
            setMySolverGreedyModifiedFlag(true);
        } else {
            stopSolve();
        }
    }

    public boolean getMySolverGreedyFlag() {
        return mySolverGreedyFlag;
    }

    public boolean getChecked() {
        return checked;
    }

    public boolean getSolve() {
        return solve;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean getPlay() {
        return play;
    }

    public boolean getPause() {
        return pause;
    }



    public void setChecked(final boolean value) {
        checked = value;
    }

    public void setSolve(final boolean value) {
        this.solve = value;
    }

    public void setMyKeyboardListener(final MyKeyboardListener myKeyboardListener) {
        this.myKeyboardListener = myKeyboardListener;
    }

    //Method to assign jFrame
    public void setProgramInterface(final ProgramInterface programInterface) {
        this.programInterface = programInterface;
    }

    public void setPause(final boolean value) {
        myKeyboardListener.block = value;
        pause = value;
    }

    public void setPlay(final boolean value) {
        play = value;
    }

    public void setGameOver(final boolean value) {
        gameOver = value;
        if (value) myKeyboardListener.block = true;
    }

    public void startSolve() {
        myKeyboardListener.block = true;
        mySolverGreedy = new MySolverGreedy(this);
        setChecked(false);
        setSolve(true);
        setPlay(false);
    }

    public void stopSolve() {
        myKeyboardListener.block = false;
        setChecked(true);
        setSolve(false);
        setMySolverGreedyFlag(false);
        setMySolverGreedyModifiedFlag(false);
        setPlay(true);
    }

    public void startSolveGreedy() {
        if (!getMySolverGreedyFlag()) {
            startSolve();
            setMySolverGreedyFlag(true);
            setMySolverGreedyModifiedFlag(false);

        } else {
            stopSolve();
        }
    }

    public void setMySolverGreedyFlag(final boolean value) {
        mySolverGreedyFlag = value;
    }

    public double checkScore(final Color[][] matrix) {
        return height(matrix) + lines(matrix) + bumpiness(matrix);
    }

    public double height(final Color[][] matrix) {
        int holes = 0;
        int height = 0;
        for (int i = 1; i < iBorder - 1; i++) {
            int height1 = 0;
            for (int j = 0; j < jBorder - 1; j++) {
                if (matrix[i][j] != programInterface.emptyColor) {
                    height1 = 20 - j;
                    break;
                }
            }
            for (int j = jBorder - 2; j > jBorder - 2 - height1; j--) {
                if (matrix[i][j] == programInterface.emptyColor) holes++;
            }
            height += height1;
        }
        return -0.510066 * height + -0.35663 * holes;
    }

    public double lines(final Color[][] matrix) {
        int lines = 0;
        for (int j = jBorder - 2; j > 0 ; j--) {
            boolean flag = true;
            for (int i = 1; i < iBorder - 1; i++) {
                if (matrix[i][j] == programInterface.emptyColor) {
                    flag = false;
                    break;
                }
            }
            if (flag) lines++;
            if (lines == 4) break;
        }
        return 0.760666 * lines;
    }

    public double bumpiness(final Color[][] matrix) {
        int bumpiness = 0;
        int height1 = 0;
        for (int j = 0; j < jBorder; j++) {
            if (matrix[1][j] != programInterface.emptyColor) {
                height1 = j;
                break;
            } else height1 = jBorder - 1;
        }
        for (int i = 2; i < iBorder - 1; i++) {
            int height2 = 0;
            for (int j = 0; j < jBorder; j++) {
                if (matrix[i][j] != programInterface.emptyColor) {
                    height2 = j;
                    break;
                } else height2 = jBorder - 1;
            }
            bumpiness += Math.abs(height1 - height2);

            height1 = height2;
        }
        return -0.184483 * bumpiness;
    }

    public int clearFullRows(final Color[][] matrix, boolean toAdd) {
        int amount = 0;
        for (int k = 0; k < rotationBorder; k++) {
            for (int j = jBorder - 2; j > 0; j--) {
                boolean flag = true;
                for (int i = 1; i < iBorder - 1; i++) {
                    if (matrix[i][j] == programInterface.emptyColor) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (toAdd) {
                        amount++;
                        clearedLines++;
                    }
                    for (int i = j; i > 0; i--) {
                        for (int l = 1; l < iBorder - 1; l++) {
                            matrix[l][i] = matrix[l][i - 1];
                        }
                    }
                    for (int i = 1; i < iBorder - 1; i++) {
                        matrix[i][0] = programInterface.emptyColor;
                    }
                }
            }
        }
        return amount;
    }

    public void generateQueue() {
        allShapes = new ArrayDeque<>();
        int a = (int) (Math.random() * 1000);
        Random number = new Random(123);
        for (int i = 0; i < amountOfShapes; i++) {
            int nextShape = number.nextInt(7);
            allShapes.add(nextShape);
        }
    }

    public boolean isGameOver(final Color[][] matrix, NewShape newShape) {
        for (int k = 0; k < rotationBorder; k++) {
            if (isBump(0, 0, matrix, newShape)) {
                return true;
            }
        }
        return false;
    }

    public NewShape newShape(final ArrayDeque<Integer> allShapesHelp) {
        NewShape newShape = new NewShape(allShapesHelp.poll());
        newShape.setCurrentRotation((int) (Math.random() * rotationBorder));
        int numberOfNewShape = (int) (Math.random() * numberOfDifferentShapes);
        allShapesHelp.add(numberOfNewShape);
        return newShape;
    }

    public void oneTick(final Color[][] matrix, NewShape newShape) {
        if (isBump(0, 1, matrix, newShape)) {
            stopShape(matrix, newShape);
            return;
        }
        newShape.setShift(newShape.getShift().x, newShape.getShift().y + 1);
        programInterface.repaint();
    }

    public void stopShape(final Color[][] matrix, NewShape currentShape) {
        for (Point point: currentShape.getShapeCoordinates()[currentShape.getCurrentRotation()]) {
            matrix[point.x + currentShape.getShift().x][point.y + currentShape.getShift().y] = currentShape.getCurrentColor();
        }

        this.currentShape = newShape(allShapes);

        if (isGameOver(matrix, this.currentShape)) {
            setGameOver(true);
        } else {
            clearFullRows(matrix, true);
        }
        setChecked(false);
    }

    public boolean isBump(int x, int y, Color[][] matrix, NewShape newShape) {
        for (Point point: newShape.getShapeCoordinates()[newShape.getCurrentRotation()]) {
            if (matrix[point.x + newShape.getShift().x + x][point.y + newShape.getShift().y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    public void shapeMoveHorizontal(int k) {
        if (!(isBump(k, 0, matrix, currentShape))) currentShape.shapeMove(k);
    }

    public void shapeRotate(int k) {
        switch (k) {
            case (1) -> {
                currentShape.upShapeRotate();
                if (isBump(0, 0, matrix, currentShape))
                    currentShape.downShapeRotate();
            }
            case (-1) -> {
                currentShape.downShapeRotate();
                if (isBump(0, 0, matrix, currentShape))
                    currentShape.upShapeRotate();
            }
        }
    }

    public void setUpNewGame() {
        generateQueue();
        stopSolve();
        setPause(false);
        setGameOver(false);
        clearedLines = 0;
        for (int i = 0; i < iBorder; i++) {
            for (int j = 0; j < jBorder; j++) {
                if (i == 0 || i == iBorder - 1 || j == jBorder - 1) matrix[i][j] = programInterface.wallsColor;
                else matrix[i][j] = programInterface.emptyColor;
            }
        }
        this.currentShape = newShape(allShapes);
        programInterface.repaint();
    }

    /*Package private*/ void logicStart() {
        myKeyboardListener = new MyKeyboardListener(this);
        programInterface.jFrame.addKeyListener(myKeyboardListener);
        setUpNewGame();
        launchApplication();
    }

    //Run of the process
    private void launchApplication() {
        Runnable r = ()->{
            try {
                while (gameProcess) {
                    programInterface.repaint();

                    if (!getGameOver() && !getPause()) {
                        if (getPlay()) {
                            oneTick(matrix, currentShape);
                            //new Timer().scheduleAtFixedRate();
                            Thread.sleep(1000);

                        } else if (getSolve()) {
                            if (!getChecked()) {
                                if (getMySolverGreedyFlag()) {
                                    mySolverGreedy.solverGreedy(matrix, currentShape);
                                } else if (getMySolverGreedyModifiedFlag()) {
                                    mySolverGreedy.solverGreedyModified(matrix, currentShape, allShapes);
                                }
                            }

                            oneTick(matrix, currentShape);
                            Thread.sleep(10);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread myThread = new Thread(r,"MyThread");
        myThread.start();
    }
}