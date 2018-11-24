public class ChainedFileSystem extends FileSystem {

    public ChainedFileSystem(){
        dataArray[0] = new FileTableContiguousChained();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < MainGUI.BLOCK_COUNT; i++){
            dataArray[i] = new DataBlockChained();
        }
    }

    @Override
    public void writeBlock(int blockNum, byte[] array) {

    }

    @Override
    public void displayBlock(int blockNumber) {
        // display the block
        dataArray[blockNumber].display();
    }

}
