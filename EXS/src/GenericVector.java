public class GenericVector<Item> {
    private Item[] v;
    private int size;

    public GenericVector(int n) {
        v = (Item[]) new Object[n];
        size = n;
    }

    public void populate(Item item) {
        for (int i = 0; i < size; i++)
            v[i] = item;
    }

    public void printVector() {
        for (int i = 0; i < size; i++) 
            System.out.print(v[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        GenericVector<Integer> gvi = new GenericVector<Integer>(n);
        gvi.populate(6);
        gvi.printVector();
    }
}
