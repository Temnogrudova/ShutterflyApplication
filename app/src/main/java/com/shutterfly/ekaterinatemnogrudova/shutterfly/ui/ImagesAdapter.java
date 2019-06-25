package com.shutterfly.ekaterinatemnogrudova.shutterfly.ui;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.R;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.Image;
import com.bumptech.glide.Glide;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.utils.SharedPreference;
import java.util.List;
import jp.wasabeef.glide.transformations.CropTransformation;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.BindingHolder>{
    private int imagePreviewSize;
    private List<Image> mImages;
    private Context mContext;
    SharedPreference sharedPreference;

    public ImagesAdapter(List<Image> images, Context context, int imagePreviewSize) {
        mImages = images;
        mContext = context;
        this.imagePreviewSize = imagePreviewSize;
        sharedPreference = new SharedPreference();

    }

    @Override
    public ImagesAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_image_view_item, parent, false);
        view.getLayoutParams().height = imagePreviewSize;
        view.getLayoutParams().width = imagePreviewSize;
        view.requestLayout();
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesAdapter.BindingHolder holder, int position) {
        Image image = mImages.get(position);
        final ImageView ivPreview = holder.itemView.findViewById(R.id.iv_preview);
        Glide.with(ivPreview.getContext())
                .load(image.getPreviewUrl()).bitmapTransform(new CropTransformation(ivPreview.getContext()))
                .into(ivPreview);
        ImageView ivLike = holder.itemView.findViewById(R.id.iv_like);

        if (checkFavoriteItem(mImages.get(position))) {
            ivLike.setVisibility(View.VISIBLE);
        } else {
            ivLike.setVisibility(View.INVISIBLE);
        }
    }

    public boolean checkFavoriteItem(Image checkImage) {
        boolean check = false;
        List<Image> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            for (Image image : favorites) {
                if (image.equals(checkImage)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public int getItemCount() {
        if (mImages == null) {
            return 0;
        }
        return mImages.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        public BindingHolder(View rowView) {
            super(rowView);
            ImageView  ivPreview = rowView.findViewById(R.id.iv_preview);
            final ImageView ivLike = rowView.findViewById(R.id.iv_like);

            ivPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ivLike.getVisibility() == View.INVISIBLE)
                    {
                        sharedPreference.addFavorite(mContext, mImages.get(getAdapterPosition()));
                        ivLike.setVisibility(View.VISIBLE);
                    } else {
                        sharedPreference.removeFavorite(mContext, mImages.get(getAdapterPosition()));
                        ivLike.setVisibility(View.INVISIBLE);

                    }
                }
            });

        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

    }
}
