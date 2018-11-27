public class IndexBlock extends AbstractBlock {
    int [] indexBlock = new int[128];

    public void display() {
        for (int i = 0; i < 128; i++) {
            if (indexBlock[i] != 0) {
                System.out.print(indexBlock[i] + "\t");
            }
        }

        System.out.println();
    }

    public void writeIndexedBlock(int [] arrays){

        // the first element is the indexed block itself
        for (int i = 1; i < arrays.length; i++){
            indexBlock[i - 1] = arrays[i];
        }
    }

    private int getIndexedBlockLength() {
        int length = 0;

        for (int i = 0; i < 128; i++) {
            if (indexBlock[i] != 0) {
                length++;
            }
        }
        return length;
    }


    public int [] getIndexBlocks(){
        int length = getIndexedBlockLength();
        int [] blocks = new int[length];

        for (int i = 0; i < 128; i++){
            if (indexBlock[i] != 0){
                blocks[i] = indexBlock[i];
            }
        }

        return blocks;
    }

    public void deleteBlock (){
        for (int i = 0; i < 128; i++) {
            indexBlock[i] = 0;
        }
    }
}
