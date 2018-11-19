public class FileTableCC extends FileTable {
    private class Record{
        String fileName;
        int startBlock;
        int length;

        Record(String fileName, int startBlock, int length){
            this.fileName = fileName;
            this.startBlock = startBlock;
            this.length = length;
        }
    }

    private Record[] fileTable;

    FileTableCC(){
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

    @Override
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


    @Override
    public void display() {
        if (isFileTableEmpty()){
            System.out.println("The file table is empty.");
        }
        else {
            for (int i = 0; i < 12; i++) {
                if (fileTable[i] != null) {
                    System.out.println(fileTable[i].fileName + "\t" + fileTable[i].startBlock + "\t" + fileTable[i].length);

                    System.out.println("\n");
                }
            }
        }
    }
}
