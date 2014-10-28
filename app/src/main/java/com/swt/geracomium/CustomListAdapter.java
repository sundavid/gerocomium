package com.swt.geracomium;

// import com.swt.geracomium.R;
// import com.swt.geracomium.AppController;
import com.swt.geracomium.entity.Article;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by tong on 14-10-23.
 * This is a custom list adapter class which provides data to list view.
 * In other words it renders the layout/article.xml in list
 * by pre_filling appropriate information.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Article> articles;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView author = (TextView)convertView.findViewById(R.id.author);
        TextView content = (TextView)convertView.findViewById(R.id.content);
        TextView published_at = (TextView)convertView.findViewById(R.id.pubTime);

        // get article data from the row
        Article doc = articles.get(position);

        // get thumbnail image
        thumbNail.setImageUrl(doc.getImageUrl(), imageLoader);

        // get title
        title.setText(doc.getTitle());

        // get author
        author.setText(doc.getAuthor().name);

        // get content
        content.setText(doc.getContent());

        // get published time
        published_at.setText(doc.getPublishedTime().toString());

        return convertView;
    }
}
