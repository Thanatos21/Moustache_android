/**
 * 
 */
package com.univ_nantes.moustache.adapter;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * @author julien
 *
 */
public class ItemPositionAndType {
	public static enum itemType {DOMAIN, TASK};

	private int groupPosition;
	private int childPosition;
	private itemType type;
	
	
	public ItemPositionAndType(ExpandableListView v, int position) {
		this.groupPosition = 0;
		this.childPosition = 0;
		
		ExpandableListAdapter adapter = v.getExpandableListAdapter();
		
		int i = 0;
		do {
			if ( v.isGroupExpanded(i) ) {
				if ( adapter.getChildrenCount(i) < position ) {
					i += adapter.getChildrenCount(i) + 1;
				}
				else {
					
				}
			}
		} while ( i <= position );
	}
	public int getGroupPosition() {
		return groupPosition;
	}
	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}
	public int getChildPosition() {
		return childPosition;
	}
	public void setChildPosition(int childPosition) {
		this.childPosition = childPosition;
	}
	public itemType getType() {
		return type;
	}
	public void setType(itemType type) {
		this.type = type;
	}
}
