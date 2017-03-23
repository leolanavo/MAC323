/******************************************************************************
 *  Compilation:  javac BulgingSquares.java
 *  Execution:    java BulgingSquares
 *  Dependencies: StdDraw.java, java.awt.Color
 *
 *  Program draws an optical illusion from Akiyoshi Kitaoka. The center appears 
 *  to bulge outwards even though all squares are the same size. 
 *
 *  meu_prompt > java BulgingSquares
 *
 *  Exercise 14 http://introcs.cs.princeton.edu/java/15inout/
 * 
 ******************************************************************************/
import edu.princeton.cs.algs4.StdDraw;

public class BulgingSquares {
    
    // Constants
    private static final int SIZE = 15;
    private static final double RADIUS_ILUSION = 63;
    private static final double MIN   = -75;
    private static final double MAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double RADIUS_MIN = 1.5;
    private static final double BIGOFF = MIN + RADIUS_MAX;
    private static final double SMALLOFF = RADIUS_MAX - (4 * RADIUS_MIN)/3;
    
    // Recieves the center and the radius of a square, which will be plotted
    // with the color according to the boolean variable color.
    public static void plotSquare(double x, double y, double radius, boolean color) {
        if (color) StdDraw.setPenColor(StdDraw.BLACK);
        else StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(x, y, radius);
    }
    
    // Recieves the center, the radius and a boolean that represents its
    // of a big square
    public static void plotIlusionQuarters(double x, double y, boolean color) {
        double r = Math.sqrt(x*x + y*y);
        
        if (r <= RADIUS_ILUSION) {
            if (x > 0 && y > 0 || x < 0 && y < 0) {
                plotSquare(x - SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x + SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color);
            }
            else if (x < 0 && y > 0 || x > 0 && y < 0) {
                plotSquare(x + SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x - SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color); 
            }
            else if (x == 0 && y > 0) {
                plotSquare(x + SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x - SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color);
            }
            else if (x == 0 && y < 0) { 
                plotSquare(x + SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x - SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
            }
            else if (x > 0 && y == 0) {
                plotSquare(x - SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x - SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color);
            }
            else if (x < 0 && y == 0) { 
                plotSquare(x + SMALLOFF, y + SMALLOFF, RADIUS_MIN, !color);
                plotSquare(x + SMALLOFF, y - SMALLOFF, RADIUS_MIN, !color);
            }
        }
    }
    
    
    public static void main(String[] args) {
        // set the scale of the coordinate system
        StdDraw.setScale(MIN-MARGIN, MAX+MARGIN);
        StdDraw.enableDoubleBuffering();
        
        // clear the background
        StdDraw.clear(StdDraw.WHITE);
        
        // color = true  --> black
        // color = false --> white
        boolean color; 
        
        // loop for ploting the Bulging Squares ilusion
        for (int i = 0; i < SIZE; i++) {
            if (i % 2 == 0) color = true;
            else color = false;
            
            for (int j = 0; j < SIZE; j++, color = !color) {
                plotSquare(j*10 + BIGOFF, i*10 + BIGOFF, RADIUS_MAX, color);
                plotIlusionQuarters(j*10 + BIGOFF, i*10 + BIGOFF, color);
            }
        }
        
        // copy offscreen buffer to onscreen
        StdDraw.show();
    }

} 
