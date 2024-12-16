package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.stu.apptruyentranh.api.reponse.NguoiDung;

public class UserActivity extends AppCompatActivity {

    private TextView tvID, tvUsername, tvEmail, tvSodu;
    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;
    Button btnLogin, btnFixpass, btnLogout;

    private static final int LOGIN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        reloadData();
    }

    private void addControls() {

        tvID = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvSodu = findViewById(R.id.tvSodu);

        btnHome = findViewById(R.id.btnHome);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
        btnLogin = findViewById(R.id.btnLogin);
        btnFixpass = findViewById(R.id.btnFixpass);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void addEvents() {

        btnLogin.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE); });

        btnLogout.setOnClickListener(v -> {
            if (NguoiDung.nowUser == null) {
                Toast.makeText(UserActivity.this, "Bạn cần đăng nhập trước!", Toast.LENGTH_SHORT).show();
            } else {
                NguoiDung.nowUser = null;
                clearUserPreferences();
                reloadData();
            }
        });

        btnFixpass.setOnClickListener(v -> {
            if (NguoiDung.nowUser == null) {
                Toast.makeText(UserActivity.this, "Bạn cần đăng nhập trước!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(UserActivity.this, FixPassActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            startActivity(intent); });
        btnTag.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, TagActivity.class);
            startActivity(intent); });
        btnShoppingList.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, BougthActivity.class);
            startActivity(intent); });
        btnBookmark.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, BookmarkActivity.class);
            startActivity(intent); });
        btnUser.setOnClickListener(v ->{
            Intent intent = new Intent(UserActivity.this, UserActivity.class);
            startActivity(intent); });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                reloadData();
            }
        }
    }

    private void reloadData() {
        if (NguoiDung.nowUser != null) {
            tvID.setText(String.valueOf(NguoiDung.nowUser.getNguoiDungId()));
            tvUsername.setText(NguoiDung.nowUser.getTenDangNhap());
            tvEmail.setText(NguoiDung.nowUser.getEmail());
            tvSodu.setText(String.valueOf(NguoiDung.nowUser.getSoDu()));
        } else {
            tvID.setText("");
            tvUsername.setText("");
            tvEmail.setText("");
            tvSodu.setText("");
        }
    }


    private void clearUserPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}