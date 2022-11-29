package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody.Model.Nguoidung;
import com.example.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity {

    EditText ed_tenNguoiDung , ed_tenDangNhap ,  ed_email , ed_matKhau ;
    TextView tv_Clickme ;
    Button btndangKy;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        myRef = FirebaseDatabase.getInstance().getReference();
        ed_tenDangNhap = this.<EditText>findViewById(R.id.ed_Name);
        mAuth = FirebaseAuth.getInstance();
        ed_tenNguoiDung = this.<EditText>findViewById(R.id.ed_tendangnhap);
        ed_email = this.<EditText>findViewById(R.id.ed_email);
        ed_matKhau = this.<EditText>findViewById(R.id.ed_MatKhau);
        btndangKy = this.<Button>findViewById(R.id.btn_dangky);
        tv_Clickme  = this.<TextView>findViewById(R.id.clickvaotui);

        tv_Clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btndangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(!ed_tenDangNhap.getText().equals("") || !ed_tenNguoiDung.getText().equals("") ||
//                !ed_email.getText().equals("") || !ed_matKhau.getText().equals("")  )
//                {
//                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }else  if(
//                        ed_matKhau.getText().length() > 0){
                    String email = ed_email.getText().toString();
                    String matKhau = ed_matKhau.getText().toString();

                    mAuth.createUserWithEmailAndPassword(email,matKhau)
                            .addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công , Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();

                                        Nguoidung nguoidung = new Nguoidung(ed_tenNguoiDung.getText().toString()
                                                ,ed_tenDangNhap.getText().toString(),ed_email.getText().toString(),ed_matKhau.getText().toString());
                                        myRef.child("nguoidung").push().setValue(nguoidung);

                                        Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
            }
        });

    }
}