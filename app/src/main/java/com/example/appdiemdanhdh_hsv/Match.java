package com.example.appdiemdanhdh_hsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdiemdanhdh_hsv.Model.ObjectWrapperForBinder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Match extends AppCompatActivity {

    private DatabaseReference mUsersDatabase,mUsersDatabaseDB;
    private ProgressDialog mprogress;
    DatabaseReference uRef, TDRef;
    FirebaseDatabase firebaseDatabase;
    private TextView txtname, txtdonvi, txtngaysinh,txtdantoc,txtgioitinh,txttongiao;
    private CircleImageView imgavt;
    private Button btnxacnhan;
    private String image,name,id,ngaysinh,dantoc,gioitinh,tongiao,loaidb,donvi,doanvien;
    private String CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar)); //status bar or the time bar at the top
        }
        AnhXa();

        CHECK="0";
        mUsersDatabase = getInstance().getReference().child("THAMDU").child(id);
        mUsersDatabaseDB = getInstance().getReference().child("DAIBIEU").child(id);

        Query userQuery=uRef.orderByChild("id").equalTo(id);

        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    /// Get data ve RAM
                    image = ds.child("image").getValue().toString();
                    CHECK = ds.child("check").getValue().toString();
                    name= ds.child("name").getValue().toString();
                    dantoc= ds.child("dantoc").getValue().toString();
                    donvi=ds.child("donvi").getValue().toString();
                    gioitinh=ds.child("gioitinh").getValue().toString();
                    loaidb=ds.child("loaidb").getValue().toString();
                    ngaysinh=ds.child("ngaysinh").getValue().toString();
                    tongiao=ds.child("tongiao").getValue().toString();
                    doanvien=ds.child("doanvien").getValue().toString();
                    /*
                    Check thong tin tren RAM
                     */
                    if(CHECK.equals("1")){
                        CHECK="1";
                        btnxacnhan.setEnabled(false);
                        btnxacnhan.setText("ĐÃ ĐIỂM DANH");
                    }

                    /// Gan DATA len View
                        txtname.setText(name.toString());
                        txtdonvi.setText(donvi.toString());
                        txtngaysinh.setText(ngaysinh.toString());
                        txttongiao.setText(tongiao.toString());
                        txtdantoc.setText(dantoc.toString());
                        if(gioitinh.equals("0"))
                            txtgioitinh.setText("Nữ");
                        else
                            txtgioitinh.setText("Nam");
                        Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.default_avatar)
                            .into(imgavt, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(image).placeholder(R.drawable.default_avatar)
                                            .into(imgavt);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mprogress.dismiss();

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mprogress.show();
                    mUsersDatabaseDB.child("check").setValue("1");
                    CHECK="1";
                    mUsersDatabase.child("dantoc").setValue(dantoc);
                    mUsersDatabase.child("donvi").setValue(donvi);
                    mUsersDatabase.child("gioitinh").setValue(gioitinh);
                    mUsersDatabase.child("id").setValue(id);
                    mUsersDatabase.child("image").setValue(image);
                    mUsersDatabase.child("name").setValue(name);
                    mUsersDatabase.child("ngaysinh").setValue(ngaysinh);
                    mUsersDatabase.child("tongiao").setValue(tongiao);
                    mUsersDatabase.child("loaidb").setValue(loaidb);
                    mUsersDatabase.child("doanvien").setValue(doanvien);
                    mUsersDatabase.child("check").setValue("1");

                    mprogress.dismiss();
                    startActivity(new Intent(getApplicationContext(), ScanQR.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Lỗi Mạng",Toast.LENGTH_SHORT).show();
                    mprogress.hide();
                }
            }
        });


    }

    private void AnhXa() {
        mprogress=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        uRef=firebaseDatabase.getReference("DAIBIEU");
        TDRef=firebaseDatabase.getReference("THAMDU");

        Intent intent=getIntent();
        id=intent.getStringExtra("ID_DB");

        txtname=(TextView) findViewById(R.id.tenDaiBieu);
        txtdonvi=(TextView) findViewById(R.id.donViDaiBieu);
        txtngaysinh=(TextView) findViewById(R.id.txtngaysinh);
        txtdantoc=(TextView) findViewById(R.id.txtdantoc);
        txtgioitinh=(TextView) findViewById(R.id.txtgioitinh);
        txttongiao=(TextView) findViewById(R.id.txttongiao);

        btnxacnhan=(Button) findViewById(R.id.btn_xacnhan);
        imgavt=(CircleImageView) findViewById(R.id.avatardb);


    }
}
