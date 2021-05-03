package app;


import java.io.Serializable;
import java.util.Date;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Block implements Serializable {
    private static final long serialVersionUID = 54548826703748578L;
    private int id;
    private long timestampFirst;
    private long timestamp;
    private String hashBlock;
    private String previousHashBlock;
    private long magicNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return id == block.id && timestampFirst == block.timestampFirst && timestamp == block.timestamp && magicNumber == block.magicNumber && Objects.equals(hashBlock, block.hashBlock) && Objects.equals(previousHashBlock, block.previousHashBlock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestampFirst, timestamp, hashBlock, previousHashBlock, magicNumber);
    }

    public Block(String zeros) {
        long time = new Date().getTime();
        this.id = 1;
        this.previousHashBlock = "" + 0;
        this.timestampFirst = time;


        boolean isFind = false;
        String s = zeros;

        while(!isFind) {
            if(hashBlock != null && hashBlock.substring(0, s.length()).contains(s)) {
                isFind = true;
            }else {
                this.magicNumber = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
                this.hashBlock = applySha256("" + magicNumber );
                long time2 = new Date().getTime();
                this.timestamp = time2;
            }

        }

    }


    public Block(Block previous, String zeros) {

        long time = new Date().getTime();
        this.id = previous.getId() + 1;
        this.previousHashBlock = previous.getHash();
        this.timestampFirst = new Date().getTime();


        boolean isFind = false;
        String s = zeros;
        while (!isFind) {
            if (hashBlock != null && hashBlock.substring(0, s.length()).contains(s)) {
                isFind = true;
            } else {
                this.magicNumber = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
                this.hashBlock = applySha256("" + magicNumber);
                long time2 = new Date().getTime();
                this.timestamp = time2;
            }
        }
    }


        public String getHash () {
            return hashBlock;
        }
        public int getId () {
            return id;
        }

        private String applySha256 (String input){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                /* Applies sha256 to our input */
                byte[] hash = digest.digest(input.getBytes("UTF-8"));
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



    private long timeGenerating() {

        return  (timestamp - timestampFirst) ;
    }

        public String toString () {
            return "Block: " + "\n"
                    + "Id: " + id + "\n"
                    + "Timestamp: " + timestamp + "\n"
                    + "Magic number: " + +magicNumber + "\n"
                    + "Hash of the previous block: " + "\n" + previousHashBlock + "\n"
                    + "Hash of the block: \n" + hashBlock + "\n"
                    + "Block was generating for " + timeGenerating() +" seconds\n";

        }
}
