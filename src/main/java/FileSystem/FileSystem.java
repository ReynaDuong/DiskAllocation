package FileSystem;

abstract public class FileSystem {
    AbstractBlock[] dataArray = new AbstractBlock[256];

    abstract public void writeBlock (int blockNum);

    abstract public void displayBlock(int blockNumber);

    public boolean isFull(){
        return ((FileTable)dataArray[0]).isFileTableEmpty();
    }

}
