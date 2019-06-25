package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.R;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.databinding.ActivityImagesBinding;
import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.FRAGMENT_IMAGES;

public class ImagesActivity extends AppCompatActivity {
    private ActivityImagesBinding mBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_images);
        if (savedInstanceState == null) {
            ImagesFragment imagesFragment = ImagesFragment.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, imagesFragment, FRAGMENT_IMAGES);
            fragmentTransaction.addToBackStack(FRAGMENT_IMAGES);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
       finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
