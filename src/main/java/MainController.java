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
        else {
            model.copyFile(fileName);
        }
    }
}
