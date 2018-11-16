package Driver;

abstract public class FileSystem {
    AbstractBlock[] dataArray = new AbstractBlock[256];

    abstract public void displayBitmap();
    abstract public void writeBlock (int blockNum);


}
