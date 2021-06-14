// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
    public static final int ACCOUNTS = 20;     // number of accounts
    public static final int STARTING_AMOUNT = 1000;
    public BlockingQueue<Transaction> queue;
    public CountDownLatch done;
    public Account[] accounts;
    private int numWorkers;
    private static final Transaction terminator = new Transaction(-1, 0, 0);

    public Bank(int numWorkers) {
        queue = new ArrayBlockingQueue<Transaction>(10);
        done = new CountDownLatch(numWorkers);
        accounts = new Account[ACCOUNTS];
        for (int i = 0; i < ACCOUNTS; i++)
            accounts[i] = new Account(i, STARTING_AMOUNT);
        this.numWorkers = numWorkers;
    }

    private class Worker extends Thread {

        private final Bank bank;

        public Worker(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            while (true) {
                Transaction curr = null;
                try {
                    curr = bank.queue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (curr.from == -1) {
                    bank.done.countDown();
                    return;
                }
                accounts[curr.from].withdraw(curr.amount);
                accounts[curr.to].deposit(curr.amount);
            }
        }
    }

    /*
     Reads transaction data (from/to/amt) from a file for processing.
     (provided code)
     */
    public void readFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Use stream tokenizer to get successive words from file
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            while (true) {
                int read = tokenizer.nextToken();
                if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
                int from = (int) tokenizer.nval;

                tokenizer.nextToken();
                int to = (int) tokenizer.nval;

                tokenizer.nextToken();
                int amount = (int) tokenizer.nval;

                queue.put(new Transaction(from, to, amount));

            }
            for (int i = 0; i < numWorkers; i++)
                queue.put(terminator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
     Processes one file of transaction data
     -fork off workers
     -read file into the buffer
     -wait for the workers to finish
    */
    public void processFile(String file, int numWorkers) {
        for (int i = 0; i < numWorkers; i++) {
            Thread t = new Worker(this);
            t.start();
        }
        readFile(file);
    }


    /*
     Looks at commandline args and calls Bank processing.
    */
    public static void main(String[] args) throws InterruptedException {
        // deal with command-lines args
        if (args.length == 0) {
            System.out.println("Args: transaction-file [num-workers [limit]]");
            System.exit(1);
        }

        String file = args[0];

        int numWorkers = 1;
        if (args.length >= 2) {
            numWorkers = Integer.parseInt(args[1]);
        }
        Bank bank = new Bank(numWorkers);
        bank.processFile(file, numWorkers);
        bank.done.await();
        for (Account account : bank.accounts)
            System.out.println(account);
    }
}

