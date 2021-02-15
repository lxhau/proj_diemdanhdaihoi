package com.example.appdiemdanhdh_hsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appdiemdanhdh_hsv.Model.ObjectWrapperForBinder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    private ProgressDialog mprogeres;
    private DatabaseReference mUsersDatabase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference uRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);
        AnhXa();
        firebaseDatabase=FirebaseDatabase.getInstance();
        uRef=firebaseDatabase.getReference("DAIBIEU");

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    private void AnhXa() {
        mprogeres = new ProgressDialog(this);
        scannerView=new ZXingScannerView(this);
        scannerView.setSoundEffectsEnabled(true);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(final Result rawResult) {
        if(rawResult!=null) {
           mprogeres.setTitle("Đang Xử Lý");
           mprogeres.setMessage("Đường truyền không ổn định.");
           mprogeres.setCanceledOnTouchOutside(false);
           mprogeres.show();
                Query userQuery=uRef.orderByChild("id").equalTo(rawResult.toString());
                userQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            startActivity(new Intent(getApplicationContext(),Match.class).putExtra("ID_DB",rawResult.toString()));
                        }else
                            startActivity(new Intent(getApplicationContext(), NotMatch.class));
                            mprogeres.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }else{

        }
    }
}
