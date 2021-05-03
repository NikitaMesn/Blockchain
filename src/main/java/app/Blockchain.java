package app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Blockchain  implements Serializable {
    private static final long serialVersionUID = 54548826703748578L;

    private ArrayList<Block> blockList;
    private int numberOfZeros;


    public Blockchain(int numberOfZeros) {
        blockList = new ArrayList<>();
        this.numberOfZeros = numberOfZeros;

    }

    public void generateBlock() {
        Block  newBlock =  blockList.size() == 0
                ? new Block(zeros()) : new Block(blockList.get(blockList.size() - 1), zeros());

        blockList.add(newBlock);
    }

    public void printAllBlocks() {
        blockList.forEach(System.out::println);
    }

    private String zeros() {
        StringBuffer s = new StringBuffer("");
        for (int i = 0; i < numberOfZeros; i++) {
            s.append("0");
        }
        return  s.toString();
    }

    public void loadChain(String fileName) {
        SerializationUtils.loadBlockchain(blockList, fileName);
    }

    public void saveChain(String fileName) {
        SerializationUtils.saveBlockchain(blockList, fileName);
    }



    static private class SerializationUtils {

        private static void saveBlockchain(List<Block> chain, String fileName) {
            File file = new File(fileName);
            try (FileOutputStream fileOut = new FileOutputStream(file);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                for (Block block : chain) {
                    objectOut.writeObject(block);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void loadBlockchain(List<Block> chain, String fileName) {
            File file = new File(fileName);
            try (FileInputStream fileOut = new FileInputStream(file);
                 ObjectInputStream objectOut = new ObjectInputStream(fileOut)) {
                Object object;

                while (true) {
                    object  = objectOut.readObject();
                    Block b = (Block) object;
                    chain.add((b));
                }
            } catch (EOFException ignored) {

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}

