package kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author julien
 * This class represent a simple task with just a name and a boolean to know whether the task is done or not
 */
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected boolean taskDone;
	
	/**
	 * Creates a Task with the name given as a parameter.
	 * Default value for the boolean taskDone is false.
	 * @param name The name of the Task
	 */
	public Task(String name) {
		this.name = name;
		this.taskDone = false;
	}


	/**
	 * Getter for the attribute name
	 * @return
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
	 * Getter for the attribute taskDone
	 * @return
	 */
	public boolean isTaskDone() {
		return taskDone;
	}

	/**
	 * Setter for the attribute taskDone
	 * @param taskDone The new value of the attribute taskDone
	 */
	public void setTaskDone(boolean taskDone) {
		this.taskDone = taskDone;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.name.equals(((Task) obj).getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if ( this.taskDone ) {
			return this.name + " - DONE";
		}
		else {
			return this.name + " - TODO";
		}
	}
	
	private  void readObject(ObjectInputStream ois)
			throws IOException, ClassNotFoundException {
		this.name = ois.readUTF();
		this.taskDone = ois.readBoolean();
	}

	// méthode writeObject, utilisée lors de la sérialization
	private  void writeObject(ObjectOutputStream oos)
			throws IOException {
		oos.writeUTF(this.name);
		oos.writeBoolean(this.taskDone);
	}

}