public class FileTableContiguousChained extends FileTable {
    private class Record{
        String fileName;
        int startBlock;
        int length;

        Record(){

        }

        Record(String fileName, int startBlock, int length){
            this.fileName = fileName;
            this.startBlock = startBlock;
            this.length = length;
        }
    }

    private Record[] fileTable;

    FileTableContiguousChained(){
        fileTable = new Record[12];
    }

    private boolean isFileTableEmpty(){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null){
                return false;
            }
        }
        return true;
    }

    public boolean isFileTableFull(){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] == null){
                return false;
            }
        }
        return true;
    }

    public void updateFileTable(String fileName, int startBlock, int length){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] == null){
                // store the info
                fileTable[i] = new Record(fileName,startBlock, length);
                break;
            }
        }
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

    public boolean doesFileExist(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null && fileTable[i].fileName.equals(fileName)){
                return true;
            }
        }
        return false;
    }

    public int getFileStartBlock(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null && fileTable[i].fileName.equals(fileName)){
                return fileTable[i].startBlock;
            }
        }
        return 0;
    }

    public int getFileLength(String fileName){
        for (int i = 0; i < 12; i++){
            if (fileTable[i] != null && fileTable[i].fileName.equals(fileName)){
                return fileTable[i].length;
            }
        }
        return 0;
    }

    public void display() {
        if (isFileTableEmpty()){
            System.out.println("The file table is empty.");
        }
        else {
            for (int i = 0; i < 12; i++) {
                if (fileTable[i] != null) {
                    System.out.println(fileTable[i].fileName + "\t" + fileTable[i].startBlock + "\t" + fileTable[i].length);
                }
            }
        }
    }
}
