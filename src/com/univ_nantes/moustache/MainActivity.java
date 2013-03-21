package com.univ_nantes.moustache;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.univ_nantes.moustache.dialogs.DomainDialogFragment;
import com.univ_nantes.moustache.dialogs.DomainDialogFragment.DomainDialogListener;

public class MainActivity extends Activity implements DomainDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		
		// Checking whether the user typed or not an entry
		if ( domainTitle.length() > 0 ) {
			TextView text = ((TextView) findViewById(R.id.hello));
			text.setText(domainTitle);
		}
		else {
			AlertDialog.Builder warningSize = new AlertDialog.Builder(this);
			warningSize.setMessage(R.string.warningSizeTitle);
			warningSize.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Do nothing. Have to check if the Listener is mandatory
                }
            });

			warningSize.create().show();
		}
		System.out.println("Creating a new domain with the name : " + domainTitle);
	}

	@Override
	public void onDomainDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		System.out.println("Cancelling the new domain creation");
		dialog.dismiss();
	}

}
