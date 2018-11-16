package Driver;

public class FileTableCC extends FileTable {
    private class Record{
        String fileName;
        int startBlock;
        int length;

        Record(){

        }
    }

    private Record[] fileTable;

    FileTableCC(){
        fileTable = new Record[12];
    }

    boolean isFileTableEmpty(){
        return false;
    }

    boolean isFileTableFull(){
        return false;
    }

    @Override
    public void display() {

    }
}
