package FileSystem;

public class FileTableIndexed extends FileTable {
    private class Record{
        String fileName;
        int indexedBlock;
    }

    boolean isFileTableEmpty(){
        return false;
    }

    @Override
    public void display() {

    }
}
