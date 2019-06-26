package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.R;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.databinding.FragmentImagesBinding;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.Image;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.pixabayApi.PixabayService;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;

import static com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.Constants.EMPTY_STRING;

public class ImagesFragment extends Fragment implements ImagesContract.View {
    private FragmentImagesBinding mBinder;
    private ImagesContract.Presenter mPresenter;
    private List<Image> mImages = new ArrayList<>();
    private ImagesAdapter mAdapter;
    private boolean isLoading = false;
    private int page = 1;
    private String mQuery = EMPTY_STRING;
    private boolean isSubmitedClicked = false;

    public static ImagesFragment newInstance() {
        return new ImagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false);
        View view = mBinder.getRoot();
        mBinder.toolBar.setTitle(getString(R.string.activity_images_tool_bar_title));
        ((ImagesActivity)getActivity()).setSupportActionBar(mBinder.toolBar);
        mPresenter = new ImagesPresenter(this,  new PixabayService(), new SchedulerProvider());
        initSearchViewListener();
        if (savedInstanceState ==null) {
            getImages();
        }
        initImagesListWithOrientationParams();
        return view;
    }

    private void initSearchViewListener() {
        mBinder.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mImages.clear();
                mQuery = query;
                page = 1;
                getImages();
                mBinder.searchView.clearFocus();
                isSubmitedClicked = true;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mBinder.searchView.getWidth() > 0 && isSubmitedClicked && newText.length() == 0)
                {
                    this.onQueryTextSubmit(EMPTY_STRING);
                    isSubmitedClicked = false;
                    return false;
                }
                return false;
            }
        });
    }

    public void initImagesListWithOrientationParams() {
        int imagePreviewSize = getPreviewSize();
        initImagesList(imagePreviewSize);
        initImagesListScrollListener();
    }

    private int getPreviewSize() {
        // Recognition of what orientation is now and getting current screen width
        int imagePreviewSize;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        GridLayoutManager gridLayoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePreviewSize = size.x / Constants.COLUMNS_IN_PORTRAIT;
            gridLayoutManager = new GridLayoutManager(getActivity(), Constants.COLUMNS_IN_PORTRAIT);
            mBinder.imagesList.setLayoutManager(gridLayoutManager);
        } else {
            imagePreviewSize = size.x / Constants.COLUMNS_IN_LANDSCAPE;
            gridLayoutManager = new GridLayoutManager(getActivity(), Constants.COLUMNS_IN_LANDSCAPE);
            mBinder.imagesList.setLayoutManager(gridLayoutManager);
        }
        return imagePreviewSize;
    }

    private void initImagesListScrollListener() {
        mBinder.imagesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mImages.size() - 1) {
                        page++;
                        getImages();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void initImagesList(int imagePreviewSize) {
        mAdapter = new ImagesAdapter(mImages, getActivity(), imagePreviewSize);
        mBinder.imagesList.setAdapter(mAdapter);
    }

    private void getImages() {
        mPresenter.getImages(mQuery, String.valueOf(page));
        mBinder.networkProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(ImagesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getImagesSuccess(List<Image> result) {
        mBinder.networkProgress.setVisibility(View.GONE);
        isLoading = false;
        mImages.addAll(result);
        mAdapter.notifyDataSetChanged();
    }
}
