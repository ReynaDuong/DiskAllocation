public class IndexedFileSystem extends FileSystem {

    public IndexedFileSystem (){
        dataArray[0] = new FileTableIndexed();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < MainGUI.BLOCK_COUNT; i++){
            dataArray[i] = null;
        }
    }

    @Override
    public void writeBlock(int blockNum, byte[] array) {
        DataBlockContiguousIndexed dataBlock = (DataBlockContiguousIndexed)dataArray[blockNum];

        if (dataArray[blockNum] == null){
            dataArray[blockNum] = new DataBlockContiguousIndexed();
            ((DataBlockContiguousIndexed)dataArray[blockNum]).writeBlock(array);
        }
        else{
            ((DataBlockContiguousIndexed)dataArray[blockNum]).writeBlock(array);
        }

        // update bitmap
        ((BitMap)dataArray[1]).updateBitmapLoadBlock(blockNum);
    }

    public void writeIndexedBlock(int indexedBlockNum, int [] blocksList){
        IndexBlock indexBlock = (IndexBlock) dataArray[indexedBlockNum];

        if (dataArray[indexedBlockNum] == null){
            dataArray[indexedBlockNum] = new IndexBlock();
            ((IndexBlock)dataArray[indexedBlockNum]).writeIndexedBlock(blocksList);
        }
        else {
            ((IndexBlock)dataArray[indexedBlockNum]).writeIndexedBlock(blocksList);
        }

        // update bitmap
        ((BitMap)dataArray[1]).updateBitmapLoadBlock(indexedBlockNum);
    }

    @Override
    public void displayBlock(int blockNumber) {
        if (dataArray[blockNumber] == null){
            System.out.println("NULL");
        }
        else if (dataArray[blockNumber] instanceof IndexBlock){
            ((IndexBlock)dataArray[blockNumber]).display();
        }
        else {
            dataArray[blockNumber].display();
        }
    }
}
