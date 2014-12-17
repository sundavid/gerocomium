package com.swt.geracomium;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.swt.geracomium.entity.Article;
import com.swt.geracomium.entity.Utils;
import com.swt.geracomium.util.CookieJsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link}
 * interface.
 */
public class MessageFragment extends ListFragment {


    private static final String TAG = MainActivity.class.getSimpleName();

    // private OnFragmentInteractionListener mListener;

    // Log tag
    private List<Article> articleList = new ArrayList<Article>();
    private CustomListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        //setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //        R.layout.fragment_message_list, android.R.id.text1, DummyContent.ITEMS));
        adapter = new CustomListAdapter(this.getActivity(), articleList);
        setListAdapter(adapter);

        // Create Volley request obj
        CookieJsonArrayRequest articleReq = new CookieJsonArrayRequest(
                Utils.server_address + "/api/articles/?type=" + this.getType(),
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing Json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Article article = new Article();
                                article.parse(obj);

                                articleList.add(article);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // notify the adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(articleReq);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_list, container, false);
    }

    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */
    @Override
    /*
        now do nothing.
        todo: show article when item clicked.
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(),
                "You have selected " + position,
                Toast.LENGTH_SHORT).show();
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
    */


    protected String getType() {
        return "ARTICLE";
    }

}
