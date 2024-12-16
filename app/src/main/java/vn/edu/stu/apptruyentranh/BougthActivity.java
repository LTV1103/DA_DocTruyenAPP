package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.NguoiDung;

public class BougthActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bougth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        btnHome = findViewById(R.id.btnHome);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
    }

    private void addEvents() {
        btnHome.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, MainActivity.class);
            startActivity(intent); });
        btnTag.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, TagActivity.class);
            startActivity(intent); });
        btnShoppingList.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, BougthActivity.class);
            startActivity(intent); });
        btnBookmark.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, BookmarkActivity.class);
            startActivity(intent); });
        btnUser.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, UserActivity.class);
            startActivity(intent); });
    }

    public static class LoginActivity extends AppCompatActivity {

        EditText edtUsername, edtPassword;
        Button btnBack, btnLogin, btnRegister;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_login);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            addControls();
            addEvents();
        }

        private void addControls() {

            edtUsername = findViewById(R.id.edtUsername);
            edtPassword = findViewById(R.id.edtPassword);

            btnBack = findViewById(R.id.btnBack);
            btnLogin = findViewById(R.id.btnLogin);
            btnRegister = findViewById(R.id.btnRegister);
        }

        private void addEvents() {

            btnBack.setOnClickListener(v ->{
                finish(); });

            btnRegister.setOnClickListener(v ->{
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent); });

            btnLogin.setOnClickListener(v -> loginUser());
        }

        private void loginUser() {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!username.matches("[a-zA-Z0-9@.]*")) {
                Toast.makeText(this, "Tên đăng nhập không được có dấu hoặc ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return;
            }

            APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);

            apiService.getUserByTenDangNhap(username).enqueue(new Callback<NguoiDung>() {
                @Override
                public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String hashedPassword = hashPassword(password);
                            if (hashedPassword.equals(response.body().getMatKhau())) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                NguoiDung.nowUser = response.body();
                                saveUserToPreferences(NguoiDung.nowUser);
                                setResult(RESULT_OK);
                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<NguoiDung> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void saveUserToPreferences(NguoiDung user) {
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("nguoiDungId", user.getNguoiDungId());
            editor.putString("tenDangNhap", user.getTenDangNhap());
            editor.putString("email", user.getEmail());
            editor.putString("matKhau", user.getMatKhau());
            editor.putString("soDu", user.getSoDu().toString());
            editor.apply();
        }
    }
}