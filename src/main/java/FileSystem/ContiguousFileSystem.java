package FileSystem;

import Driver.MainGUI;

public class ContiguousFileSystem extends FileSystem{

    public ContiguousFileSystem(){
        dataArray[0] = new FileTableCC();
        dataArray[1] = new BitMap();

        // 254 blocks to hold data
        for (int i = 2; i < MainGUI.BLOCKCOUNT; i++){
            dataArray[i] = new DataBlockCI();
        }
    }

    @Override
    public void writeBlock (int blockNum){
        // check if block is empty

        // write to the block
    }

    @Override
    public void displayBlock (int blockNum){
        // display the block
        dataArray[blockNum].display();
    }
}
