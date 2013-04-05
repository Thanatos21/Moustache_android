/**
 * 
 */
package com.univ_nantes.moustache.adapter;

import java.util.List;

import kernel.Domain;
import kernel.Task;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_nantes.moustache.R;

/**
 * @author julien
 *
 */
public class DomainAdapter extends BaseExpandableListAdapter {
	
	/**
	 * This class is used to pass a parameter in the OnClickListener
	 * @author julien
	 *
	 */
	public class AddTaskListener implements View.OnClickListener {
		private int groupPosition;

		public AddTaskListener(int groupPosition) {
			this.groupPosition = groupPosition;
		}

		public void onClick(View v) {
			System.out.println(groupPosition);
			AlertDialog.Builder al = new AlertDialog.Builder(mContext);
			final View alView = mInflater.inflate(R.layout.create_dialog, null);
			al.setView(alView);
			al.setTitle(R.string.task_dialog_title);
			al.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					EditText taskName = (EditText) alView.findViewById(R.id.create_dialog_titleValue);
					String sTaskName = taskName.getText().toString();
					
					if ( sTaskName.length() > 0 ) {
						items.get(groupPosition).addTask(new Task(taskName.getText().toString()));
						notifyDataSetChanged();
					}
					else {
						Toast.makeText(mContext, R.string.warningSizeTitle, Toast.LENGTH_LONG).show();
					}
				}
			});
	 
	        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
			al.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("Cancelling the new task creation");
				}
			});
			al.show();
		}
	}
	
	public class TaskCheckedListener implements View.OnClickListener {
		private int groupPosition;
		private int childPosition;
		
		public TaskCheckedListener(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onClick(View v) {
			System.out.println(groupPosition + " - " + childPosition);
			CheckBox c = (CheckBox) v;
			items.get(groupPosition).getTasks().get(childPosition).setTaskDone(c.isChecked());
			notifyDataSetChanged();
		}
	}

	
	
	private List<Domain> items;
	private Context mContext;
    private LayoutInflater mInflater;

    public DomainAdapter(List<Domain> items, Context mContext) {
		super();
		this.items = items;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition).getTasks().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
        {
            convertView = (RelativeLayout)mInflater.inflate(R.layout.task_sublist, parent, false);
        }
		
        TextView taskName = (TextView) convertView.findViewById(R.id.task_sublist_name);
        CheckBox taskCheck = (CheckBox) convertView.findViewById(R.id.task_sublist_check);
        
        taskName.setText(items.get(groupPosition).getTasks().get(childPosition).getName());
        
        taskCheck.setChecked(items.get(groupPosition).getTasks().get(childPosition).isTaskDone());
        taskCheck.setOnClickListener(new TaskCheckedListener(groupPosition, childPosition));
        
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return items.get(groupPosition).getTasks().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return items.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
        {
            convertView = (RelativeLayout)mInflater.inflate(R.layout.domain_list, parent, false);
        }
		
        TextView taskName = (TextView) convertView.findViewById(R.id.domain_name);
        taskName.setText(items.get(groupPosition).getName());
        
        ImageView addTask = (ImageView) convertView.findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new AddTaskListener(groupPosition));
        
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
