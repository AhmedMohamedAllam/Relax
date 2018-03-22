package com.allam.relax.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.model.Item;
import com.allam.relax.view.CreateItemDialog;
import com.allam.relax.view.NavigationDrawer;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private ArrayList<Item> mOrderItems;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationDrawer.getDrawer(this, toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        // test recyclerview
        mOrderItems = getTestData();
        mRecyclerView = findViewById(R.id.orders_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(new OrderAdapter(mOrderItems));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mItemNameTextView;
        private Item mCurrentItem;
        public OrderHolder(View itemView) {
            super(itemView);
            mItemNameTextView = itemView.findViewById(android.R.id.text1);
            mItemNameTextView.setOnClickListener(this);
        }

        public void onBindView(Item item){
            mItemNameTextView.setText(item.getName());
            mCurrentItem = item;
        }

        @Override
        public void onClick(View view) {
            CreateItemDialog dialog = new CreateItemDialog();
            dialog.show(getFragmentManager(), "");
        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
        private ArrayList<Item> mItems;
        public OrderAdapter(ArrayList<Item> items) {
            mItems = items;
        }

        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            holder.onBindView(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }


    private ArrayList<Item> getTestData(){
        ArrayList<Item> items = new ArrayList<>();
        Item item = new Item();
        for (int i = 0; i< 10; i++){
            item.setName("item " + i);
            items.add(item);
        }
        return items;
    }
}
