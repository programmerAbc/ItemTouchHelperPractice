package com.practice.itemtouchhelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuyakun on 2017/8/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<String> data = new LinkedList<>(Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15"));
    ItemTouchHelper itemTouchHelper;

    public MyAdapter() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(data, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                data.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.bindItemTouchHelper(itemTouchHelper);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setupWithRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        String data;
        @BindView(R.id.itemTv)
        TextView itemTv;
        @BindView(R.id.itemIBtn)
        ImageButton itemIBtn;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            data = "";
        }

        public void bindData(String s) {
            data = s;
            itemTv.setText(data);
        }

        public void bindItemTouchHelper(final ItemTouchHelper itemTouchHelper) {
            itemIBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper.startDrag(MyViewHolder.this);
                    }
                    return false;
                }
            });
        }
    }
}
