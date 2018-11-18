package FileSystem;

import Driver.MainGUI;

public class BitMap extends AbstractBlock {
    private boolean[] bitmap;

    BitMap(){
        bitmap = new boolean[MainGUI.BLOCKCOUNT];

        // empty disk
        for (int i = 0; i < MainGUI.BLOCKCOUNT; i++){
            bitmap[i] = false;
        }

        // file table and bitmap
        bitmap[0] = bitmap[1] = true;
    }

    public void updateBitmapLoadBlock (int blockNum){
        bitmap[blockNum] = true;
    }

    public void updateBitmapDeleteBlock (int blockNum){
        bitmap[blockNum] = false;
    }

    public boolean isBlockEmpty (int blockNum){
        return !bitmap[blockNum];
    }

    @Override
    public void display(){
        for (int i = 1; i <= MainGUI.BLOCKCOUNT; i++){
            System.out.print(bitmap[i-1] ? 1: 0);


            if (i % 32 == 0){
                System.out.print("\n");
            }
        }

        System.out.println();
    }
}
