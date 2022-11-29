package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody.Model.Nguoidung;
import com.example.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapActivity extends AppCompatActivity {

    EditText ed_Email , ed_matKhau ;
    Button btnDangNhap ;
    private FirebaseAuth mAuth;
   public static SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap2);

        mAuth = FirebaseAuth.getInstance();
        ed_Email = this.<EditText>findViewById(R.id.ed_emailDangNhap);
        ed_matKhau = findViewById(R.id.ed_matKhauDangNhap);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_Email.getText().equals("") || ed_matKhau.getText().equals(""))
                {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập email ", Toast.LENGTH_SHORT).show();
                }else{
                    String email = ed_Email.getText().toString();
                    String matKhau = ed_matKhau.getText().toString();

                    mAuth.signInWithEmailAndPassword(email,matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DangNhapActivity.this,HomeActivity.class);
                                Nguoidung nguoidung = new Nguoidung(email,matKhau);
                                intent.putExtra("nguoidung",nguoidung);
                                startActivity(intent);

//                                sharedPreferences = getSharedPreferences("NguoiDung", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                                editor.putString("uid",nguoidung.getId().toString());
//                                editor.putString("name",nguoidung.getName().toString());


                            }

                        }
                    });
                }
            }
        });



    }
}