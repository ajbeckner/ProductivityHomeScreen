package com.ajbecknerapps.productivityhomescreen;

import android.support.v4.app.Fragment;

/**
 * Created by AJ on 7/30/15.
 */
public class TieredListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TieredListMakerFragment();
    }
}
