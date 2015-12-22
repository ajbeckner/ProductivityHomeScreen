package com.ajbecknerapps.productivityhomescreen;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by AJ on 7/29/15.
 */
public class TieredList {
    private String mtitle;
    private UUID mId;
    private ArrayList<UUID> mTieredListIds;
    private Context mContext;

    public TieredList(Context context,String title) {
        mtitle = title;
        mId = UUID.randomUUID();
        mTieredListIds = new ArrayList<UUID>();
        mContext = context;
    }

    public String getTitle() {
        return mtitle;
    }

    public UUID getId() {
        return mId;
    }

    public ArrayList<UUID> getTieredListIds() {
        return mTieredListIds;
    }

    public void setTieredListIds(ArrayList<UUID> tieredListIds) {
        this.mTieredListIds = tieredListIds;
    }

    void add(TieredList list){
        mTieredListIds.add(list.getId());
    }

    void add(UUID id) {
        mTieredListIds.add(id);
    }

    void remove(TieredList list){
        for(UUID id: getTieredListIds()){
            if(list.getId().equals(id)){
                mTieredListIds.remove(id);
            }
        }
    }

    ArrayList<UUID> getListIds(){
        return mTieredListIds;
    }

    ArrayList<TieredList> getLists(){
        ArrayList<TieredList> tieredLists = new ArrayList<>();
        for (TieredList list: TieredListArray.get(mContext).getLists()){
            for (UUID id : mTieredListIds) {
                if (list.getId().equals(id)){
                    tieredLists.add(list);
                }
            }
        }

        return tieredLists;
    }

    public void setTitle(String title) {
        this.mtitle = title;
    }
}
