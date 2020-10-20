package com.example.loaddatadynamically;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.loaddatadynamically.Interface.ILoadMore;
import com.example.loaddatadynamically.adapter.MyAdapter;
import com.example.loaddatadynamically.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    List<Item> items = new ArrayList<>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Random data
       random10Data();

        // Init Views

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView,this,items);
        recyclerView.setAdapter(adapter);
        // set load more event
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if (items.size()<=50) // check max size
                {
                    items.add(null);
                    adapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            adapter.notifyItemRemoved(items.size());
                            // random more data
                            int index = items.size();
                            int end = index+10;
                            for (int i=index;i<end;i++){
                                String name = UUID.randomUUID().toString();
                                Item item = new Item(name,name.length());
                                items.add(item);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },5000);
                }else{
                    Toast.makeText(MainActivity.this, "Load Data Completed !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void random10Data() {
        for (int i=0;i<10;i++){
            String name = UUID.randomUUID().toString();
            Item item = new Item(name,name.length());
            items.add(item);
        }
    }
}