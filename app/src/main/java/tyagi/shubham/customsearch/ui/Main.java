package tyagi.shubham.customsearch.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import tyagi.shubham.customsearch.fragments.DownloadFragment;
import tyagi.shubham.customsearch.fragments.DorkFragment;
import tyagi.shubham.customsearch.fragments.AdvanceFragment;
import tyagi.shubham.customsearch.R;
import tyagi.shubham.customsearch.adapters.ViewPagerAdapter;

public class Main extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    DorkFragment dorkFragment;
    DownloadFragment downloadFragment;
    AdvanceFragment advanceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_settings:
                //Toast.makeText(this, "APP IN DEVELOPMENT", Toast.LENGTH_LONG).show();
                about();
                return true;
            case R.id.action_share:
                shareThis();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void about() {
      //  new Fan
        new FancyAlertDialog.Builder(this).setAnimation(Animation.SLIDE)
                .setTitle("About")
                .setMessage("This app is Developed by Shubham Tyagi(shubham2tyagi7@gmail.com)\nApp is in Development\nVersion:0.1")
                .setPositiveBtnBackground(Color.parseColor("#388d3b"))
                .setBackgroundColor(Color.parseColor("#d1245e"))
                .setPositiveBtnText("Okay")
                .isCancellable(false)
                .setNegativeBtnText("Share")
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        shareThis();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info, Icon.Visible).build();
    }

    private void shareThis() {
        PackageManager pm =getPackageManager();
        ApplicationInfo appInfo;
        try {
            appInfo=pm.getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+appInfo.publicSourceDir));
            startActivity(Intent.createChooser(intent, "Share it Using:"));
        }catch (PackageManager.NameNotFoundException e) {

        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        downloadFragment =new DownloadFragment();
        dorkFragment =new DorkFragment();
        advanceFragment =new AdvanceFragment();
        adapter.addFragment(downloadFragment,"DOWNLOAD");
        adapter.addFragment(dorkFragment,"DORK");
        adapter.addFragment(advanceFragment,"ADVANCE");
        viewPager.setAdapter(adapter);
    }

}
