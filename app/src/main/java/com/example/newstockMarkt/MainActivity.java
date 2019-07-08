package com.example.newstockMarkt;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.newstockMarkt.App.MainApp;
import com.example.newstockMarkt.Models.CompanySearchModel;
import com.example.newstockMarkt.Models.NewsModel;
import com.example.newstockmarkt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView newsRecycler;
    NewsAdapter newsAdapter;
    ArrayList<NewsModel> newsData;
    EditText companySearchEdit;
    int pageNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecycler = findViewById(R.id.news_recycler);
        newsAdapter = new NewsAdapter(this);
        newsData = new ArrayList<>();
        newsRecycler.setHasFixedSize(true);
        CustomLinearLayout linearLayoutManager = new CustomLinearLayout(this, RecyclerView.VERTICAL, false, false);
        newsRecycler.setLayoutManager(linearLayoutManager);
        newsAdapter.setNewsData(newsData);
        newsAdapter.setNewsListEventListner(new NewsAdapter.newsListEventListner() {
            @Override
            public void onClick(String url) {
                findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
                DetailsFragment newsDetailFragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                newsDetailFragment.setArguments(bundle);
                Utils.hideKeyboard(MainActivity.this);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newsDetailFragment).commit();
            }
        });
        newsRecycler.setAdapter(newsAdapter);
        companySearchEdit = findViewById(R.id.searchEdt);
        final RecyclerView searchRecycler = findViewById(R.id.search_recycler);
        final View clearSearchBtn = findViewById(R.id.close_search);
        CustomLinearLayout searchLayoutManager = new CustomLinearLayout(this, RecyclerView.VERTICAL, false, false);
        searchRecycler.setLayoutManager(searchLayoutManager);
        final ArrayList<CompanySearchModel> companyData = new ArrayList<>();
        final CompanySearchAdapter companySearchAdapter = new CompanySearchAdapter(this,companyData);
        searchRecycler.setAdapter(companySearchAdapter);
        pageNo = 1;
        callNewsApi();
        companySearchEdit.addTextChangedListener(new TextWatcher() {
            ProgressBar progressBar = findViewById(R.id.progressbar);
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                progressBar.setVisibility(View.VISIBLE);
                companyData.clear();
                companySearchAdapter.notifyDataSetChanged();
                searchRecycler.setVisibility(View.VISIBLE);
                searchRecycler.bringToFront();
                progressBar.bringToFront();
                if (charSequence.length() > 0) {

                    String searchUrl = Utils.getSearchApi(charSequence.toString());
                    JsonObjectRequest searchJsonObj = new JsonObjectRequest(Request.Method.GET,
                            searchUrl, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    progressBar.setVisibility(View.GONE);
                                    try {
                                        JSONArray newsArr = response.getJSONArray("bestMatches");
                                        for (int i = 0; i < newsArr.length(); i++) {
                                            JSONObject jsonObject = newsArr.getJSONObject(i);
                                            CompanySearchModel searchModel = new CompanySearchModel();
                                            searchModel.setCompanySmybl(jsonObject.getString("1. symbol"));
                                            searchModel.setCompanyName(jsonObject.getString("2. name"));
                                            companyData.add(searchModel);
                                        }
                                        clearSearchBtn.setVisibility(View.VISIBLE);
                                        companySearchAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // hide the progress dialog
                            searchRecycler.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    MainApp.getInstance().addToRequestQueue(searchJsonObj);
                } else {
                    //if empty search text clear the previous search data and remove the search recycler view
                    companyData.clear();
                    progressBar.setVisibility(View.GONE);
                    companySearchAdapter.notifyDataSetChanged();
                    searchRecycler.setVisibility(View.GONE);
                    clearSearchBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        clearSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companySearchEdit.setText("");
                companyData.clear();
                companySearchAdapter.notifyDataSetChanged();
                searchRecycler.setVisibility(View.GONE);
                clearSearchBtn.setVisibility(View.GONE);
            }
        });
      newsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
          }

          @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              // At the end of recyclerview get more data with new pageIndex value (PageNo++ = 20 more news data )
              if(!recyclerView.canScrollVertically(1) && dy != 0)
              {
                  pageNo++;
                  callNewsApi();

              }

          }
      });

    }

    @Override
    public void onBackPressed() {
        // Remove details fragment on backpress
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof IOnBackPressed) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    void callNewsApi(){
        String url = Utils.getNewsApiUrl(Utils.dateToStr("yyyy-MM-dd", new Date()), pageNo);
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray newsArr = response.getJSONArray("articles");
                            for (int i = 0; i < newsArr.length(); i++) {
                                JSONObject jsonObject = newsArr.getJSONObject(i);
                                NewsModel newsModel = new NewsModel();
                                newsModel.setTitle(jsonObject.getString("title"));
                                newsModel.setDescription(jsonObject.getString("description"));
                                newsModel.setPublicer(jsonObject.getString("author"));
                                newsModel.setNewsUrl(jsonObject.getString("url"));
                                newsModel.setThumbNailUrl(jsonObject.getString("urlToImage"));
                                newsModel.setPublishedDate(Utils.changeDateFormat(jsonObject.getString("publishedAt").replace("T"," "),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
                                newsData.add(newsModel);
                            }
                            newsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // hide the progress dialog
                progressBar.setVisibility(View.GONE);
            }
        });
        MainApp.getInstance().addToRequestQueue(jsonObjReq);
    }
}
