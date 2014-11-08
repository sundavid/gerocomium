package com.swt.geracomium;

import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by dsun on 11/8/14.
 */


public class ClickedImage extends NetworkImageView {
    private String _url;
    private ImageLoader _loader;

    public ClickedImage(android.content.Context context) {
        super(context);
    }

    public ClickedImage(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickedImage(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(final View parent, final View other) {
        final ClickedImage self = this;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setVisibility(GONE);
                other.setVisibility(VISIBLE);
                if (other instanceof ClickedImage) {
                    ClickedImage clickedImage = (ClickedImage) other;
                    clickedImage.setImageUrl(self._url, self._loader);
                }
            }
        });
    }

    @Override
    public void setImageUrl(String url, ImageLoader loader) {
        super.setImageUrl(url, loader);
        this._url = url;
        this._loader = loader;
    }
}
