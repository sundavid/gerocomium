package com.swt.geracomium;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.swt.geracomium.entity.User;
import com.swt.geracomium.entity.Utils;
import com.swt.geracomium.util.ImageUtils;
import com.swt.geracomium.volley.MultiPartStack;
import com.swt.geracomium.volley.MultiPartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PostFragment extends Fragment {

    private final int SELECT_PHOTO = 1;

    private RequestQueue mVolleyQueue;
    private RadioGroup type;
    private EditText messageContent;
    private GridLayout images;
    Button posMsgBtn;
    Button addImageBtn;
    private List<Uri> imageUris = new ArrayList<Uri>();


    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mVolleyQueue = Volley.newRequestQueue(getActivity(), new MultiPartStack());

        type = (RadioGroup)view.findViewById(R.id.message_type);
        messageContent = (EditText)view.findViewById(R.id.message_content);
        images = (GridLayout)view.findViewById(R.id.images);
        posMsgBtn = (Button)view.findViewById(R.id.post_message);
        addImageBtn = (Button)view.findViewById(R.id.add_image);

        final PostFragment self = this;
        posMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.postArticle();
            }
        });
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        User user = User.getUser();
        if (user.role != "nurse") {
            type.setVisibility(View.GONE);
        }

        return view;
    }

    private void postArticle() {
        final String messageContent = this.messageContent.getText().toString();
        RadioButton selectedButton = (RadioButton)getActivity().findViewById(this.type.getCheckedRadioButtonId());
        String typeStr = selectedButton != null ? selectedButton.getText().toString() : getString(R.string.message_article);
        String type = getString(R.string.message_type_article);
        if (typeStr == getString(R.string.message_alert)) type = getString(R.string.message_type_alert);
        else if(typeStr == getString(R.string.message_notification)) type = getString(R.string.message_type_notification);

        final String messageType = type;
        final PostFragment self = this;
        MultiPartStringRequest r = new MultiPartStringRequest(Request.Method.POST, Utils.server_address + "/api/articles/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent mIntent = new Intent(getActivity(), MainFrameActivity.class);
                        startActivity(mIntent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        for (Uri uri: this.imageUris) {
            Log.d("selected image", uri.toString());
            Log.d("selected image", ImageUtils.getPath(getActivity(), uri));
            String filename = ImageUtils.getPath(getActivity(), uri);
            Log.d("Image to upload", filename);
            r.addFileUpload(ImageUtils.getFileName(filename), new File(filename));
        }
        r.addStringUpload("csrfmiddlewaretoken", Utils.csrf_token);
        r.addStringUpload("_content_type", "application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("type", messageType);
            json.put("title", "title is optional");
            json.put("published_at", new SimpleDateFormat(getString(R.string.date_server_format)).format(new Date()));
            json.put("content", messageContent);
            json.put("author", User.getUser().id);

            r.addStringUpload("_content", json.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyQueue.add(r);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case SELECT_PHOTO: {
                    this.addImage(data.getData());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addImage(Uri imageUri) {
        final PostFragment self = this;

        ImageView view = new ImageView(this.images.getContext());
        int width = (Utils.window_width - 200) / 3;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width * 3 / 4);
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.removeImageView((ImageView)v);
            }
        });
        try {
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(imageUri, cr, width, width * 3 / 4);
            Log.d("bitmap", bitmap.toString());
            view.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        this.images.setVisibility(View.VISIBLE);
        this.images.addView(view);
        this.imageUris.add(imageUri);

        if (this.imageUris.size() >= 9) addImageBtn.setClickable(false);
    }

    private void removeImageView(ImageView image) {
        Drawable drawable = image.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.imageUris.remove(this.images.indexOfChild(image));
        this.images.removeView(image);

        if (this.imageUris.isEmpty())
            this.images.setVisibility(View.GONE);

        addImageBtn.setClickable(true);
    }
}
