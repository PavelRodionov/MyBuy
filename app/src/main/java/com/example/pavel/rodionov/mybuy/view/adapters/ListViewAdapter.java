package com.example.pavel.rodionov.mybuy.view.adapters;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pavel.rodionov.mybuy.R;
import com.example.pavel.rodionov.mybuy.controller.DBHandler;
import com.example.pavel.rodionov.mybuy.model.Buy;
import com.example.pavel.rodionov.mybuy.view.common.NotifyChangedInterface;

import java.util.List;

public class ListViewAdapter extends BaseAdapter implements NotifyChangedInterface{
    private List<Buy> goods;

    private AlertDialog.Builder builder;
    private AlertDialog adialog;

    public ListViewAdapter(List<Buy> goods){
        this.goods = goods;
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return goods.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.layout_for_list_view,null);
        TextView text = (TextView) view.findViewById(R.id.recycler_text_for_buys);
        text.setText(goods.get(position).getGoods());
        ImageView imageDelete = (ImageView) view.findViewById(R.id.item_delete);
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler.getInstance().deleteBuy(goods.get(position));
            }
        });
        ImageView imageEdit = (ImageView) view.findViewById(R.id.item_edit);
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view  = View.inflate(parent.getContext(),R.layout.layout_for_dialog,null);
                final TextView text; text = (TextView)view.findViewById(R.id.edit_text);
                builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle(R.string.dialog_title);
                builder.setView(view);
                builder.setNegativeButton(R.string.dialog_button_cancel, null);
                builder.setPositiveButton(R.string.dialog_button_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHandler.getInstance().updateBuy(goods.get(position),text.getText().toString());
                    }
                });
                adialog = builder.create();
                adialog.show();
            }
        });
        return view;
    }

    @Override
    public void changedData() {
        goods.clear();
        goods = DBHandler.getInstance().getAllBuys();

        for(Buy buy:goods) Log.d("Pavel","ListViewAdapter: " + buy.toString());

        notifyDataSetChanged();
    }
}
