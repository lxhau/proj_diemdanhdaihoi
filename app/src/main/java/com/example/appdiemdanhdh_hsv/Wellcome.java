package com.example.appdiemdanhdh_hsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Wellcome extends AppCompatActivity {

    private int CAMERA_PERMISSION_CODE = 1;
    private FirebaseAuth mAuth;
    Button btn_sign;
    EditText edt_mail,edt_pass;
    private ProgressDialog mprogeres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar)); //status bar or the time bar at the top
        }
        AnhXa();

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edt_mail.getText().toString();
                final String password =edt_pass.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Mật Khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mprogeres.setTitle("Đang Đăng Nhập");
                    mprogeres.setMessage("Đang Kết Nối Sever, Vui Lòng Chờ !");
                    mprogeres.setCanceledOnTouchOutside(false);
                    mprogeres.show();
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Wellcome.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        mprogeres.dismiss();
                                        sentToStar();
                                        Toast.makeText(getApplicationContext(),"Đăng Nhập Thành Công",Toast.LENGTH_SHORT).show();
                                    }else{
                                        mprogeres.hide();
                                        Toast.makeText(getApplicationContext(),"Thất Bại",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Cấp Phép Quyền Truy Cập")
                    .setMessage("Bạn cần cấp phép quyền truy cập này để ứng dụng để có thể hoạt động")
                    .setPositiveButton("Chấp Nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private void sentToStar(){
        startActivity(new Intent(this,Info_BKT.class).putExtra("MSSV",edt_mail.getText().toString())
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    private void AnhXa(){
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_sign=(Button) findViewById(R.id.btndn);
        edt_mail=(EditText) findViewById(R.id.edtusername);
        edt_pass=(EditText) findViewById(R.id.edtpwd);
        mprogeres = new ProgressDialog(this);
    }
}
