package com.example.hp.mypassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateMyPassWord extends AppCompatActivity {
    Button show,exit;Button sub;EditText txtpassw;EditText txtsubpass;
    Activity o=this;
    boolean trueshow=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_pass_word);
        show=(Button)findViewById(R.id.btnshow);
        txtpassw=(EditText)findViewById(R.id.txtnewPass);
        txtsubpass=(EditText)findViewById(R.id.txtSubmitPass);
        sub=(Button)findViewById(R.id.btnsubmit);
        exit=(Button)findViewById(R.id.Exit);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trueshow==false)
                ShowPassWord();
                else {
                    HidePassWord();
                }
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((txtpassw.getText()+"").length()<8 || (txtpassw.getText()+"").length()>24)
                {
                    report("Mật khẩu tối thiểu 8 ký tự,tối đa 24 ký tự");
                }
                else if(String.valueOf(txtpassw.getText()).equals(String.valueOf(txtsubpass.getText())))

                {
                    MainActivity.database.Query("INSERT INTO AppPass VALUES ('MYPASS','" + txtsubpass.getText() + "" + "')");
                    AlertDialog.Builder b = new AlertDialog.Builder(o);

                    b.setTitle("Thành công");
                    b.setMessage("Khởi tạo mật khẩu thành công");

                    b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });



                    AlertDialog al = b.create();

                    al.show();

                }
                else {

                    report("Mật khẩu xác nhận không đúng");
                }
            }
        });
    }
    void report(String k)
    {
        Toast.makeText(this,k,Toast.LENGTH_SHORT).show();
    }
    void ShowPassWord()
    {
        show.setText("Ẩn mật khẩu");
        txtpassw.setTransformationMethod(null);
        txtsubpass.setTransformationMethod(null);
        trueshow=true;
    }
    void  HidePassWord()
    {
        show.setText("Hiện mật khẩu");
        try {
            txtpassw.setTransformationMethod(new PasswordTransformationMethod());
            txtsubpass.setTransformationMethod(new PasswordTransformationMethod());
            trueshow=false;
        } catch (Exception e) {

        }
    }
}
