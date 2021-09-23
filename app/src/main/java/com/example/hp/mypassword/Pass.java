package com.example.hp.mypassword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Pass extends AppCompatActivity {
    ListView list;
    Button change;
    Button back;
    Button newp;
    public static ArrayList<PassWord> arrayListpass;
    public static ArrayList<String> arr;
    Pass s= this;
    Intent n;
    Intent hu;
    public static ArrayAdapter<String> adt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        list=(ListView)findViewById(R.id.listView);
        back=(Button)findViewById(R.id.btnback);
        newp=(Button)findViewById(R.id.btnaddpass);
        change=(Button)findViewById(R.id.btnchange);
        hu=new Intent(this,ChangePass.class);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(hu);
            }
        });
        n=new Intent(this,newpass.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(n);
            }
        });
        String Pass="";
        arrayListpass=new ArrayList();
        arr=new ArrayList();

        Cursor getPass=MainActivity.database.getData("select * from PassWord");
        while (getPass.moveToNext())
        {
            arrayListpass.add(new PassWord(getPass.getString(0),getPass.getString(1),getPass.getString(2)));
            arr.add(getPass.getString(1));
        }
        adt=new ArrayAdapter<String>(this,
        R.layout.support_simple_spinner_dropdown_item,
                arr
        );
        list.setAdapter(adt);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(s);

                b.setTitle("Mật khẩu của bạn là");
                b.setMessage(arrayListpass.get(position).getPassword());

                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


                AlertDialog al = b.create();

                al.show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(s);
                final int e=position;
                b.setTitle("Cảnh báo");
                b.setMessage("Bạn có thực sự muốn xóa mật khẩu " + arrayListpass.get(position).getName() + "?");

                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.database.Query("DELETE FROM PassWord where id='"+arrayListpass.get(e).getId()+"'");
                        reload();
                        dialog.cancel();
                    }
                });
                b.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


                AlertDialog al = b.create();

                al.show();
                return true;
            }
        });
    }
    public static void reload()
    {
        arrayListpass.clear();
        arr.clear();

        Cursor getPass=MainActivity.database.getData("select * from PassWord");
        while (getPass.moveToNext())
        {
            arrayListpass.add(new PassWord(getPass.getString(0),getPass.getString(1),getPass.getString(2)));
            arr.add(getPass.getString(1));
        }
        adt.notifyDataSetChanged();
    }

}
