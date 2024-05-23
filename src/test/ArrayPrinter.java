package test;

public class ArrayPrinter implements Runnable {
    public Tile[][] arrayToPrint;

    public ArrayPrinter(Tile[][] array) {
        this.arrayToPrint = array;
    }

    public void run() {
        for (Tile[] row : arrayToPrint) {
            for (Tile value : row) {
                if (value != null)
                    System.out.print(value.getLetter() + " ");
                else
                    System.out.print('?' + " ");
            }
            System.out.println();
        }
    }

}