import java.util.ArrayList;

public class BitMap extends AbstractBlock {
    private boolean[] bitmap;

    BitMap(){
        bitmap = new boolean[MainGUI.BLOCK_COUNT];

        // empty disk
        for (int i = 0; i < MainGUI.BLOCK_COUNT; i++){
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

    public int getStartBlock(int numberOfBlock){
        int startBlock = 2;
        int blockCount = 0;

        for (int i = 2; i < MainGUI.BLOCK_COUNT; i++){
            if (!bitmap[i]) {
                blockCount++;
            }
            if (blockCount == numberOfBlock){
                break;
            }
        }
        return startBlock;
    }

    @Override
    public void display(){
        for (int i = 1; i <= MainGUI.BLOCK_COUNT; i++){
            System.out.print(bitmap[i-1] ? 1: 0);


            if (i % 32 == 0){
                System.out.print("\n");
            }
        }
        System.out.println();
    }

    public int [] randomFreeBlocks(int blockLength){
        int min = 0;
        int max = 0;
        int block = 0;

        int [] randomBlocks = new int[blockLength];

        // loop through the map, pick out all available block
        ArrayList<Integer> availableBlocks = new ArrayList<>();

        for (int i = 0; i < MainGUI.BLOCK_COUNT; i++){
            if (!bitmap[i]){
                availableBlocks.add(i);
            }
        }

        // random from the range of the list length
        min = 0;
        max = availableBlocks.size() + 1;

        for (int i = 0; i < blockLength; i++){
            block = (int) (min + (Math.random() * (max - min)));
            availableBlocks.remove(block);
            randomBlocks[i] = block;
            max--;
        }

        return randomBlocks;
    }

}
