package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments.DatosDosFragment;
import com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments.DatosTresFragment;
import com.misael.tesis.frequency.app.frequencyapp.Activities.Fragments.DatosUnoFragment;
import com.misael.tesis.frequency.app.frequencyapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatosActivity extends AppCompatActivity
        implements DatosUnoFragment.OnFragmentInteractionListener, DatosDosFragment.OnFragmentInteractionListener, DatosTresFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public TextView txtFechaDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        txtFechaDatos = (TextView) findViewById(R.id.fechaDatos);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        SimpleDateFormat day_date = new SimpleDateFormat("dd");
        String mes = month_date.format(calendar.getTime());
        String dia = day_date.format(calendar.getTime());
        String ano = year_date.format(calendar.getTime());

        txtFechaDatos.setText(dia +"-"+mes+"-"+ano);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            //PlaceholderFragment fragment = new PlaceholderFragment();
            Fragment fragment = null;

            switch(sectionNumber){
                case 1:
                        fragment = new DatosUnoFragment();
                    break;
                case 2:
                    fragment = new DatosDosFragment();
                    break;
                case 3:
                    fragment = new DatosTresFragment();
                    break;
            }

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            //fragment.setArguments(args);

            return fragment;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Horas de Sueño";
                case 1:
                    return "Comidas";
                case 2:
                    return "Observaciones";
            }
            return null;
        }
    }
}
