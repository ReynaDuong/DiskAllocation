package Driver;

public class IndexedFileSystem extends FileSystem {

    IndexedFileSystem (){
        dataArray[0] = new FileTableCC();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < DriverMain.BLOCKCOUNT; i++){
            dataArray[i] = new DataBlockCI();
        }
    }

    @Override
    public void writeBlock (int blockNum){
    }

    @Override
    public void displayBitmap(){

    }
}
