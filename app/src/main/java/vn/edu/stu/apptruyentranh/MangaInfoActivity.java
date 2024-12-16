package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;
import vn.edu.stu.apptruyentranh.fragments.MangaInfoFragment;

public class MangaInfoActivity extends AppCompatActivity {

    private ImageView imgCover; // Hiển thị ảnh bìa truyện
    private TextView tvTitle, tvStatus, tvAuthor, tvTag, tvDescribe;
    private Button btnRead, btnChapter, btnBack;
    private int truyenTranhId; // ID của truyện tranh
    private List<String> imageUrls = new ArrayList<>(); // Danh sách chứa URL ảnh
    private TruyenTranh truyenTranh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manga_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();

        int truyenTranhId = getIntent().getIntExtra("truyenTranhId", -1);
        if (truyenTranhId != -1) {
            fetchTruyenTranh(truyenTranhId);
            MangaInfoFragment fragment = MangaInfoFragment.newInstance(truyenTranhId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            Log.e("MangaInfoActivity", "No truyenTranhId provided");
        }
    }

    private void addControls() {
        imgCover = findViewById(R.id.img_Cover);
        tvTitle = findViewById(R.id.tvTitle);
        tvStatus = findViewById(R.id.tvStatus);
        btnBack = findViewById(R.id.btnBack);
        btnRead = findViewById(R.id.btnRead);
    }

    private void addEvents() {
        btnBack.setOnClickListener(v -> finish());
        btnRead.setOnClickListener(v -> {
            Intent intent = new Intent(MangaInfoActivity.this, ReadActivity.class);
            intent.putExtra("truyenTranhId", truyenTranhId);
            startActivity(intent);
        });            // Xử lý sự kiện khi nhấn nút Đọc từ đầu

    }

    private void fetchTruyenTranh(int id) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<TruyenTranh> call = apiService.getComicById(id);
        call.enqueue(new Callback<TruyenTranh>() {
            @Override
            public void onResponse(Call<TruyenTranh> call, Response<TruyenTranh> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TruyenTranh truyenTranh = response.body();
                    updateUI(truyenTranh); // Cập nhật giao diện với thông tin truyện
                    // Lấy các URL ảnh bìa nếu có
                    if (truyenTranh.getAnhBia() != null) {
                        String imageUrl = "https://res.cloudinary.com/dsyeobmcl/image/upload/AnhBia/" + truyenTranh.getAnhBia();
                        Glide.with(MangaInfoActivity.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)
                                .into(imgCover);
                    }
                } else {
                    Log.e("MangaInfoActivity", "Kết quả không thành công: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TruyenTranh> call, Throwable t) {
                Log.e("MangaInfoActivity", "Lỗi khi lấy dữ liệu", t);
            }
        });
    }

    // Cập nhật giao diện với thông tin truyện
    private void updateUI(TruyenTranh truyenTranh) {
        tvTitle.setText(truyenTranh.getTenTruyen());
        tvStatus.setText(truyenTranh.getTrangThai());


    }

}