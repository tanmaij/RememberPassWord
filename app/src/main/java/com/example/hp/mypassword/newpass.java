package com.example.hp.mypassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newpass extends AppCompatActivity {
    Button back,sub;
    EditText txtname,txtpas;
    Button show;
    boolean t=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);
        show=(Button)findViewById(R.id.btnshowp);
        back=(Button)findViewById(R.id.btnback);
        sub=(Button)findViewById(R.id.btnsub);
        txtname=(EditText)findViewById(R.id.txttenpass);
        txtpas=(EditText)findViewById(R.id.txtpass);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtpas.setTransformationMethod(new PasswordTransformationMethod());
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t==false)
                    ShowPassWord();
                else HidePassWord();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((txtname.getText()+"").length() ==0 || (txtpas.getText()+"").length()==0){
                    report("Vui lòng nhập thông tin hợp lệ");
                }else {

                    MainActivity.database.Query("Insert into PassWord Values (null,'"+txtname.getText()+"','"+txtpas.getText()+"')");
                    Pass.reload();
                    finish();
                }
            }
        });


    }
    void report(String k)
    {
        Toast.makeText(this, k, Toast.LENGTH_SHORT).show();
    }
    void ShowPassWord()
    {
        show.setText("Ẩn mật khẩu");
        txtpas.setTransformationMethod(null);

        t=true;
    }
    void  HidePassWord()
    {
        show.setText("Hiện mật khẩu");
        try {
            txtpas.setTransformationMethod(new PasswordTransformationMethod());

            t=false;
        } catch (Exception e) {

        }
    }
}
