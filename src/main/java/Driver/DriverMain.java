package Driver;

import java.util.Scanner;

public class DriverMain {

    public static final int BLOCKCOUNT = 256;
    public static final int BLOCKLENGTH = 512;

    public static void main (String [] args){
        int choice = 0;
        String method = "";
        final String contiguousMethod = "contiguous";
        final String chainedMethod = "chained";
        final String indexedMethod = "indexed";
        FileSystem system;


        Scanner scanner = new Scanner(System.in);

        // choose allocation methods
        method = scanner.nextLine();

        if (method.equals(contiguousMethod)){
            system = new ContiguousFileSystem();
        }
        else if (method.equals(chainedMethod)){
            system = new ChainedFileSystem();
        }
        else{
            system = new IndexedFileSystem();
        }

        // choice for the menu
        do{
            choice = chooseOptions();

            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3: // display bitmap
                    system.displayBitmap();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:


            }

        }
        while (choice != 8);
    }


    private static int chooseOptions(){
        Integer choice;

        // get user option until getting a valid choice
        do {
            // display a menu for choosing
            System.out.println("1) Display a file");
            System.out.println("2) Display the file table");
            System.out.println("3) Display the free space bitmap");
            System.out.println("4) Display a disk block");
            System.out.println("5) Copy a file from the simulation to a file on the real system");
            System.out.println("6) Copy a file from the real system to a file in the simulation");
            System.out.println("7) Delete a file");
            System.out.println("8) Exit");

            // get user's option
            choice = checkRange(getInt(), 1, 8);
        }
        while (choice == null);

        return choice;
    }


    /**
     * get user's input for an integer and validate input
     * @return null if input is not an int or the integer inputted
     */
    private static Integer getInt(){
        Scanner scanner = new Scanner(System.in);
        String arg = scanner.next();
        Integer x;

        try{
            x = Integer.parseInt(arg);
        }
        catch(NumberFormatException e){
            x = null;
        }
        return x;
    }

    /**
     * check for the range of input
     * @param x null or integer
     * @param lowerBound lower bound
     * @param upperBound upper bound
     * @return the integer if input is in range
     */
    private static Integer checkRange(Integer x, int lowerBound, int upperBound){
        /*
        return null if input is not integer or not in the range
         */
        if (x == null || x < lowerBound || x > upperBound) {
            x = null;
        }
        return x;
    }

}
