package app;

import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {

    Blockchain blockchain;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    @Override
    public Block call() throws Exception {
        return blockchain.nextBlock(Thread.currentThread().getId() + "");
    }
}
