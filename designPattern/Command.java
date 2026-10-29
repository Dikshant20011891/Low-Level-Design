package designPattern;

/*The Command Design Pattern is a behavioral pattern that turns a request into 
a standalone object, allowing you to parameterize actions, queue them, log them, 
or support undoable operations all while decoupling the sender from the receiver.*/

import java.util.Stack;

interface ControlCommand {
	void execute();
	void undo();
}

class Light{
	public void on() {
		System.out.println("Light turned on");
	}
	
	public void off() {
		System.out.println("Light turned off");
	}
}

class Thermostat{
	private int currentTemperature = 20;
	
	public void setTemperatur(int tmp) {
		System.out.println("Thermostat set to " + tmp + "C");
		currentTemperature = tmp;
	}
	
	public int getCurrentTemperature() {
		return currentTemperature;
	}
}

class LightOnCommand implements ControlCommand{
	private final Light light;
	
	public LightOnCommand(Light light) {
		this.light = light;
	}
	
	@Override
	public void execute() {
		light.on();
	}
	
	@Override
	public void undo() {
		light.off();
	}
}

class LightOffCommand implements ControlCommand{
	private final Light light;
	
	public LightOffCommand(Light light) {
		this.light = light;
	}
	
	@Override
	public void execute() {
		light.off();
	}
	
	@Override
	public void undo() {
		light.on();
	}
}

class SetTemperatureCommand implements ControlCommand{
	private final Thermostat thermostat;
	private final int newTemperature;
	private int previousTemperature;
	
	public SetTemperatureCommand(Thermostat thermostat,int temperature) {
		this.thermostat = thermostat;
		this.newTemperature = temperature;
	}
	
	@Override
	public void execute() {
		previousTemperature = thermostat.getCurrentTemperature();
		thermostat.setTemperatur(newTemperature);
	}
	
	@Override
	public void undo() {
		thermostat.setTemperatur(previousTemperature);
	}
}

class RemoteControl{
	private final Stack<ControlCommand> history = new Stack<>();
	
	public void executeCommand(ControlCommand command) {
		command.execute();
		history.push(command);
	}
	
	public void undoLast() {
		if(!history.isEmpty()) {
			ControlCommand lastCommand = history.pop();
			lastCommand.undo();
		}
		else {
			System.out.println("Nothing to undo.");
		}
	}
}


public class Command {
	public static void main(String[] args) {
		Light light = new Light();
		Thermostat thermostat = new Thermostat();
		
		ControlCommand lightOn = new LightOnCommand(light);
		ControlCommand lightOff = new LightOffCommand(light);
		ControlCommand setTemp = new SetTemperatureCommand(thermostat, 25);

        RemoteControl remote = new RemoteControl();

        System.out.println("--- Executing Commands ---");
        remote.executeCommand(lightOn);
        remote.executeCommand(setTemp);
        remote.executeCommand(lightOff);

        System.out.println("\n--- Undoing Commands ---");
        remote.undoLast();
        remote.undoLast();
        remote.undoLast();
        remote.undoLast();
	}
}
