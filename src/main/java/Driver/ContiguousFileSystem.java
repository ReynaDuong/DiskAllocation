package Driver;

public class ContiguousFileSystem extends FileSystem{

    ContiguousFileSystem (){
        dataArray[0] = new FileTableCC();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < DriverMain.BLOCKCOUNT; i++){
            dataArray[i] = new DataBlockCI();
        }
    }

    @Override
    public void writeBlock (int blockNum){
        // check if block is empty

        // write to the block
    }

    @Override
    public void displayBitmap(){
        dataArray[1].display();
    }

}
