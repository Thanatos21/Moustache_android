package kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author julien
 * This class represent a Domain, meaning a list of Task
 */
public class Domain {
	protected String name;
	protected List<Task> tasks;
	
	/**
	 * Creates a Task with the name given as a parameter.
	 * Default value for tasks is an empty ArrayList
	 * @param name The name of the Domain
	 */
	public Domain(String name) {
		this.name = name;
		this.tasks = new ArrayList<Task>();
	}
	
	/**
	 * Creates a Task with the name and Task list given as parameters. 
	 * @param name The name of the Domain
	 * @param t The ArrayList of Task.
	 */
	public Domain(String name, List<Task> t) {
		this.name = name;
		this.tasks = t;
	}
	
	/**
	 * Getter for the attribute name
	 * @return The attribute name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the attribute name
	 * @param name The new value of the attribute name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the attribute tasks
	 * @return The attribute tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Setter for the attribute tasks
	 * @param tasks The new value of the attribute tasks
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * This method allows you to add a Task in the ArrayList tasks.
	 * If the Task you want to add is already in tasks(same name), it won't be added.
	 * @param t The Task you want to add
	 */
	public void addTask(Task t) {
		if ( !this.tasks.contains(t) ) {
			this.tasks.add(t);
		}
		else {
			System.err.println("This task is already present in this domain's Task list");
		}
	}
	
	/**
	 * This method allows you to remove a task in the ArrayList tasks
	 * @param t The Task you want to remove
	 */
	public void removeTask(Task t) {
		this.tasks.remove(t);
	}
	
	@Override
	public String toString() {
		String s;
		
		s = "Domain name : " + this.name + "\n";
		Iterator<Task> i = this.tasks.iterator();
		Task t;
		
		while ( i.hasNext() ) {
			t = i.next();
			s += "\t" + t.toString() + "\n";
		}
		
		return s;
	}
}
