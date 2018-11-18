package FileSystem;


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

    public boolean isFileTableEmpty(){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null){
                return false;
            }
        }
        return true;
    }


    @Override
    public void display() {
        if (isFileTableEmpty()){
            System.out.println("The file table is empty.");
        }
        else {
            for (int i = 0; i < 12; i++) {
                if (fileTable[i] != null) {
                    System.out.println(fileTable[i].fileName + "\t" + fileTable[i].startBlock + "\t" + fileTable[i].length);
                }

                System.out.println("\n");
            }
        }
    }
}
