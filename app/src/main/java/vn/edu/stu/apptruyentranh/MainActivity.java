package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.adapter.TruyenTranhAdapter;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;
    private ImageButton imgSearch;
    private ImageView bannerImage;
    private GridView gvTruyen;
    private TruyenTranhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        fetchTruyenTranh();
    }

    private void addControls() {
        btnHome = findViewById(R.id.btnHome);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
        imgSearch = findViewById(R.id.img_search);
        bannerImage = findViewById(R.id.bannerImage);
        gvTruyen = findViewById(R.id.gvTruyen);
    }

    private void addEvents() {
        gvTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TruyenTranh selectedComic = (TruyenTranh) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, MangaInfoActivity.class);
                intent.putExtra("truyenTranhId", selectedComic.getTruyenTranhId());
                startActivity(intent);
            }
        });


        imgSearch.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, vn.edu.stu.apptruyentranh.MainActivity.class);
            startActivity(intent);
        });
        btnTag.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, TagActivity.class);
            startActivity(intent);
        });
        btnShoppingList.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, BougthActivity.class);
            startActivity(intent);
        });
        btnBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });
        btnUser.setOnClickListener(v -> {
            Intent intent = new Intent(vn.edu.stu.apptruyentranh.MainActivity.this, UserActivity.class);
            startActivity(intent);
        });
    }

    private void fetchTruyenTranh() {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<TruyenTranh>> call = apiService.getAllComics();

        call.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TruyenTranh> truyenTranhList = response.body();
                    adapter = new TruyenTranhAdapter(vn.edu.stu.apptruyentranh.MainActivity.this, truyenTranhList);
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

