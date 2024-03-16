package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.newsapp.customadapter.ArticleCustomAdapter;
import com.example.newsapp.model.ArticleModel;
import com.example.newsapp.viewmodel.ArticleViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArticleCustomAdapter adapter;
    ArticleViewModel viewModel;

    List<ArticleModel> articleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAssets());
            parseJSON(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.viewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =  new ArticleCustomAdapter(this, articleList);
        recyclerView.setAdapter(adapter);

        ///init the View Model
        /*viewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        viewModel.getArticleListObserver().observe(this, new Observer<List<ArticleModel>>() {
            @Override
            public void onChanged(@Nullable List<ArticleModel> articleModels) {
                if(articleModels != null) {
                    articleList = articleModels;
                    adapter.setArticleList(articleModels);
                }
            }
        });*/
    }

    private String loadJSONFromAssets(){
        String json = null;
        try {
            InputStream inputStream = getAssets().open("news.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();

            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private void parseJSON(JSONObject jsonObject){
        try {
            JSONArray articleArray = jsonObject.getJSONArray("articles");
            String format = "yyyy-MM-dd'T'HH:mm:ss"; //"2024-01-10T22:41:25Z"
            Gson gson = new GsonBuilder().setDateFormat(format).create();
            Type listType = new TypeToken<List<ArticleModel>>() {}.getType();
            articleList = gson.fromJson(articleArray.toString(), listType);
            Collections.sort(articleList, new Comparator<ArticleModel>() {
                @Override
                public int compare(ArticleModel t1, ArticleModel t2) {
                    return t1.getPublishedAt().compareTo(t2.getPublishedAt());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}