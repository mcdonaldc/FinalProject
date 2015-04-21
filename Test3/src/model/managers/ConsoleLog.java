package model.managers;

import java.util.ArrayList;
import java.util.Iterator;

public class ConsoleLog implements Iterable<String>{
	
	private static ConsoleLog console;
	private ArrayList<String> messages;
	
	public static ConsoleLog getInstance(){
		if(console == null){
			console = new ConsoleLog();
		}
		return console;
	}
	
	private ConsoleLog(){
		messages = new ArrayList<String>(100);
	}
	
	public void log(String message){
		messages.add(message);
	}
	public Iterator<String> getLastMessages(int num){
		if(messages.size()-num-1 > -1){
			return messages.listIterator(messages.size()-num-1);
		}
		return iterator();
	}

	@Override
	public Iterator<String> iterator() {
		return messages.iterator();
	}
}
