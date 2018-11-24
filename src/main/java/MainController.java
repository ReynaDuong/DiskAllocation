class MainController {
    private MainModel model;

    MainController(){
    }

    void setFileSystem(String method){
        model = new MainModel(method);
    }

    void displayBlock(int blockNumber){
        model.displayBlock(blockNumber);
    }

    void copyFile(String fileName) throws Exception {

        if (model.isFileSystemFull()){
            throw new Exception("The file table is full.");
        }
        else if (model.doesFileExist(fileName)){
            throw new Exception("The file already exists.");
        }
        else {
            model.copyFile(fileName);
        }
    }


    void displayFile(String fileName) throws Exception {
        if (!model.doesFileExist(fileName)){
            throw new Exception("File not found");
        }
        else {
            model.displayFile(fileName);
        }
    }

    void deleteFile(String fileName) throws Exception {
        if (!model.doesFileExist(fileName)){
            throw new Exception("File not found");
        }
        else {
            model.deleteFile(fileName);
        }
    }

    void copyToRealSystem(String fileName) throws Exception {
        if (!model.doesFileExist(fileName)){
            throw new Exception("File not found");
        }
        else {
            model.copyToRealSystem(fileName);
        }
    }
}
