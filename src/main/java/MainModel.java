import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

class MainModel {
    private FileSystem system;

    private static final String contiguousMethod = "contiguous";
    private static final String chainedMethod = "chained";
    private static final String indexedMethod = "indexed";

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

        // check if file exists
        if (!file.exists()){
            throw new Exception("File does not exist");
        }

        //byte[] byteArray = IOUtils.toByteArray(is);

        byte[] byteArray = Files.readAllBytes(new File(fileName).toPath());

        if (system instanceof ContiguousFileSystem){
            copyContiguous(byteArray, file.getName());
        }
        else if (system instanceof ChainedFileSystem){
            copyChained(byteArray, file.getName());
        }
        else {
            copyIndexed(byteArray, file.getName());
        }
    }

    private void copyContiguous(byte[] byteArray, String fileName) throws Exception{
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
            ((FileTableContiguousChained)system.accessBlock(0)).updateFileTable(fileName, startBlock, blockLength);
        }

    }

    private void copyChained(byte[] byteArray, String fileName) throws Exception{
        // each block 508 bytes
        int length = byteArray.length;
        int blockLength = 0;
        int startBlock = 0;
        byte[] array = new byte[508];
        int count = 0;
        int [] freeBlocks;
        DataBlockChained blockChained;
        int blockNum = 0;

        if (length > 5080){
            throw new Exception("The file is too large");
        }
        else {
            // each block 508 bytes
            blockLength = (int) Math.ceil((double)length/508);

            freeBlocks = ((BitMap)system.accessBlock(1)).randomFreeBlocks(blockLength);
            startBlock = freeBlocks[0];

            for (int i = 1; i <= length; i++){
                int j = i % 508;

                if (j % 508 == 0 || i == (length - 1)){
                    array = Arrays.copyOfRange(byteArray, 0, 509);

                    if (count < blockLength) {
                        // get a free block and write data
                        if (count != (blockLength - 1)) {
                            blockNum = freeBlocks[count];
                            blockChained = ((DataBlockChained)system.accessBlock(blockNum));
                            blockChained.writeBlock(array, freeBlocks[count + 1]);
                            // update bitmap
                            ((BitMap)(system.accessBlock(1))).updateBitmapLoadBlock(blockNum);
                        }
                        else if (count == blockLength - 1){
                            blockNum = freeBlocks[count];
                            ((DataBlockChained)system.accessBlock(blockNum)).writeBlock(array, -1);
                            ((BitMap)(system.accessBlock(1))).updateBitmapLoadBlock(blockNum);
                        }
                        // increase count
                        count++;
                    }
                }
            }

            // update file table
            ((FileTableContiguousChained)system.accessBlock(0)).updateFileTable(fileName, startBlock, blockLength);
        }
    }

    private void copyIndexed(byte[] byteArray, String fileName) throws Exception{
        // each block 512 bytes
        int length = byteArray.length;
        int indexedBlock = 0;
        int blockLength = 0;
        byte[] array = new byte[512];
        int count = 0;
        int [] freeBlocks;
        DataBlockContiguousIndexed dataBlock;
        int blockNum = 0;

        if (length > 5120){
            throw new Exception("The file is too large");
        }
        else {
            // each block 512 bytes
            // random length + 1 for the first block as indexed block
            blockLength = (int) Math.ceil((double)length/508);
            freeBlocks = ((BitMap)system.accessBlock(1)).randomFreeBlocks(blockLength + 1);

            // the first block is the indexed block
            indexedBlock = freeBlocks[0];
            ((IndexedFileSystem) system).writeIndexedBlock(indexedBlock, freeBlocks);

            // write to the next block
            for (int i = 1; i <= length; i++){
                int j = i % 512;

                if (j % 512 == 0 || i == (length - 1)){
                    array = Arrays.copyOfRange(byteArray, 0, 513);

                    if (count <= blockLength) {
                        // get a free block and write data
                        if (count != blockLength) {
                            blockNum = freeBlocks[count + 1];
                            (system).writeBlock(blockNum, array);
                        }
                        else{
                            break;
                        }
                        // increase count
                        count++;
                    }
                }
            }

            // update file table
            ((FileTableIndexed)system.accessBlock(0)).updateFileTable(fileName, indexedBlock);
        }
    }


    boolean doesFileExist(String fileName){
        if (system instanceof ContiguousFileSystem || system instanceof ChainedFileSystem){
            return ((FileTableContiguousChained)system.accessBlock(0)).doesFileExist(fileName);
        }
        else if (system instanceof IndexedFileSystem){
            FileTableIndexed fileTableIndexed = (FileTableIndexed)system.accessBlock(0);
            return fileTableIndexed.doesFileExist(fileName);
        }
        else{
            System.out.println();
        }
        return true;
    }

    void displayFile(String fileName){
        if (system instanceof ContiguousFileSystem){
            displayContiguous(fileName);
        }
        else if (system instanceof ChainedFileSystem){
            displayChained(fileName);
        }
        else {
            displayIndexed(fileName);
        }
    }

    private void displayContiguous(String fileName){
        // get file start block and file's length in the file table
        int fileStartBlock = 0;
        int fileLength = 0;

//        fileName = new File(fileName).getName();

        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);

        // display each block
        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            system.displayBlock(i);
            System.out.println();
        }
    }


    private void displayChained(String fileName){
        // get the file start block and length
        int fileStartBlock = 0;
        int fileLength = 0;
        int nextBlock = 0;

        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);

        nextBlock = fileStartBlock;

        // display blocks
        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            // display the first block
            if (nextBlock != -1) {
                ((DataBlockChained)system.accessBlock(nextBlock)).display();
                // get next block
                nextBlock = ((DataBlockChained)system.accessBlock(nextBlock)).getNextBlock();
            }
            else {
                break;
            }
        }
    }

    private void displayIndexed(String fileName){
        // get the file's indexed block
        int indexedBlockNum = 0;
        FileTableIndexed fileTableIndexedBlock = ((FileTableIndexed)system.accessBlock(0));

        indexedBlockNum = fileTableIndexedBlock.getFileIndexedBlock(fileName);

        // get the list of block
        IndexBlock indexBlock = ((IndexBlock)system.accessBlock(indexedBlockNum));
        int [] blockList = indexBlock.getIndexBlocks();
        int length = blockList.length;

        // display each block
        DataBlockContiguousIndexed dataBlockContiguousIndexed;
        for (int i = 0; i < length; i++){
            int blockNum = blockList[i];
            dataBlockContiguousIndexed = ((DataBlockContiguousIndexed)system.accessBlock(blockNum));

            dataBlockContiguousIndexed.display();

            System.out.println();
        }
    }

    void deleteFile(String fileName){
        if (system instanceof ContiguousFileSystem){
            deleteContiguous(fileName);
        }
        else if (system instanceof ChainedFileSystem){
            deleteChained(fileName);
        }
        else {
            deleteIndexed(fileName);
        }
    }

    private void deleteContiguous(String fileName) {
        // get file start block and file's length in the file table
        int fileStartBlock = 0;
        int fileLength = 0;

        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);

        // delete blocks of the file
        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            ((DataBlockContiguousIndexed)system.accessBlock(i)).deleteBlock();
            // update bitmap
            ((BitMap)system.accessBlock(1)).updateBitmapDeleteBlock(i);
        }

        // update file system
        ((FileTableContiguousChained)system.accessBlock(0)).deleteFileInfo(fileName);
    }

    
    private void deleteChained(String fileName){
        // get file start block and file length
        int fileStartBlock = 0;
        int fileLength = 0;
        int nextBlock = 0;
        int tempBlock;

        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);
        nextBlock = fileStartBlock;

        // go to block and delete
        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            // display the first block
            if (nextBlock != -1) {
                // get the next block number
                tempBlock = ((DataBlockChained)system.accessBlock(nextBlock)).getNextBlock();
                ((DataBlockChained)system.accessBlock(nextBlock)).deleteBlock();

                // update the bitmap
                ((BitMap)system.accessBlock(1)).updateBitmapDeleteBlock(nextBlock);

                // get next block
                nextBlock = tempBlock;
            }
            else {
                break;
            }
        }

        // update the file table
        ((FileTableContiguousChained)system.accessBlock(0)).deleteFileInfo(fileName);
    }


    private void deleteIndexed (String fileName){
        // get the file's indexed block
        int indexedBlockNum = 0;
        FileTableIndexed fileTableIndexedBlock = ((FileTableIndexed)system.accessBlock(0));

        indexedBlockNum = fileTableIndexedBlock.getFileIndexedBlock(fileName);

        // get the list of block
        IndexBlock indexBlock = ((IndexBlock)system.accessBlock(indexedBlockNum));
        int [] blockList = indexBlock.getIndexBlocks();
        int length = blockList.length;

        // delete the indexed block
        indexBlock.deleteBlock();
        ((BitMap)system.accessBlock(1)).updateBitmapDeleteBlock(indexedBlockNum);

        // delete each block
        DataBlockContiguousIndexed dataBlockContiguousIndexed;
        for (int i = 0; i < length; i++) {
            int blockNum = blockList[i];
            dataBlockContiguousIndexed = ((DataBlockContiguousIndexed) system.accessBlock(blockNum));
            dataBlockContiguousIndexed.deleteBlock();

            // update the bitmap
            ((BitMap)system.accessBlock(1)).updateBitmapDeleteBlock(blockNum);

        }

        // delete the file table
        ((FileTableIndexed)system.accessBlock(0)).deleteFileInfo(fileName);
    }


    void copyToRealSystem(String fileName) throws Exception {
        if (system instanceof ContiguousFileSystem){
            copyToRealSystemContiguous(fileName);
        }
        else if (system instanceof ChainedFileSystem){
            copyToRealSystemChained(fileName);
        }
        else {
            copyToRealSystemIndexed(fileName);
        }
    }

    private void copyToRealSystemContiguous(String fileName) throws Exception {
        // find the file and get info
        int fileStartBlock = 0;
        int fileLength = 0;
        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);

        byte [] bigByteArray = new byte[(512 * fileLength)];

        // merge into a byte array
        int count = 0;

        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            if (count == fileLength){
                break;
            }

            byte [] array = ((DataBlockContiguousIndexed)system.accessBlock(i)).getDataByte();
            // bigByteArray = Arrays.copyOfRange(array, 0, 513);
            System.arraycopy(array, 0, bigByteArray, count * 512, 512);
            count++;
        }

       writeToFile(fileName, bigByteArray);
    }

    private void copyToRealSystemChained(String fileName) throws Exception{
        // find the file and get info
        int fileStartBlock = 0;
        int fileLength = 0;
        fileStartBlock = ((FileTableContiguousChained)system.accessBlock(0)).getFileStartBlock(fileName);
        fileLength = ((FileTableContiguousChained)system.accessBlock(0)).getFileLength(fileName);
        int nextBlock = fileStartBlock;

        byte [] bigByteArray = new byte[(508 * fileLength)];

        // merge data blocks into a big one
        int count = 0;

        for (int i = fileStartBlock; i < (fileStartBlock + fileLength); i++){
            if (nextBlock != -1) {
                // merge into the big array
                byte [] array = ((DataBlockChained)system.accessBlock(nextBlock)).getDataByte();
                System.arraycopy(array, 0, bigByteArray, count * 508, 508);

                // get the next block number
                nextBlock = ((DataBlockChained)system.accessBlock(nextBlock)).getNextBlock();
                count++;
            }
            else {
                break;
            }
        }

        // write to file
        writeToFile(fileName, bigByteArray);
    }

    private void copyToRealSystemIndexed (String fileName) throws Exception{
        // get the file's indexed block
        int indexedBlockNum = 0;
        FileTableIndexed fileTableIndexedBlock = ((FileTableIndexed)system.accessBlock(0));

        indexedBlockNum = fileTableIndexedBlock.getFileIndexedBlock(fileName);

        // get the list of block
        IndexBlock indexBlock = ((IndexBlock)system.accessBlock(indexedBlockNum));
        int [] blockList = indexBlock.getIndexBlocks();
        int length = blockList.length;

        byte [] bigByteArray = new byte[(512 * length)];

        // merge into a big array
        int count = 0;

        for (int i = 0; i < length; i++){
            int blockNum = blockList[i];

            if (count == length){
                break;
            }
            byte [] array = ((DataBlockContiguousIndexed)system.accessBlock(blockNum)).getDataByte();
            System.arraycopy(array, 0, bigByteArray, count * 512, 512);
            count++;
        }

        writeToFile(fileName, bigByteArray);
    }


    private void writeToFile(String fileName, byte [] target) throws IOException {
        // write into a new file
        File file = new File(fileName);
        FileOutputStream stream = new FileOutputStream(file.getAbsolutePath());

        try {
            stream.write(target);
        } finally {
            stream.close();
        }

    }


}
