public class ContiguousFileSystem extends FileSystem{

    public ContiguousFileSystem(){
        dataArray[0] = new FileTableContiguousChained();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < MainGUI.BLOCK_COUNT; i++){
            dataArray[i] = new DataBlockContiguousIndexed();
        }
    }

    @Override
    public void writeBlock (int blockNum, byte[] array){
        ((DataBlockContiguousIndexed)dataArray[blockNum]).writeBlock(array);

        // update bitmap
        ((BitMap)dataArray[1]).updateBitmapLoadBlock(blockNum);
    }

    @Override
    public void displayBlock (int blockNum){
        // display the block
        dataArray[blockNum].display();
    }
}
