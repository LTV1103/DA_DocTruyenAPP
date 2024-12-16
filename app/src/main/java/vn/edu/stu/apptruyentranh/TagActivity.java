package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.adapter.TheLoaiAdapter;
import vn.edu.stu.apptruyentranh.adapter.TruyenTranhAdapter;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public class TagActivity extends AppCompatActivity {

    private GridView gvTag, gvTruyen;
    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;
    private ImageButton imgSearch;
    private List<String> categories;
    TextView theLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        fetchCategories();
    }

    private void addControls() {
        gvTag = findViewById(R.id.gvTag);
        btnHome = findViewById(R.id.btnHome);
        theLoai = findViewById(R.id.theLoai);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
        imgSearch = findViewById(R.id.img_search);
        gvTruyen = findViewById(R.id.gvTruyen);
    }

    private void addEvents() {
        gvTag.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories.get(position);
            theLoai.setText("Thể loại: " + selectedCategory);
            fetchTruyenByCategory(selectedCategory);
        });

        imgSearch.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btnTag.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, TagActivity.class);
            startActivity(intent);
        });
        btnShoppingList.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, BougthActivity.class);
            startActivity(intent);
        });
        btnBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });
        btnUser.setOnClickListener(v -> {
            Intent intent = new Intent(TagActivity.this, UserActivity.class);
            startActivity(intent);
        });
    }

    private void fetchCategories() {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<String>> call = apiService.getAllCategories();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body(); // Gán danh sách thể loại vào biến toàn cục
                    TheLoaiAdapter adapter = new TheLoaiAdapter(TagActivity.this, categories);
                    gvTag.setAdapter(adapter);

                    // In log để kiểm tra xem categories có được lấy đúng
                    System.out.println("Categories fetched: " + categories);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTruyenByCategory(String category) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<TruyenTranh>> call = apiService.searchTruyenByCategory(category);

        call.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TruyenTranh> truyenList = response.body();
                    TruyenTranhAdapter adapter = new TruyenTranhAdapter(TagActivity.this, truyenList);
                    gvTruyen.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TruyenTranh>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
