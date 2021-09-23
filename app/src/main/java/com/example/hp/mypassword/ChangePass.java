package com.example.hp.mypassword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePass extends AppCompatActivity {
    EditText txtpassw;EditText txtsubpass; EditText old;Button sub; Button show;Button back;
    boolean trueshow=false;
    ChangePass o=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        show=(Button)findViewById(R.id.btnshowpass);
        old=(EditText)findViewById(R.id.txtoldpass);
        txtpassw=(EditText)findViewById(R.id.txtnewpass);
        txtsubpass=(EditText)findViewById(R.id.txtsubmitpass);
        sub=(Button)findViewById(R.id.btnsubmitpass);
        back=(Button)findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueshow==true)
                    HidePassWord();
                else ShowPassWord();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pass="";

                Cursor getAppPass=MainActivity.database.getData("select * from AppPass");
                while (getAppPass.moveToNext())
                {
                    Pass=getAppPass.getString(1);
                }
                if(!Pass.equals(String.valueOf(old.getText()))){

                    report("Mật khẩu cũ không đúng");
                }
                else if ((txtpassw.getText()+"").length()<8 || (txtpassw.getText()+"").length()>24)
                {
                    report("Mật khẩu tối thiểu 8 ký tự,tối đa 24 ký tự");
                }
                else if(!String.valueOf(txtpassw.getText()).equals(String.valueOf(txtsubpass.getText())))
                {
                    report("Mật khẩu xác nhận không đúng");
                }
                else {

                    MainActivity.database.Query("Update AppPass set password='"+txtpassw.getText()+""+"' where id='MYPASS'");
                    AlertDialog.Builder b = new AlertDialog.Builder(o);

                    b.setTitle("Thành công");
                    b.setMessage("Đổi mật khẩu thành công");

                    b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });



                    AlertDialog al = b.create();

                    al.show();
                }
            }
        });
    }
    void report(String k)
    {
        Toast.makeText(this, k, Toast.LENGTH_SHORT).show();

    }
    void HidePassWord()
    {
        show.setText("Hiện mật khẩu");
        old.setTransformationMethod(new PasswordTransformationMethod());
        txtpassw.setTransformationMethod(new PasswordTransformationMethod());
        txtsubpass.setTransformationMethod(new PasswordTransformationMethod());
        trueshow=false;
    }
    void  ShowPassWord()
    {
        show.setText("Ẩn mật khẩu");
        try {
            txtsubpass.setTransformationMethod(null);
            old.setTransformationMethod(null);
            txtpassw.setTransformationMethod(null);
            trueshow=true;
        } catch (Exception e) {

        }
    }
}
