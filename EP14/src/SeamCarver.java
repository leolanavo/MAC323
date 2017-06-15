import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import java.lang.Math;

public class SeamCarver {
        
    private Picture pic;

    private class Node {
        public double dist;
        public int minFather;
        public final double energy;

        public Node (int x, int y) {
            dist = Double.POSITIVE_INFINITY;
            energy = energy(x, y);
            minFather = -1;
        }
    }

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new java.lang.NullPointerException();
        
        this.pic = picture;
    }
   
    // current picture
    public Picture picture() {
        return pic;
    }   
    
    // width of current picture
    public int width() {
        return pic.width();
    }
   
    // height of current picture
    public int height() {
        return pic.height();
    }
   
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new java.lang.IndexOutOfBoundsException();

        int prevX = (x - 1)%width();
        int nextX = (x + 1)%width();
        int prevY = (x - 1)%height();
        int nextY = (x + 1)%height();

        int red, blue, green;
        red = pic.get(prevX, y).getRed() - pic.get(nextX, y).getRed();
        blue = pic.get(prevX, y).getBlue() - pic.get(nextX, y).getBlue();
        green = pic.get(prevX, y).getGreen() - pic.get(nextX, y).getGreen();
       
        int deltaX = (red*red) + (blue*blue) + (green*green);
        
        red = pic.get(x, prevY).getRed() - pic.get(x, nextY).getRed();
        blue = pic.get(x, prevY).getBlue() - pic.get(x, nextY).getBlue();
        green = pic.get(x, prevY).getGreen() - pic.get(x, nextY).getGreen();
        
        int deltaY = (red*red) + (blue*blue) + (green*green);
        
        return Math.sqrt(deltaX + deltaY);
    }

    // sequence of indices for horizontal seam (vector of y's)
    public int[] findHorizontalSeam() {
        int[] path = new int[height()];

        Node[][] matrix = createMatrix(false);
        
        for (int i = 1; i < width(); i++)
            for (int j = 0; j < height() && i+1 < width(); j++)
                    for (int k = -1; k < 2; k++)
                        if (j+k < height() && j+k >= 0 &&
                            matrix[j][i].dist + matrix[j+k][i+1].energy < matrix[j+k][i+1].dist) {
                            
                            matrix[j+k][i+1].dist = matrix[j][i].dist + matrix[j+k][i+1].energy;
                            matrix[j+k][i+1].minFather = j;
                        }
        
        double min = Double.POSITIVE_INFINITY;
        int y = 0;
        
        for (int i = width() - 1, j = 0; j < height(); j++) {
            if (matrix[j][i].dist < min) {
                min = matrix[j][i].dist;
                y = i;
            }
        }

        for (int i = width() - 1; i >= 0; i--, y = matrix[i][y].minFather)
            path[i] = y;
        
        return path;

    }
   
    // sequence of indices for vertical seam (vector of x's)
    public int[] findVerticalSeam() {
        int[] path = new int[height()];

        Node[][] matrix = createMatrix(true);

        for (int i = 1; i < height(); i++)
            for (int j = 0; j < width() && i+1 < height(); j++)
                    for (int k = -1; k < 2; k++)
                        if (j+k < width() && j+k >= 0 &&
                            matrix[i][j].dist + matrix[i+1][j+k].energy < matrix[i+1][j+k].dist) {
                            
                            matrix[i+1][j+k].dist = matrix[i][j].dist + matrix[i+1][j+k].energy;
                            matrix[i+1][j+k].minFather = j;
                        }
        
        double min = Double.POSITIVE_INFINITY;
        int x = 0;
        
        for (int i = 0, j = height() - 1; i < width(); i++) {
            if (matrix[i][j].dist < min) {
                min = matrix[i][j].dist;
                x = i;
            }
        }

        for (int i = height() - 1; i >= 0; i--, x = matrix[i][x].minFather)
            path[i] = x;
        
        return path;
    }

    private Node[][] createMatrix(boolean vertical) {
        Node[][] matrix = new Node[height()][width()];

        for (int i = 0; i < height(); i++)
            for (int j = 0; j < width(); j++)
                matrix[i][j] = new Node(j, i);
        
        int firstLim = vertical ? width() : height();
        int secondLim = vertical ? height() : width();

        for (int i = 0; i < firstLim; i++) {
            double energy = matrix[0][i].energy;
            matrix[0][i].dist = energy;
            if (1 < secondLim)
                for (int j = -1; j < 2; j++) {
                    int lin = vertical ? 1 : i+j;
                    int col = vertical ? i+j : 1;
                    
                    if (i+j < firstLim && i+j >= 0 &&
                        energy + matrix[lin][col].energy < matrix[lin][col].dist) {
                        matrix[lin][col].dist = energy + matrix[lin][col].energy;
                        matrix[lin][col].minFather = i;
                    }
                }
        }

        return matrix;
    }
   
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new java.lang.NullPointerException();

        if (width() == 1)
            throw new java.lang.IllegalArgumentException();
    }
   
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new java.lang.NullPointerException();
        
        if (height() == 1)
            throw new java.lang.IllegalArgumentException();
        
    }
   
    // do unit testing of this class
    public static void main(String[] args) {}
}
