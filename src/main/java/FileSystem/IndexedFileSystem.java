package FileSystem;

import Driver.MainGUI;

public class IndexedFileSystem extends FileSystem {

    public IndexedFileSystem (){
        dataArray[0] = new FileTableCC();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < MainGUI.BLOCKCOUNT; i++){
            dataArray[i] = new DataBlockCI();
        }
    }

    @Override
    public void writeBlock (int blockNum){
    }

    @Override
    public void displayBlock(int blockNumber) {

    }
}
