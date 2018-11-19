import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

class MainModel {
    private FileSystem system;

    static final String contiguousMethod = "contiguous";
    static final String chainedMethod = "chained";
    static final String indexedMethod = "indexed";

    MainModel(String method){

        if (method.equals(MainModel.contiguousMethod)){
            system = new ContiguousFileSystem();
        }
        else if (method.equals(MainModel.chainedMethod)){
            system = new ChainedFileSystem();
        }
        else{
            system = new IndexedFileSystem();
        }
    }

    void displayBlock(int blockNumber){
        system.displayBlock(blockNumber);
    }


    boolean isFileSystemFull(){
        return system.isFull();
    }


    void copyFile(String fileName) throws Exception {
        // copy
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);

        //byte[] byteArray = IOUtils.toByteArray(is);

        byte[] byteArray = Files.readAllBytes(new File(fileName).toPath());

        if (system instanceof ContiguousFileSystem){
            copyContiguous(byteArray, file.getName());
        }
        else if (system instanceof ChainedFileSystem){

        }
        else {

        }
    }

    void copyContiguous(byte[] byteArray, String fileName) throws Exception{
        int length = byteArray.length;
        int blockLength = 0;
        int startBlock = 0;
        byte[] array = new byte[512];
        int count = 0;

        if (length > 5120){
            throw new Exception("The file is too large");
        }
        else {
            // each block 512 bytes
            blockLength = (int) Math.ceil((double)length/512);

            // find empty consecutive blocks
            startBlock = ((BitMap)(system.accessBlock(1))).getStartBlock(blockLength);
            System.out.println("The file is written from block " + startBlock);

            // write blocks
            for (int i = 1; i <= length; i++){
                int j = i % 512;

                if (j % 512 == 0 || i == (length - 1)){
                    array = Arrays.copyOfRange(byteArray, 0, 513);

                    if (count <= blockLength) {
                        system.writeBlock(startBlock + count, array);
                        count++;
                    }
                }
            }

            // update file table
            ((FileTableCC)system.accessBlock(0)).updateFileTable(fileName, startBlock, blockLength);
        }

    }

    void copyChained(byte[] byteArray){
        // each block 508 bytes
    }

    void copyIndexed(byte[] byteArray){
        // each block 512 bytes
    }
}
