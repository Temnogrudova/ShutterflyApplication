package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.R;
import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.FRAGMENT_IMAGES;

public class ImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_images);
        if (savedInstanceState == null) {
            showImagesFragment();
        }
    }

    private void showImagesFragment() {
        ImagesFragment imagesFragment = ImagesFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, imagesFragment, FRAGMENT_IMAGES);
        fragmentTransaction.addToBackStack(FRAGMENT_IMAGES);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
