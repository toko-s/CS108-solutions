// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private static Thread[] threads;
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Args: target [length] [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];
		if (args.length>2) {
			int len = Integer.parseInt(args[1]);
			int num = Integer.parseInt(args[2]);
			threads = new Thread[num];
			new Cracker().decipher(targ,len,num);
		}
		else{
			System.out.println(encrypt(targ));
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
	}

	private class Worker extends Thread{
		private int from;
		private int to;
		private String target;
		private int len;
		private CountDownLatch latch;

		public Worker(String target, int from, int to, int len, CountDownLatch latch){
			this.from = from;
			this.to = to;
			this.target = target;
			this.len = len;
			this.latch = latch;
		}

		private void search(String curr){
			if(curr.length() > len)
				return;
			if(encrypt(curr).equals(target)){
				System.out.println(curr);
				latch.countDown();
				return;
			}
			for(char c : CHARS) {
				if(isInterrupted())
					return;
				search(curr + c);
			}
		}

		@Override
		public void run() {
			for(int i = from; i < to; i++)
				search("" + CHARS[i]);
		}
	}

	private void decipher(String targ, int len, int num) {
		CountDownLatch latch = new CountDownLatch(1);
		int interval = CHARS.length/num;
		int i;
		for(i = 0;i < num - 1; i++){
			Thread t = new Worker(targ,i * interval, (i + 1) * interval,len,latch);
			t.start();
			threads[i] = t;
		}
		threads[i] = new Worker(targ,i * interval, CHARS.length,len,latch);
		threads[i].start();
		try {
			latch.await();
			for(Thread t : threads)
				if(t.isAlive())  t.interrupt();

			System.out.println("all done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String encrypt(String targ) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(targ.getBytes());
			return hexToString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
