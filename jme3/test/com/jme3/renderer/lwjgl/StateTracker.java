package com.jme3.renderer.lwjgl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateTracker {

	private List<State> states = new ArrayList<StateTracker.State>();

	public void registerCall(Object... args) {
		states.add(new State(getCallingMethod(true, 3), Arrays.asList(args)));
	}

	private String getCallingMethod(boolean className, int i) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		
		if(className)
			return stackTraceElements[i].getClassName() + "." + stackTraceElements[i].getMethodName();

		return stackTraceElements[i].getMethodName();
	}
	
	public String asString(){
		String result = states.toString();
		System.out.println("private static String " + getCallingMethod(false, 3) + " = \"" + result+"\";");	
		return result;
	}
	
	private class State{
		private String name;
		private List<Object> args;
		
		public State(String name, List<Object> args) {
			super();
			this.name = name;
			this.args = args;
		}

		@Override
		public String toString() {
			String result = "{" + name + " " + args.toString() +"}";
			return result;
		}
	}
}
