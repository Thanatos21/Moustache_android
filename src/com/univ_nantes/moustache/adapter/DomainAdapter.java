/**
 * 
 */
package com.univ_nantes.moustache.adapter;

import java.util.List;

import kernel.Domain;
import kernel.Task;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
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
@SuppressLint("NewApi")
public class DomainAdapter extends BaseExpandableListAdapter {
	
	/**
	 * This class is used to pass a parameter in the OnClickListener
	 * @author julien
	 *
	 */
	public class AddTaskListener implements View.OnClickListener {
		private int groupPosition;

		public AddTaskListener(int groupPosition) {
			super();
			this.groupPosition = groupPosition;
		}

		public void onClick(View v) {
			System.out.println(groupPosition);
			AlertDialog.Builder al = new AlertDialog.Builder(mContext);
			final View alView = mInflater.inflate(R.layout.create_dialog, null);
			al.setView(alView);
			String dialogTitle = mContext.getString(R.string.task_dialog_title) + " " + items.get(groupPosition).getName();
			al.setTitle(dialogTitle);
			al.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					EditText taskName = (EditText) alView.findViewById(R.id.create_dialog_titleValue);
					String sTaskName = taskName.getText().toString();
					
					if ( sTaskName.length() > 0 ) {
						Task t = new Task(taskName.getText().toString());
						if ( items.get(groupPosition).getTasks().contains(t) ) {
							Toast.makeText(mContext, R.string.warningSameTaskTitle, Toast.LENGTH_LONG).show();
						}
						else {
							items.get(groupPosition).addTask(t);
							notifyDataSetChanged();
						}
					}
					else {
						Toast.makeText(mContext, R.string.warningSizeTitle, Toast.LENGTH_LONG).show();
					}
				}
			});
	 
	        al.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
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
			super();
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
        
        //RelativeLayout taskList = (RelativeLayout) convertView.findViewById(R.id.task_sublist);
        
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
		
        TextView domainName = (TextView) convertView.findViewById(R.id.domain_name);
        domainName.setText(items.get(groupPosition).getName());
        
        TextView doneTasksIndicator = (TextView) convertView.findViewById(R.id.done_tasks_indicator);
        doneTasksIndicator.setText("[" + items.get(groupPosition).getNumberTaskDone() + "/" + items.get(groupPosition).getTasks().size() + "]");
        
        ImageView addTask = (ImageView) convertView.findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new AddTaskListener(groupPosition));
        
        //RelativeLayout domainList = (RelativeLayout) convertView.findViewById(R.id.domain_list);
        
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
