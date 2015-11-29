package com.example.assy.rishomon.tools;

/**
 * Created by assy on 13/11/2015.
 */

        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

        import com.example.assy.rishomon.fragments.DrinksFragmens;
        import com.example.assy.rishomon.fragments.FoodFragment;
        import com.example.assy.rishomon.fragments.IceCreamFragment;
        import com.example.assy.rishomon.fragments.OtherFragment;
        import com.example.assy.rishomon.fragments.SalesFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String s;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FoodFragment tab1 = new FoodFragment();
                return tab1;
            case 1:
                DrinksFragmens tab2 = new DrinksFragmens();
                return tab2;
            case 2:
                IceCreamFragment tab3 = new IceCreamFragment();
                return tab3;
            case 3:
                OtherFragment tab4 = new OtherFragment();
                return tab4;
            case 4:
                SalesFragment tab5 = new SalesFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}