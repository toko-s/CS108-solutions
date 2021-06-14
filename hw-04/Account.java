// Account.java

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int transactions;


	public Account(int id, int balance) {
		this.id = id;
		this.balance = balance;
		transactions = 0;
	}

	public synchronized void withdraw(int amount){
		transactions++;
		balance-=amount;
	}

	public synchronized void deposit(int amount){
		transactions++;
		balance+=amount;
	}

	@Override
	public String toString() {
		return "acct:" + id + " bal:" + balance + " trans:" + transactions;
	}
}
