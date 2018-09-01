package happy.life.mantras;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private String url = "http://mantras.happylife.in/mantras_api";


    public static List<String>str = new ArrayList<String>();
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    //private List<Mantras> mantraList;
    private RecyclerView.Adapter adapter;
    public static HashMap<String,List<Mantras>> hm = new HashMap<>();
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    int lastgroupposition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_re);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        //	prepareListData();

        listAdapter = new ExpandableListAdapter(this, str, hm
        );

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
               /* if(lastgroupposition!=-1 && lastgroupposition!=groupPosition)
                {
                   expListView.collapseGroup(lastgroupposition);

                }
                lastgroupposition=groupPosition;*/
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        str.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        str.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
*/
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                TextView de = (TextView) v.findViewById(R.id.lblListItem);
                TextView ti = (TextView) v.findViewById(R.id.lblListHeader);


                String selected = "*"+ti.getText().toString()+"*"+ de.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, selected);
                startActivity(Intent.createChooser(intent, "Share via"));

                return false;
            }
        });

        // mList = findViewById(R.id.main_list);

        // mantraList = new ArrayList<>();
        //adapter = new MantraAdapter(getApplicationContext(),mantraList);


/*
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        mList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ViewGroup vg = (ViewGroup) view;
                        TextView ti = (TextView) vg.findViewById(R.id.title);
                        TextView de = (TextView) vg.findViewById(R.id.desc);

                        String selected = ti.getText().toString() + de.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, selected);
                        startActivity(Intent.createChooser(intent, "Share via"));
                    }

                })
        );*/


        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.sync_data)
        {


            if (isNetworkAvailable()) {
                Toast.makeText(this, "sync", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "succesful!", Toast.LENGTH_SHORT).show();
                hm.clear();;
                str.clear();;
                getData();;
            } else {
                Toast.makeText(this, "no internet connection..", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);



                        Mantras mantra = new Mantras();


                        String lineSep = System.getProperty("line.separator");

                        // mantra.setTitle(jsonObject.getString("title"));
                        str.add(jsonObject.getString("title"));
                        mantra.setDesc(jsonObject.getString("desc"));

                        mantra.desc= mantra.desc.replace("<br />", lineSep);
                        mantra.desc= mantra.desc.replace("<p>", lineSep);
                        mantra.desc= mantra.desc.replace("</p>", lineSep);
                        mantra.desc= mantra.desc.replace("<li>", lineSep);
                        mantra.desc= mantra.desc.replace("</li>", lineSep);
                        mantra.desc= mantra.desc.replace("<ol>", lineSep);
                        mantra.desc= mantra.desc.replace("</ol>", lineSep);
                        mantra.desc= mantra.desc.replace("<ul>", lineSep);
                        mantra.desc= mantra.desc.replace("</ul>", lineSep);



                        List<Mantras> mantraList= new ArrayList<>();
                        mantraList.clear();
                        mantraList.add(mantra);
                        hm.put(str.get(i),mantraList);
                        //  mantraList.clear();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                listAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                // Intent intent = new Intent (MainActivity1.this,happy.life.mantras.MainActivity1.class);
                //startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 30 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 5 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public void onBackPressed() {
        ExitActivity.exitApplicationAndRemoveFromRecent(MainActivity.this);
        super.onBackPressed();
    }
}
