package app;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {

    Blockchain blockchain;
    Chat chat;

    public Miner(Blockchain blockchain, Chat chat) {
        this.blockchain = blockchain;
        this.chat = chat;

    }

    @Override
    public Block call() throws Exception {
        return blockchain.nextBlock(Thread.currentThread().getId() + "", chat);
    }


}


class Chat {
    private ArrayList<String> messages = new ArrayList<>();

    public void addMessage(String m) {
        messages.add(m);
    }

    public String receiveAllMessages() {
        StringBuilder allMessages = new StringBuilder("\n");

        for (String m : messages) {
            allMessages.append(m + "\n");
        }

        return allMessages.toString();
    }
}