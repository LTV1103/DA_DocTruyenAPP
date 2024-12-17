package vn.edu.stu.apptruyentranh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.adapter.ReadAdapter;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.DanhDauTrang;
import vn.edu.stu.apptruyentranh.api.reponse.NguoiDung;

public class ReadActivity extends AppCompatActivity {
    Button btn_Back;
    ImageButton btn_tym;
    int chuongtruyenId;
    private ListView lvRead;
    TextView tvTitle;
    String chuongTitle;
    private ReadAdapter adapter;
    private List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addCotrols();
        addEvents();

        chuongtruyenId = getIntent().getIntExtra("chuongtruyenId", -1);
        chuongTitle = getIntent().getStringExtra("chuongTitle");
        if (chuongTitle != null) {
            tvTitle.setText(chuongTitle);
        }
        if (chuongtruyenId != -1) {
            fetchChapterImages(chuongtruyenId);
        }
    }

    private void addEvents() {
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_tym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBookmark(chuongtruyenId); // Gọi hàm lưu dấu trang khi nhấn nút "tym"
            }
        });
    }

    private void addCotrols() {
        tvTitle = findViewById(R.id.tvTitle); // Initialize the TextView once here
        btn_Back = findViewById(R.id.btn_Back);
        btn_tym = findViewById(R.id.btn_tym);
        lvRead = findViewById(R.id.lvRead);
        adapter = new ReadAdapter(this, imageUrls);
        lvRead.setAdapter(adapter);
    }

    private void saveBookmark(int chuongtruyenId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int nguoiDungId = sharedPreferences.getInt("nguoiDungId", -1);  // Sử dụng -1 làm giá trị mặc định

        if (nguoiDungId == -1) {
            Log.e("ReadActivity", "User is not logged in");
            Toast.makeText(this, "Vui lòng đăng nhập để lưu dấu trang", Toast.LENGTH_SHORT).show();
            return;
        }

        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<DanhDauTrang> call = apiService.saveBookmark(String.valueOf(nguoiDungId), String.valueOf(chuongtruyenId));

        call.enqueue(new Callback<DanhDauTrang>() {
            @Override
            public void onResponse(Call<DanhDauTrang> call, Response<DanhDauTrang> response) {
                if (response.isSuccessful()) {
                    Log.d("ReadActivity", "Bookmark saved successfully");
                    Toast.makeText(ReadActivity.this, "Đã lưu dấu trang", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("ReadActivity", "Failed to save bookmark: " + response.message());
                    Toast.makeText(ReadActivity.this, "Lưu dấu trang thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DanhDauTrang> call, Throwable t) {
                Log.e("ReadActivity", "Error saving bookmark", t);
                Toast.makeText(ReadActivity.this, "Lỗi khi lưu dấu trang", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void fetchChapterImages(int id) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<String>> call = apiService.getChapterImages(id);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> imageUrls = response.body();
                    // Call getImageUrl to process each image URL if needed
                    fetchImageUrls(imageUrls);
                } else {
                    Log.e("ReadActivity", "Response unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ReadActivity", "Error fetching data", t);
            }
        });
    }

    private void fetchImageUrls(List<String> imageUrlsList) {
        for (String imagePath : imageUrlsList) {
            String fullImageUrl = "https://res.cloudinary.com/dsyeobmcl/image/upload/AnhBia/" + imagePath;
            updateImageList(fullImageUrl);  // Cập nhật danh sách ảnh
        }
    }

    private void updateImageList(String newImageUrl) {
        imageUrls.add(newImageUrl);
        adapter.notifyDataSetChanged();
    }
}
