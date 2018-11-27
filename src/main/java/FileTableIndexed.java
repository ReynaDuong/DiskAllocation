public class FileTableIndexed extends FileTable {
    private class Record{
        String fileName;
        int indexedBlock;

        Record(){

        }

        Record(String fileName, int indexedBlock){
            this.indexedBlock = indexedBlock;
            this.fileName = fileName;
        }
    }

    private Record[] fileTable;

    FileTableIndexed(){
        fileTable = new Record[14];
    }

    private boolean isFileTableEmpty(){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null){
                return false;
            }
        }
        return true;
    }

    public boolean doesFileExist(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null && fileTable[i].fileName.equals(fileName)){
                return true;
            }
        }
        return false;
    }


    public void display() {
        if (isFileTableEmpty()){
            System.out.println("The file table is empty.");
        }
        else {
            for (int i = 0; i < 14; i++) {
                if (fileTable[i] != null) {
                    System.out.println(fileTable[i].fileName + "\t" + fileTable[i].indexedBlock);
                }
            }
        }
    }


    public boolean isFileTableFull(){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] == null){
                return false;
            }
        }
        return true;
    }

    public void updateFileTable(String fileName, int indexedBlock){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] == null){
                // store the info
                fileTable[i] = new Record(fileName, indexedBlock);
                break;
            }
        }
    }

    public int getFileIndexedBlock(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null && fileTable[i].fileName.equals(fileName)){
                return fileTable[i].indexedBlock;
            }
        }
        return 0;
    }


    public void deleteFileInfo(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i].fileName.equals(fileName)){
                // delete info
                fileTable[i] = null;
                break;
            }
        }
    }
}
