import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

public class BulgingSquares {
    
    //Constants
    private static final double XMIN   = -75;
    private static final double XMAX   =  75;
    private static final double YMIN   = -75;
    private static final double YMAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double DIAM_MAX   = 2*RADIUS_MAX;
    private static final double RADIUS_MIN = 1.5;
    private static final double DIAM_MIN   = 2*RADIUS_MIN;
    private static double BIGOFF = XMIN + RADIUS_MAX;
    private static double SMALLOFF = RADIUS_MAX - RADIUS_MIN - 0.5;
    private static int SIZE = 15;

    private static int[][] table = {
      // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
 /*0*/  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
 /*1*/  {0, 0, 0, 0, 0, 0, 6, 2, 5, 0, 0, 0, 0, 0, 0},
 /*2*/  {0, 0, 0, 0, 6, 6, 6, 2, 5, 5, 5, 0, 0, 0, 0},
 /*3*/  {0, 0, 0, 6, 6, 6, 6, 2, 5, 5, 5, 5, 0, 0, 0},
 /*4*/  {0, 0, 6, 6, 6, 6, 6, 2, 5, 5, 5, 5, 5, 0, 0},
 /*5*/  {0, 0, 6, 6, 6, 6, 6, 2, 5, 5, 5, 5, 5, 0, 0},
 /*6*/  {0, 6, 6, 6, 6, 6, 6, 2, 5, 5, 5, 5, 5, 5, 0},
 /*7*/  {0, 3, 3, 3, 3, 3, 3, 0, 4, 4, 4, 4, 4, 4, 0},
 /*8*/  {0, 5, 5, 5, 5, 5, 5, 1, 6, 6, 6, 6, 6, 6, 0},
 /*9*/  {0, 0, 5, 5, 5, 5, 5, 1, 6, 6, 6, 6, 6, 0, 0},
/*10*/  {0, 0, 5, 5, 5, 5, 5, 1, 6, 6, 6, 6, 6, 0, 0},
/*11*/  {0, 0, 0, 5, 5, 5, 5, 1, 6, 6, 0, 6, 0, 0, 0},
/*12*/  {0, 0, 0, 0, 5, 5, 5, 1, 6, 6, 6, 0, 0, 0, 0},
/*13*/  {0, 0, 0, 0, 0, 0, 5, 1, 6, 0, 0, 0, 0, 0, 0},
/*14*/  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    public BulgingSquares () {};

    public void plotSquare (double X, double Y, double radius, boolean color) {
        if (color) StdDraw.setPenColor(StdDraw.BLACK);
        else StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(X, Y, radius);
    }

    
    public void plotIlusion (double X, double Y, boolean color) {
        switch (table[(int)(Y - BIGOFF)/10][(int)(X - BIGOFF)/10]) {
            case 1:
                plotSquare(X + SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X - SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                break;
            case 2:
                plotSquare(X + SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X - SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                break;
            case 3:
                plotSquare(X + SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X + SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                break;
            case 4:
                plotSquare(X - SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X - SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                break;
            case 5:
                plotSquare(X + SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X - SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                break;
            case 6:
                plotSquare(X - SMALLOFF, Y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(X + SMALLOFF, Y - SMALLOFF, RADIUS_MIN, !color);
                break;
            default:
                break;
        }
    }
    
    public static void main (String[] args) {
        // set the scale of the coordinate system
        StdDraw.setXscale(XMIN-MARGIN, XMAX+MARGIN);
        StdDraw.setYscale(YMIN-MARGIN, YMAX+MARGIN);
        StdDraw.enableDoubleBuffering();
        
        // clear the background
        StdDraw.clear(StdDraw.WHITE);
        BulgingSquares bsq = new BulgingSquares();
        boolean color;
        for (int i = 0; i < SIZE; i++) {
            if (i%2 == 0) color = true;
            else color = false;
            for (int j = 0; j < SIZE; j++, color = !color) {
                bsq.plotSquare(j*10 + BIGOFF, i*10 + BIGOFF, RADIUS_MAX, color);
                bsq.plotIlusion(j*10 + BIGOFF, i*10 + BIGOFF, color);
            }
        }

        // copy offscreen buffer to onscreen
        StdDraw.show();
    }

} 
