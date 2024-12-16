package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.NguoiDung;

public class RegisterActivity extends AppCompatActivity {

    Button btnBack, btnRegister;
    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPass);
    }

    private void addEvents() {
        btnRegister.setOnClickListener(v -> registerUser());
        btnBack.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() <= 5) {
            Toast.makeText(this, "Mật khẩu phải dài hơn 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!username.matches("[a-zA-Z0-9@.]*")) {
            Toast.makeText(this, "Tên đăng nhập không được có dấu hoặc ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return;
            }
        if (!email.matches("[a-zA-Z0-9@.]*")) {
            Toast.makeText(this, "Email không được có dấu hoặc ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return;
        }

        String hashedPassword = hashPassword(password);

        // Tạo người dùng mới
        NguoiDung newUser = new NguoiDung();
        newUser.setTenDangNhap(username);
        newUser.setEmail(email);
        newUser.setMatKhau(hashedPassword);
        newUser.setSoDu(BigDecimal.ZERO);

        // Đăng ký người dùng mới
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        apiService.createUser(newUser).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    NguoiDung.nowUser = response.body();
                    saveUserToPreferences(NguoiDung.nowUser);
                    setResult(RESULT_OK);
                    Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                    startActivity(intent);
                } else if (response.code() == 409 || response.code() == 500) {
                    Toast.makeText(RegisterActivity.this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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