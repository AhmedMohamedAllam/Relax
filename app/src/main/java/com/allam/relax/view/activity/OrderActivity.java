package com.allam.relax.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.controller.FirebaseOrderController;
import com.allam.relax.controller.interfaces.OnCompleteListener;
import com.allam.relax.model.Item;
import com.allam.relax.model.Order;
import com.allam.relax.view.ItemDetailDialog;
import com.allam.relax.view.NavigationDrawer;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private ArrayList<Item> mOrderItems = new ArrayList<>();
    private OrderAdapter mOrderAdapter;
    private static boolean isEditingItem = false;
    private OnCompleteListener<Item> mItemDialogCompleteListener;
    private static int mSelectedItemIndex;
    private FirebaseOrderController mOrderController = new FirebaseOrderController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.order_activity);
        setSupportActionBar(toolbar);

        Button submitButton = findViewById(R.id.submit_order_button);

        NavigationDrawer.getDrawer(this, toolbar, NavigationDrawer.newOrderID);

        // test recyclerview
        RecyclerView recyclerView = findViewById(R.id.orders_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mOrderAdapter = new OrderAdapter(mOrderItems);
        recyclerView.setAdapter(mOrderAdapter);


        mItemDialogCompleteListener = new OnCompleteListener<Item>() {
            @Override
            public void OnComplete(Item obj, String error) {
               if (isEditingItem){
                   mOrderItems.remove(mSelectedItemIndex);
                   mOrderItems.add(mSelectedItemIndex,obj);
                   isEditingItem = false;
               }else{
                   mOrderItems.add(obj);
               }
                mOrderAdapter.notifyDataSetChanged();
            }
        };

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOrderItems.isEmpty()){
                    Toast.makeText(OrderActivity.this, R.string.add_item_first, Toast.LENGTH_SHORT).show();
                    return;
                }
                Order currentOrder = new Order(mOrderItems);
                mOrderController.uploadOrderToFirebase(currentOrder, new OnCompleteListener<Order>() {
                    @Override
                    public void OnComplete(Order obj, String error) {
                        if(error != null){
                            Toast.makeText(OrderActivity.this,error , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(OrderActivity.this,"order added successfully" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mItemNameTextView;
        private Item mCurrentItem;
        private int mPosition;

        OrderHolder(View itemView) {
            super(itemView);
            mItemNameTextView = itemView.findViewById(android.R.id.text1);
            mItemNameTextView.setOnClickListener(this);
        }

        void onBindView(Item item, int position){
            mItemNameTextView.setText(item.getName());
            mCurrentItem = item;
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            mSelectedItemIndex = mPosition;
            isEditingItem = true;
            ItemDetailDialog itemDetailDialog = ItemDetailDialog.getInstance(mCurrentItem);
            itemDetailDialog.mCompleteListener = mItemDialogCompleteListener;
            itemDetailDialog.show(getFragmentManager(),null);
        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
        private ArrayList<Item> mItems;
        OrderAdapter(ArrayList<Item> items) {
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
            holder.onBindView(mItems.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_order_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_order_menu_button: {
                isEditingItem = false;
                ItemDetailDialog dialog = new ItemDetailDialog();
                dialog.mCompleteListener = mItemDialogCompleteListener;
                dialog.show(getFragmentManager(), "");
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
