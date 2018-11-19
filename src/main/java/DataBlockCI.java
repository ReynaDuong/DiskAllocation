import java.util.Arrays;

public class DataBlockCI extends DataBlock{
    private byte[] dataByte;

    DataBlockCI (){
        dataByte = new byte[MainGUI.BLOCKLENGTH];
    }

    @Override
    public void display() {
        for (int i = 1; i <= MainGUI.BLOCKCOUNT; i++){
            System.out.print(dataByte[i-1]);


            if (i % 32 == 0){
                System.out.print("\n");
            }
        }

        System.out.println();
    }

    @Override
    public void writeBlock(byte[] dataArray) {
        dataByte = Arrays.copyOf(dataArray, dataArray.length);
    }
}
