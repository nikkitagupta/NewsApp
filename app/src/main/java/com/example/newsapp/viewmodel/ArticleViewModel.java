package com.example.newsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.ArticleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ArticleViewModel extends ViewModel {

    private MutableLiveData<List<ArticleModel>> articleList;

    public ArticleViewModel(MutableLiveData<List<ArticleModel>> articleList) {
        this.articleList = articleList;
    }

    public MutableLiveData<List<ArticleModel>> getArticleListObserver() {
        return articleList;

    }

}
