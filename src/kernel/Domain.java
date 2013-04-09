package kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author julien
 * This class represent a Domain, meaning a list of Task
 */
public class Domain implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
		if ( this.tasks.contains(t) ) {
			System.err.println("This task is already present in this domain's Task list");
		}
		else {
			this.tasks.add(t);
		}
	}
	
	/**
	 * This method allows you to remove a task in the ArrayList tasks
	 * @param t The Task you want to remove
	 */
	public void removeTask(Task t) {
		this.tasks.remove(t);
	}
	
	/**
	 * This method allow you to know the number of tasks done in this group
	 * @return 
	 */
	public int getNumberTaskDone() {
		int count = 0;
		for ( Task t : tasks ) {
			if ( t.isTaskDone() ) {
				count += 1;
			}
		}
		
		return count;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.name.equals(((Domain) o).getName());
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

	private  void readObject(ObjectInputStream ois)
			throws IOException, ClassNotFoundException {
		this.name = ois.readUTF();
		this.tasks = (List<Task>) ois.readObject();
	}

	// méthode writeObject, utilisée lors de la sérialization
	private  void writeObject(ObjectOutputStream oos)
			throws IOException {
		oos.writeUTF(this.name);
		oos.writeObject(this.tasks);
	}

}
