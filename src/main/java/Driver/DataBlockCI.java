package Driver;

public class DataBlockCI extends DataBlock{
    private byte[] dataByte;

    DataBlockCI (){
        dataByte = new byte[DriverMain.BLOCKLENGTH];
    }

    @Override
    public void display() {

    }
}
