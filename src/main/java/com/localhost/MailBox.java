package com.localhost;

public class MailBox {
	
	public final int max_num = 4;
	boolean stopped = false;
	private final int array[] = new int[max_num];
	private int num = 0;
	private int head = 0;
	private int tail = 0;
	
	public  int put(int x) throws InterruptedException {
		int res=0;
		if(num == max_num ){
			res= -1;
		}else{
			array[head++] = x;
			head %= max_num;
			num++;
			//notifyAll();
		}
		return res;
	}
	
	public  int get() throws InterruptedException {
		int r =0;
		if (num == 0){
			r = -1;
		}else{
			r = array[tail++];
			tail %= max_num;
			num--;
			//notifyAll();
		}
		return r;
	}
	
	public synchronized void stop(){
		stopped = true;
	}
	
	public synchronized void start() throws InterruptedException{
		while(num != 0){
			System.out.println("vous etes mis en attente pour le moment: get");
			wait();
		}
		stopped = false;
		notifyAll();
		
	}
	
}