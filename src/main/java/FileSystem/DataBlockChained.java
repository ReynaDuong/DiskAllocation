package FileSystem;

public class DataBlockChained extends DataBlock {
    private int nextBlock;
    private byte[] dataByte;

    DataBlockChained(int nextBlock){
        this.nextBlock = nextBlock;
        dataByte = new byte[508];
    }

    private boolean isEmpty(){
        return dataByte.length == 0;
    }

    public boolean isEndBlock(){
        return isEmpty() && nextBlock == -1;
    }

    @Override
    public void display() {

    }
}
