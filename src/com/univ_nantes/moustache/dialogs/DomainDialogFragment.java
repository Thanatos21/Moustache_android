package com.univ_nantes.moustache.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.univ_nantes.moustache.R;

public class DomainDialogFragment extends DialogFragment {
	
	/* 
	 * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    public interface DomainDialogListener {
        public void onDomainDialogPositiveClick(DialogFragment dialog);
        public void onDomainDialogNegativeClick(DialogFragment dialog);
    }
    
    // Use this instance of the interface to deliver action events
    DomainDialogListener mListener;
    
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder createDomainDialog = new AlertDialog.Builder(getActivity());
		createDomainDialog.setTitle("New domain");
		
		// Adding a validation button to the dialog window
		createDomainDialog.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mListener.onDomainDialogPositiveClick(DomainDialogFragment.this);
			}
		});
		
		// Adding a cancel button to the window
		createDomainDialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mListener.onDomainDialogNegativeClick(DomainDialogFragment.this);
			}
		});
		
		// Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
		
		// Setting the layout
		createDomainDialog.setView(inflater.inflate(R.layout.create_dialog, null));
		
		return createDomainDialog.create();
    }
	
    // Override the Fragment.onAttach() method to instantiate the DomainDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DomainDialogListener so we can send events to the host
            mListener = (DomainDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
