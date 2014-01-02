package com.xeeapps.HuPlayer;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.xeeapps.mappers.Mapper;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: khafaga Date: 10/09/12 Time: 03:36 ص To
 * change this template use File | Settings | File Templates.
 */
public class GUIMaker {

	private Cursor cursor;
	private ArrayList<Mapper> rowsValues = new ArrayList<Mapper>();
	private ArrayAdapter<Mapper> listAdapter;


	public void setupGUI(ListActivity activity, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder,
			int rowLayout,int rowId,EditText searchInput) {
		listAdapter = new ArrayAdapter<Mapper>(
				activity.getApplicationContext(), rowLayout,rowId);

		setCursor(activity.managedQuery(uri, projection, selection,
				selectionArgs, sortOrder));
		Log.i(activity.getClass() + " cursor values count", cursor.getCount()
				+ "");
		activity.startManagingCursor(cursor);
		int rowsCount = cursor.getCount();
        Log.i("cursor size: ", ""+rowsCount) ;
		cursor.moveToFirst();
		for (int i = 0; i < rowsCount; i++) {
			cursor.moveToPosition(i);
			Mapper currentValue =new Mapper( cursor.getString(0),i);
		//	if (getRowsValues().indexOf(currentValue) == -1) {
            rowsValues.add(currentValue);
				listAdapter.add(currentValue);
			//	getRowsValues().add(currentValue);
			//}
		}

		activity.setListAdapter(listAdapter);
         searchInput.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                 //To change body of implemented methods use File | Settings | File Templates.
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                listAdapter.getFilter().filter(charSequence);
             }

             @Override
             public void afterTextChanged(Editable editable) {
                 //To change body of implemented methods use File | Settings | File Templates.
             }
         });

	}

	public Cursor getCursor() {

		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

    public ArrayList<Mapper> getRowsValues() {
        return rowsValues;
    }

    public void setRowsValues(ArrayList<Mapper> rowsValues) {
        this.rowsValues = rowsValues;
    }
}
