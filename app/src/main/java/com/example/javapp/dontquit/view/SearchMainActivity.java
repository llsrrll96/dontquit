package com.example.javapp.dontquit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;

import com.example.javapp.dontquit.R;
//https://jaejong.tistory.com/93
public class SearchMainActivity extends AppCompatActivity {

    private SearchView searchView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        init();
        exListener();
    }

    public void init()
    {
        searchView = (SearchView) findViewById(R.id.search_view_main);
        intent = new Intent(getApplicationContext(),ResultActivity.class);
    }

    public void exListener()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.v("Search: ",s);
                intent.putExtra("query",s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}