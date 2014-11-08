package com.swt.geracomium;

// import com.swt.geracomium.R;
// import com.swt.geracomium.AppController;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.swt.geracomium.entity.Article;
import com.swt.geracomium.entity.Utils;

import java.util.List;

import static android.view.ViewGroup.LayoutParams;

/**
 * Created by tong on 14-10-23.
 * This is a custom list adapter class which provides data to list view.
 * In other words it renders the layout/article.xml in list
 * by pre_filling appropriate information.
 */
public class CustomListAdapter extends BaseAdapter {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articles;

    public CustomListAdapter(Activity activity, List<Article> articles) {
        this.activity = activity;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int location) {
        return articles.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.article, null);
        }

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView thumbNail = (NetworkImageView)convertView.findViewById(R.id.thumbnail);
        // TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView author = (TextView)convertView.findViewById(R.id.author);
        TextView content = (TextView)convertView.findViewById(R.id.content);
        TextView published_at = (TextView)convertView.findViewById(R.id.pubTime);
        GridLayout grids = (GridLayout) convertView.findViewById(R.id.images);
        ClickedImage fullImage = (ClickedImage) convertView.findViewById(R.id.full_image);
        fullImage.init(fullImage, grids);

        // get article data from the row
        Article doc = articles.get(position);

        // get thumbnail image
        thumbNail.setImageUrl(Utils.server_address + doc.author_icon, imageLoader);

        // get title
        // title.setText(doc.getTitle());

        // get author
        author.setText(doc.author_name);

        // get content
        content.setText(doc.getContent());

        // get published time
        published_at.setText(doc.getPublishedTime());


        int maxWidth = Utils.window_width - 200;
        int width = maxWidth / 3;
        if (doc.images.length < 2) width = maxWidth - 200;
        for (String img : doc.images) {
            /*
            View v = inflater.inflate(R.layout.article_image, grids);
            ((NetworkImageView)v.findViewById(R.id.image)).setImageUrl(Utils.server_address + img, imageLoader);
            grids.getWidth() / 3 -
            if (true) continue;
            */
            Log.v("image", img);
            ClickedImage view = new ClickedImage(parent.getContext());
            view.init(grids, fullImage);
            LayoutParams params = new LayoutParams(width, width * 3 / 4);
            view.setPadding(5, 5, 5, 5);
            view.setLayoutParams(params);
            grids.addView(view);
            view.setImageUrl(Utils.server_address + img, imageLoader);
        }

        return convertView;
    }
}
