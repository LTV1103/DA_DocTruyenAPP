package vn.edu.stu.apptruyentranh;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
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
import vn.edu.stu.apptruyentranh.adapter.ReadAdapter;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;

public class ReadActivity extends AppCompatActivity {
    Button btn_Back, btn_tym;
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
    }

    private void addCotrols() {
        tvTitle = findViewById(R.id.tvTitle); // Initialize the TextView once here
        btn_Back = findViewById(R.id.btn_Back);
        btn_tym = findViewById(R.id.btn_tym);
        lvRead = findViewById(R.id.lvRead);
        adapter = new ReadAdapter(this, imageUrls);
        lvRead.setAdapter(adapter);
    }

    private void fetchChapterImages(int id) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<String>> call = apiService.getChapterImages(id);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> imageUrls = response.body();
                    updateImageList(imageUrls);
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

    private void updateImageList(List<String> newImageUrls) {
        imageUrls.clear();
        imageUrls.addAll(newImageUrls);
        adapter.notifyDataSetChanged();
    }
}
