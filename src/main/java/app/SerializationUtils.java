package app;

import java.io.*;
import java.util.List;

public class SerializationUtils {

    public static void saveBlockchain(List<Block> chain, String fileName) {
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

    public static void loadBlockchain(List<Block> chain, String fileName) {
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