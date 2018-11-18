package Driver;

import FileSystem.*;
import FileSystem.FileSystem;

import java.io.File;
import java.util.Scanner;

public class MainGUI {

    public static final int BLOCKCOUNT = 256;
    public static final int BLOCKLENGTH = 512;

    public static void main (String [] args){
        int choice = 0;
        String method = "";
        int blockNum = 0;
        String fileName = "";

        MainController mainController = new MainController();

        Scanner scanner = new Scanner(System.in);

        // choose allocation methods
        System.out.println("File system type: ");
        method = scanner.nextLine();
        mainController.setFileSystem(method);

        // choice for the menu
        do{
            try {

                choice = chooseOptions();

                switch (choice) {
                    case 1: // Display a file
                        fileName = getFileName();
                        break;
                    case 2: // Display the file table
                        mainController.displayBlock(0);
                        break;
                    case 3: // Display bitmap
                        mainController.displayBlock(1);
                        break;
                    case 4: // Display a disk block
                        blockNum = getBlockNumber();
                        mainController.displayBlock(blockNum);
                        break;
                    case 5: // Copy a file from the simulation to a file on the real system

                        break;
                    case 6: // Copy a file from the real system to a file in the simulation
                        fileName = getFileName();
                        mainController.copyFile(fileName);
                        break;
                    case 7: // Delete a file

                        break;
                    default: // Exit
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
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
            System.out.print("Your choice: ");

            // get user's option
            choice = checkRange(getInt(), 1, 8);
        }
        while (choice == null);

        return choice;
    }

    private static int getBlockNumber(){
        Integer choice;

        // get user option until getting a valid choice
        do {
            System.out.println("Block number to display (from 2 to 255) : ");

            // get user's option
            choice = checkRange(getInt(), 0, 255);
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

    private static String getFileName(){
        String fileName = "";
        Scanner scanner = new Scanner(System.in);
        File file = null;

        do {
            System.out.println("Enter file name (max 8 characters): ");
            fileName = scanner.nextLine().trim();

            file = new File(fileName);
        }
        while (fileName.length() <= 8 && file.exists());

        System.out.println("File exists");

        return fileName;
    }

}
