package com.univ_nantes.moustache.activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import kernel.Domain;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.univ_nantes.moustache.R;
import com.univ_nantes.moustache.adapter.DomainAdapter;
import com.univ_nantes.moustache.adapter.SwipeDismissGroupListViewTouchListener;
import com.univ_nantes.moustache.dialog.DomainDialogFragment;
import com.univ_nantes.moustache.dialog.DomainDialogFragment.DomainDialogListener;

public class MainActivity extends Activity implements DomainDialogListener {
	private static final String SAVE_FILE_NAME = "moustache.save";
	
	private List<Domain> domainList;
	private DomainAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadSavedData();
		
		ExpandableListView exList = (ExpandableListView) findViewById(R.id.main_list);
	    adapter = new DomainAdapter(domainList, this);
	    adapter.registerDataSetObserver(new DataSetObserver() {
	    	public void onChanged() {
	    		saveData();
	    	}
		});
	    exList.setIndicatorBounds(0, 20);
	    exList.setAdapter(adapter);
	    SwipeDismissGroupListViewTouchListener touchListener =
                new SwipeDismissGroupListViewTouchListener(exList, new SwipeDismissGroupListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(View listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    //domainList.remove(adapter.getGroup(position));
                                	int grpPosition = ExpandableListView.getPackedPositionGroup(position);
                                	System.out.println(listView + " - "+ position + " - " + grpPosition);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        exList.setOnTouchListener(touchListener);
        exList.setOnScrollListener(touchListener.makeScrollListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_split_action_bar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch(item.getItemId()) {
			// User asked to create a new domain
			case R.id.action_domain_menu:
				DomainDialogFragment domainDialog = new DomainDialogFragment();
				domainDialog.show(getFragmentManager(), "createDomain");
			
			// Default case is always caused by a "not implemented" button
			default:
				System.err.println("Not yet implemented");
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDomainDialogPositiveClick(DialogFragment dialog) {
		// Retrieving the value entered by the user
		EditText edit = (EditText)dialog.getDialog().findViewById(R.id.create_dialog_titleValue);
		String domainTitle = edit.getText().toString();
		
		// Checking whether or not the user typed an entry
		if ( domainTitle.length() > 0 ) {
			Domain d = new Domain(domainTitle);
			domainList.add(d);
			adapter.notifyDataSetChanged();
			
			System.out.println("Creating a new domain with the name : " + domainTitle);
		}
		else {
//			AlertDialog.Builder warningSize = new AlertDialog.Builder(this);
//			warningSize.setMessage(R.string.warningSizeTitle);
//			warningSize.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    // Do nothing. Have to check if the Listener is mandatory
//                }
//            });
//
//			warningSize.create().show();
			Toast.makeText(getApplicationContext(), R.string.warningSizeTitle, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onDomainDialogNegativeClick(DialogFragment dialog) {
		System.out.println("Cancelling the new domain creation");
		dialog.dismiss();
	}
	
	public void saveData() {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(domainList);
			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadSavedData() {
		FileInputStream fis;
		ObjectInputStream ois;
		try
		{
			fis = openFileInput(SAVE_FILE_NAME);
			ois = new ObjectInputStream(fis);
			domainList = (ArrayList<Domain>) ois.readObject();
		}
		catch(Exception ex)
		{
			domainList = new ArrayList<Domain>();
			ex.printStackTrace();
		}
	}

}
