package app;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args)  {
        Blockchain b = new Blockchain();
        //b.loadChain("data");
        ExecutorService es = Executors.newFixedThreadPool(10);
        ArrayList<Miner> miners = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Chat chat = new Chat();



            for (int mnr = 0; mnr < 10; mnr++) {

                miners.add(new Miner(b, chat));
            }


            try {
                b.addNewBlock(es.invokeAny(miners));
                b.printLastBlock();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


            }

            b.saveChain("data.db");
            es.shutdown();

    }
}



