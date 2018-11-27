import java.util.Arrays;

public class DataBlockContiguousIndexed extends DataBlock{
    private byte[] dataByte;

    DataBlockContiguousIndexed(){
        dataByte = new byte[MainGUI.BLOCK_LENGTH];
    }

    public byte[] getDataByte() {
        return dataByte;
    }

    public void display() {
        for (int i = 1; i <= MainGUI.BLOCK_LENGTH; i++){
            System.out.print(dataByte[i-1]);


            if (i % 32 == 0){
                System.out.print("\n");
            }
        }

        System.out.println();
    }

    public void writeBlock(byte[] dataArray) {
        dataByte = Arrays.copyOf(dataArray, dataArray.length);
    }

    public void deleteBlock(){
        for (int i = 0; i < MainGUI.BLOCK_COUNT; i++){
            dataByte[i] = 0;
        }
    }


}
