package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public class MangaInfoActivity extends AppCompatActivity {

    private ImageView imgCover;
    private TextView tvTitle, tvStatus, tvAuthor, tvTag, tvDescribe;
    private Button btnRead, btnChapter,btnback;
    private int truyenTranhId;

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

        truyenTranhId = getIntent().getIntExtra("truyenTranhId", -1);
        if (truyenTranhId != -1) {
            fetchTruyenTranh(truyenTranhId);
        } else {
            Log.e("MangaInfoActivity", "No truyenTranhId provided");
        }
    }

    private void addControls() {
        imgCover = findViewById(R.id.img_Cover);
        tvTitle = findViewById(R.id.tvTitle);
        tvStatus = findViewById(R.id.tvStatus);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvTag = findViewById(R.id.tvTag);
        btnback = findViewById(R.id.btn_Back);
        tvDescribe = findViewById(R.id.tvDescribe);
        btnRead = findViewById(R.id.btnRead);
        btnChapter = findViewById(R.id.btnChapter);
    }

    private void addEvents() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChapter.setOnClickListener(v -> {
            Intent intent = new Intent(MangaInfoActivity.this, ChapterListActivity.class);
            intent.putExtra("truyenTranhId", truyenTranhId);
            intent.putExtra("title", tvTitle.getText().toString());
            intent.putExtra("status", tvStatus.getText().toString());
            startActivity(intent);
        });
    }

    private void fetchTruyenTranh(int id) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<TruyenTranh> call = apiService.getComicById(id);

        call.enqueue(new Callback<TruyenTranh>() {
            @Override
            public void onResponse(Call<TruyenTranh> call, Response<TruyenTranh> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TruyenTranh truyenTranh = response.body();
                    updateUI(truyenTranh);
                } else {
                    Log.e("MangaInfoActivity", "Response unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TruyenTranh> call, Throwable t) {
                Log.e("MangaInfoActivity", "Error fetching data", t);
            }
        });
    }

    private void updateUI(TruyenTranh truyenTranh) {
        tvTitle.setText(truyenTranh.getTenTruyen());
        tvStatus.setText(truyenTranh.getTrangThai());
        tvAuthor.setText(truyenTranh.getTacGia());
        tvTag.setText(truyenTranh.getTheLoai());
        tvDescribe.setText(truyenTranh.getMoTa());

        String imageUrl = "http://your-image-base-url/" + truyenTranh.getAnhBia();
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCover);
    }
}
