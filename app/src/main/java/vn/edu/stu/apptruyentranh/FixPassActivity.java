package vn.edu.stu.apptruyentranh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class FixPassActivity extends AppCompatActivity {

    Button btnBack, btnComfirm;
    EditText edtOldPass, edtNewPass, edtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fix_pass);
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
        btnComfirm = findViewById(R.id.btnComfirm);
        edtOldPass = findViewById(R.id.edtOldPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
    }

    private void addEvents() {

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnComfirm.setOnClickListener(v -> {
            String oldPass = edtOldPass.getText().toString();
            String newPass = edtNewPass.getText().toString();
            String confirmPass = edtConfirmPass.getText().toString();

            // Băm mật khẩu cũ để so sánh
            String hashedOldPass = hashPassword(oldPass);

            if (hashedOldPass == null || !hashedOldPass.equals(NguoiDung.nowUser.getMatKhau())) {
                Toast.makeText(FixPassActivity.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPass.length() <= 5) {
                Toast.makeText(FixPassActivity.this, "Mật khẩu mới phải dài hơn 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(FixPassActivity.this, "Mật khẩu mới và mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Băm mật khẩu mới
            String hashedNewPass = hashPassword(newPass);
            if (hashedNewPass == null) {
                Toast.makeText(FixPassActivity.this, "Đã xảy ra lỗi khi băm mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật mật khẩu mới cho người dùng
            NguoiDung.nowUser.setMatKhau(hashedNewPass);

            // Gửi yêu cầu POST để lưu thông tin người dùng
            APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
            apiService.saveUser(NguoiDung.nowUser).enqueue(new Callback<NguoiDung>() {
                @Override
                public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(FixPassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(FixPassActivity.this, "Đổi mật khẩu thất bại: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NguoiDung> call, Throwable t) {
                    Toast.makeText(FixPassActivity.this, "Đổi mật khẩu thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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

}