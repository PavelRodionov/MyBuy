package com.example.pavel.rodionov.mybuy;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pavel.rodionov.mybuy.controller.DBHandler;
import com.example.pavel.rodionov.mybuy.model.Buy;
import com.example.pavel.rodionov.mybuy.view.adapters.ListViewAdapter;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private AlertDialog.Builder builder;
    private AlertDialog adialog;
    private List<Buy> goods;

    private ListView listView;
    private ListViewAdapter listAdapter;


    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDBHandler();
        initListView();
        initDialog();
    }

    private void initDBHandler() {
        dbHandler = new DBHandler(this);
    }



    private void initDialog(){
        View view  = View.inflate(this,R.layout.layout_for_dialog,null);
        text = (TextView)view.findViewById(R.id.edit_text);
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setView(view);
        builder.setNegativeButton(R.string.dialog_button_cancel, null);
        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHandler.getInstance().addBuy(new Buy(text.getText().toString()));
            }
        });
        adialog = builder.create();
    }



    private void initListView(){
        listView = (ListView) findViewById(R.id.list_view);

        goods = DBHandler.getInstance().getAllBuys();

        listAdapter = new ListViewAdapter(goods);
        listView.setAdapter(listAdapter);
        DBHandler.getInstance().setAdapter(listAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_new_list:
                adialog.show();
                break;
            case R.id.menu_delete_list:
                DBHandler.getInstance().deleteTable();
                break;
        }

        return true;
    }
}
