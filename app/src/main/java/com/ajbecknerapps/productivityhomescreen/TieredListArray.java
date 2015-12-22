package com.ajbecknerapps.productivityhomescreen;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by AJ on 7/29/15.
 */
public class TieredListArray {
    private static TieredListArray sTieredListArray = new TieredListArray();
    private static ArrayList<TieredList> mTieredLists;

    public static TieredListArray get(Context context) {
        if (mTieredLists == null){
            mTieredLists = new ArrayList<>();
            mTieredLists.add(new TieredList(context, null));
        }
        return sTieredListArray;
    }

    private TieredListArray() {
    }

    public ArrayList<TieredList> getLists() {
        return mTieredLists;
    }

    TieredList getList(int position){
        return mTieredLists.get(position);
    }
}
