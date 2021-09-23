package com.example.hp.mypassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Database database;
    Intent CreatePass;
    Button sm;EditText p;
    Button show;
    boolean trueshow=false;
    Intent intentuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm=(Button)findViewById(R.id.btnSubmit);
        p=(EditText)findViewById(R.id.txtpassapp);
        show=(Button)findViewById(R.id.ongnoimay);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueshow==false)
                    ShowPassWord();
                else HidePassWord();
            }
        });
        intentuse=new Intent(this,Pass.class);
        CreateDataBase();
        CheckMyPass();
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pass="";

                Cursor getAppPass=database.getData("select * from AppPass");
                while (getAppPass.moveToNext())
                {
                    Pass=getAppPass.getString(1);
                }
                if(Pass.equals(p.getText()+"")==false){
                    report("Mật khẩu không đúng");
                }
                else {
                    startActivity(intentuse);
                }
            }
        });

    }
    void report(String k)
    {
        Toast.makeText(this, k, Toast.LENGTH_SHORT).show();
    }
    private void CreateDataBase()
    {
        database = new Database(this,"pass.sqlite",null,1);
        database.Query("CREATE TABLE IF NOT EXISTS PassWord(id INTEGER PRIMARY KEY AUTOINCREMENT ,Name varchar(200), password varchar(200))");
        database.Query("CREATE TABLE IF NOT EXISTS AppPass(id varchar(20) PRIMARY KEY,password varchar(200))");
    }
    public void InsertData(String name,String password)
    {
        database.Query("INSERT INTO PassWord VALUES(null,'" + name + "','" + password + "')");
    }
    private void CheckMyPass()
    {
        String Pass="";
        Cursor getAppPass=database.getData("select * from AppPass");
        while (getAppPass.moveToNext())
        {
            Pass=getAppPass.getString(1);
        }
        if (Pass=="")
        {
            CreatePass=new Intent(this,CreateMyPassWord.class);
            startActivity(CreatePass);
        }else {

        }
    }
    void ShowPassWord()
    {
        show.setText("Ẩn mật khẩu");
        p.setTransformationMethod(null);

        trueshow=true;
    }
    void  HidePassWord()
    {
        show.setText("Hiện mật khẩu");
        try {

            p.setTransformationMethod(new PasswordTransformationMethod());
            trueshow=false;
        } catch (Exception e) {

        }
    }

}
