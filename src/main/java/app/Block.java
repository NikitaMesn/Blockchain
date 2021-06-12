package app;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

public class Block implements Serializable {
    private static final long serialVersionUID = 54548826703748578L;

    private final int id;
    private final String minerId;
    private final long timestampStart;
    private long timestampEnd;
    private String hashBlock;
    private final String data;
    private long magicNumber;
    private final int numberOfZeros;
    private int numberOfZerosNext;
    private final Block previous;


    public Block(String minerId, Block previous, Chat chat ) {
        timestampStart = new Date().getTime();

        if (previous == null) {
            this.minerId = minerId;
            this.id = 1;
            this.previous = null;
            this.numberOfZeros = 0;
            generateData();
            this.numberOfZerosNext = this.numberOfZerosNext + numberOfZerosForNext();
            this.data = "no messages\n";

        } else {
            this.minerId = minerId;
            this.previous = previous;
            this.id = previous.getId() + 1;
            this.numberOfZeros = previous.getNumberOfZerosNext();
            generateData();
            this.numberOfZerosNext = this.numberOfZeros + numberOfZerosForNext();
            this.data = chat.receiveAllMessages();
        }

        chat.addMessage(Thread.currentThread().getName() + ": Hello!");

    }


    private void generateData() {

        do {
            this.magicNumber = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            this.hashBlock = applySha256("" + magicNumber );
            this.timestampEnd = new Date().getTime();
            //System.out.println(this.id + " " + hashBlock);
        } while (!isFound());

    }

    private boolean isFound() {
        for (int i = 0; i < numberOfZeros; i++) {
            if (hashBlock.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private int numberOfZerosForNext() {
        long time = (timestampEnd - timestampStart) / 1000;
        if (time  < 15) return 1;
        if (time > 15 && this.id != 1) return -1;
        return 0;
    }


    public int getNumberOfZeros() {
        return this.numberOfZeros;
    }


    private String applySha256 (String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String toString () {

        String previousHash = this.id != 1 ?
                "Hash of the previous block: " + "\n" + previous.getHashBlock() + "\n" :
                "Hash of the previous block: 0 \n";

        String lastStr;
        if (this.numberOfZeros > this.numberOfZerosNext) {
            lastStr = "N was decreased by 1 \n";
        } else if (this.numberOfZeros < this.numberOfZerosNext) {
            lastStr = "N was increased to " + this.numberOfZerosNext + "\n";
        } else {
            lastStr = "N stays the same \n";
        }


        return "Block: " + "\n"
                + "Created by miner # " + minerId + "\n"
                + "Id: " + id + "\n"
                + "Timestamp: " + timestampEnd + "\n"
                + "Magic number: " + magicNumber + "\n"
                + previousHash
                + "Hash of the block: \n" + hashBlock + "\n"
                + "Block data: " + data
                + "Block was generating for " + ((timestampEnd - timestampStart) / 1000) +" seconds\n"
                + lastStr;
    }

    public int getId() {
        return id;
    }

    public String getHashBlock() {
        return hashBlock;
    }

    public int getNumberOfZerosNext() {
        return numberOfZerosNext;
    }
}
