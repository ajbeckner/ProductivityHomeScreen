package com.ajbecknerapps.productivityhomescreen;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by AJ on 7/29/15.
 */
public class TieredListFragment extends ListFragment {
    private TieredList mTieredList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mTieredList = TieredListArray.get(getActivity()).getList(0);
        TieredListAdapter adapter = new TieredListAdapter(mTieredList.getLists());
        setListAdapter(adapter);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState){
        View v = super.onCreateView(inflater,parent,savedInstanceState);

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.i("tag", "onKey Back listener is working!!!");
                        UUID id = mTieredList.getId();
                        for (TieredList list : TieredListArray.get(getActivity()).getLists()) {
                            for (UUID childId : list.getListIds()) {
                                if (id.equals(childId)) {
                                    mTieredList = list;
                                    TieredListAdapter adapter = new TieredListAdapter(mTieredList.getLists());
                                    setListAdapter(adapter);
                                    ((TieredListAdapter) getListAdapter()).notifyDataSetChanged();
                                    getActivity().setTitle(mTieredList.getTitle());
                                    return true;
                                }
                            }
                        }

                        //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getActivity().finish();
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tabbed_pager, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Log.d("tag", "add item touched");

                final TieredList tieredList = new TieredList(getActivity(),"Hold me down to edit");

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Edit");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
                input.setLayoutParams(lp);
                dialog.setView(input);

                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tieredList.setTitle(input.getText().toString());

                    }
                });
                dialog.show();

                mTieredList.add(tieredList);
                TieredListArray.get(getActivity()).getLists().add(tieredList);
                TieredListAdapter adapter = new TieredListAdapter(mTieredList.getLists());
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();
                //Intent i = new Intent(getActivity(), TieredListActivity.class);
                //i.putExtra(TieredListMakerFragment.EXTRA_TIEREDLIST_ID, tieredList.getId());
                //startActivityForResult(i, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_context_tieredlist, menu);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        TieredListAdapter adapter = (TieredListAdapter)getListAdapter();
        final TieredList tieredList = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                mTieredList.remove(tieredList);
                TieredListArray.get(getActivity()).getLists().remove(tieredList);
                adapter = new TieredListAdapter(mTieredList.getLists());
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_edit_crime:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Edit");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
                input.setLayoutParams(lp);
                dialog.setView(input);

                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    tieredList.setTitle(input.getText().toString());

                    }
                });


                dialog.show();

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("tag", ((TieredListAdapter)getListAdapter()).getItem(position).getTitle() + "was clicked");
        mTieredList = ((TieredListAdapter)getListAdapter()).getItem(position);
        TieredListAdapter adapter = new TieredListAdapter(mTieredList.getLists());
        setListAdapter(adapter);
        ((TieredListAdapter) getListAdapter()).notifyDataSetChanged();
        getActivity().setTitle(mTieredList.getTitle());
    }


    private class TieredListAdapter extends ArrayAdapter<TieredList> {

        public TieredListAdapter(ArrayList<TieredList> tieredLists) {
            super(getActivity(), 0, tieredLists);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_tieredlist, null);
            }

            // Configure the view for this Crime
            TieredList t = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.list_item_tieredlist_textview);
            titleTextView.setText(t.getTitle());

            return convertView;
        }

    }



}
