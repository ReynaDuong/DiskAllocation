import java.util.Arrays;

public class DataBlockChained extends DataBlock {
    private int nextBlock;
    private byte[] dataByte;

    DataBlockChained(){
        this.nextBlock = -1;
        dataByte = new byte[508];
    }

    private boolean isEmpty(){
        return dataByte.length == 0;
    }

    public boolean isEndBlock(){
        return isEmpty() && nextBlock == -1;
    }

    public void display() {
        for (int i = 1; i <= 508; i++){
            System.out.print(dataByte[i-1]);

            if (i % 32 == 0){
                System.out.print("\n");
            }
        }

        System.out.println();
    }

    public byte[] getDataByte() {
        return dataByte;
    }

    public int getNextBlock(){
        return nextBlock;
    }

    public void writeBlock(byte[] dataArray){}

    public void writeBlock(byte[] dataArray, int nextBlock){
        dataByte = Arrays.copyOf(dataArray, dataArray.length);
        this.nextBlock = nextBlock;
    }

    public void deleteBlock(){
        for (int i = 0; i < 508; i++){
            dataByte[i] = 0;
        }

        nextBlock = -1;
    }
}
