package app;

import java.io.*;
import java.util.ArrayList;


public class Blockchain  implements Serializable {
    private static final long serialVersionUID = 54548826703748578L;


    private ArrayList<Block> blockList;
    private int numberOfZeros;

    public Blockchain() {
        this.blockList = new ArrayList<>();
    }

    public Block nextBlock(String miner, Chat chat) throws FileNotFoundException {


        Block block;
        if (blockList.size() != 0) {

            block = new Block(miner, blockList.get(blockList.size() - 1), chat);
        } else {


            block = new Block(miner, null, chat);

        }

        return block;


    }

    public void addNewBlock(Block b) {
        blockList.add(b);
    }

    public void printLastBlock() {
        System.out.println(blockList.get(blockList.size() - 1));
    }



    public void printAllBlocks() {
        blockList.forEach(System.out::println);
    }


    public void loadChain(String fileName) {
        SerializationUtils.loadBlockchain(blockList, fileName);
    }

    public void saveChain(String fileName) {
        SerializationUtils.saveBlockchain(blockList, fileName);
    }

}

