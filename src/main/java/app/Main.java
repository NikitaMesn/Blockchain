package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must start with: ");
        int numberOfZeros = input.nextInt();

        Blockchain chain = new Blockchain(numberOfZeros);
        chain.loadChain("chain.data");
        for (int i = 0; i < 5; i++) {
            chain.generateBlock();
        }

        chain.printAllBlocks();
        chain.saveChain("chain.data");

    }
}
