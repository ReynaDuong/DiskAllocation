package Driver;

public class ChainedFileSystem extends FileSystem {

    ChainedFileSystem(){
        dataArray[0] = new FileTableCC();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < DriverMain.BLOCKCOUNT; i++){
            dataArray[i] = new DataBlockChained(-1);
        }
    }

    @Override
    public void writeBlock (int blockNum){
    }

    @Override
    public void displayBitmap(){

    }
}
