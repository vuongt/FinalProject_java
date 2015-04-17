package vfs;
import java.util.Scanner;

public class TestInterf {

	
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		boolean on=true;
		while(on){
			System.out.println("again");
			
			System.out.println("introduce:");
			String s=sc.nextLine();
			
			
			switch(s){
			
			case "ib":
				if(on){
					break;//works
				}
				
			case "iib":
				if(on){
					if(on){
						break;//works
					}
				}
			case "close":
				on=false;
				break;
			
			
			}
			
			
		}
		sc.close();
	}
}
