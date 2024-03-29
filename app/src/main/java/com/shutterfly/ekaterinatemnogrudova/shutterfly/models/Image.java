package com.shutterfly.ekaterinatemnogrudova.shutterfly.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("previewURL")
    private String previewURL;
    @SerializedName("id")
    private String id;

    public String getPreviewUrl() {
        return previewURL;
    }

    public void setPreviewUrl(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        if (!id.equals(other.id))
            return false;
        return true;
    }

}
