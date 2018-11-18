package Driver;

import FileSystem.ChainedFileSystem;
import FileSystem.ContiguousFileSystem;
import FileSystem.*;
import FileSystem.IndexedFileSystem;

class MainModel {
    private FileSystem system;

    static final String contiguousMethod = "contiguous";
    static final String chainedMethod = "chained";
    static final String indexedMethod = "indexed";

    MainModel(String method){

        if (method.equals(MainModel.contiguousMethod)){
            system = new ContiguousFileSystem();
        }
        else if (method.equals(MainModel.chainedMethod)){
            system = new ChainedFileSystem();
        }
        else{
            system = new IndexedFileSystem();
        }
    }

    void displayBlock(int blockNumber){
        system.displayBlock(blockNumber);
    }


    boolean isFileSystemFull(){
        return system.isFull();
    }


    void copyFile(String fileName) {
        // copy
    }
}
