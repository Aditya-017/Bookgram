package com.internshala.bookgram.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.internshala.bookgram.R;
import com.internshala.bookgram.activity.MainActivity;
import com.internshala.bookgram.adapter.DashboardRecyclerAdapter;
import com.internshala.bookgram.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class DashboardFragment extends Fragment {

    private RecyclerView rc;
    //private RecyclerView.LayoutManager lm;
   private DashboardRecyclerAdapter recyclerAdapter;
   private EditText st;
   private Button sb;
    private RequestQueue queue;
    public ArrayList<Book> bookInfoList =new ArrayList<Book>();




   /* public DashboardFragment() {
        // Required empty public constructor
        bookInfoList.add(new Book("p.s. I love you","Cecelia Aheren","RS 299","4.5",R.drawable.ps_ily ));
        bookInfoList.add(new Book("The Great Getsby","F. Scott Fitzgerald","RS 399","4.1",R.drawable.great_gatsby ));
        bookInfoList.add(new Book("Anna Karenina","Leo Tolstoy","RS 199","4.3",R.drawable.anna_karenina ));
        bookInfoList.add(new Book("Madame Bovary","Gustave Flaubert","RS 500","4.0",R.drawable.madam_bovary));
        bookInfoList.add(new Book("War and Peace","Leo Tolstoy","RS 249","4.8",R.drawable.war_and_peace));
        bookInfoList.add(new Book("Lolita","Vladimir Nabokov","RS 349","3.9",R.drawable.lolita));
        bookInfoList.add(new Book("Middlemarch","George Elliot","RS 599","4.2",R.drawable.midlemarch));
        bookInfoList.add(new Book("The Adventure of Huckleberry Finn","Mark Twain","RS 699","4.5",R.drawable.adventure_huckleberry));
        bookInfoList.add(new Book("Moby Dick","Herman Melville","RS 499","4.5",R.drawable.moby_dick));
        bookInfoList.add(new Book("The Lord Of The Rings","J.R.R. Tolkien","RS 749","5.0",R.drawable.the_lord_of_the_rings));
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        rc= view.findViewById(R.id.recyclerDashboard);
        st=view.findViewById(R.id.txtsearch);
        sb=view.findViewById(R.id.btnSearch);
        queue = Volley.newRequestQueue(getContext());

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookInfoList.clear();
                String search=st.getText().toString();
                search=search.replace(" ","+");
                Uri uri=Uri.parse(search);
                Uri.Builder builder=uri.buildUpon();
              //  search();
                parseJASON(builder.toString());

            }
        });

        return view;
    }

    private boolean Read_network_state(Context context)
    {    boolean is_connected;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }
    /*private void search() {
        String search_query = st.getText().toString();
        boolean is_connected = Read_Network_State(getContext());
    }*/




    private void parseJASON(String s) {
        String url="https://www.googleapis.com/books/v1/volumes?q=".concat(s);

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String Name="";
                String author="";
                String publisher="";
                String price="";
                String imgUrl="";

                try {
                    JSONArray items=response.getJSONArray("items");
                    for(int i=0;i<items.length();i++) {
                        JSONObject item = items.getJSONObject(i);
                        JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                        try {
                             Name = volumeInfo.getString("title");
                            JSONArray authors = volumeInfo.getJSONArray("authors");
                             author = authors.getString(0);
                             publisher = volumeInfo.getString("publisher");
                            JSONObject saleInfo = item.getJSONObject("saleInfo");
                            JSONObject listPrice =saleInfo.getJSONObject("listPrice");
                             price= String.valueOf(listPrice.getInt("amount"));
                            JSONObject imageLinks=volumeInfo.getJSONObject("imageLinks");
                            imgUrl = imageLinks.getString("smallThumbnail");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bookInfoList.add(new Book(Name,author,price,imgUrl,publisher));


                    }
                    rc.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerAdapter=new DashboardRecyclerAdapter(getContext(),bookInfoList);
                    rc.setAdapter(recyclerAdapter);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "volley error", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }
}
