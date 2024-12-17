package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.DanhDauTrang;

public class BookmarkActivity extends AppCompatActivity {
    private ListView lv_dautrang;
    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookmark);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int nguoiDungId = sharedPreferences.getInt("nguoiDungId", -1);  // Sử dụng -1 làm giá trị mặc định

        if (nguoiDungId == -1) {
            Log.e("ReadActivity", "User is not logged in");
            Toast.makeText(this, "Vui lòng đăng nhập để lưu dấu trang", Toast.LENGTH_SHORT).show();
            return;
        } else {
            fetchAndDisplayBookmarks(String.valueOf(nguoiDungId));
        }

        addControls();
        addEvents();
    }

    private void addControls() {
        btnHome = findViewById(R.id.btnHome);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
        lv_dautrang = findViewById(R.id.lv_dautrang);
    }

    private void addEvents() {
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnTag.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkActivity.this, TagActivity.class);
            startActivity(intent);
        });

        btnShoppingList.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkActivity.this, BougthActivity.class);
            startActivity(intent);
        });

        btnBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });

        btnUser.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkActivity.this, UserActivity.class);
            startActivity(intent);
        });
    }

    private void fetchAndDisplayBookmarks(String nguoiDungId) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<DanhDauTrang>> call = apiService.getBookmarksByUserId(nguoiDungId);

        call.enqueue(new Callback<List<DanhDauTrang>>() {
            @Override
            public void onResponse(Call<List<DanhDauTrang>> call, Response<List<DanhDauTrang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DanhDauTrang> bookmarks = response.body();

                    // Tạo danh sách các map cho SimpleAdapter
                    List<Map<String, String>> data = new ArrayList<>();

                    // Lấy dữ liệu từ DanhDauTrang và cho vào danh sách
                    for (DanhDauTrang bookmark : bookmarks) {
                        Map<String, String> map = new HashMap<>();
                        map.put("truyen", "Truyện: " + bookmark.getChuongTruyenId());  // Hiển thị tên truyện
                        map.put("chuong", "Chương: " + bookmark.getNguoiDungId());   // Hiển thị tên chương
                        data.add(map);
                    }

                    // Tạo SimpleAdapter
                    String[] from = {"truyen", "chuong"};
                    int[] to = {R.id.txtTruyen, R.id.txtChuong};

                    SimpleAdapter adapter = new SimpleAdapter(BookmarkActivity.this, data, R.layout.item_bookmark, from, to);
                    lv_dautrang.setAdapter(adapter);
                } else {
                    Toast.makeText(BookmarkActivity.this, "Không tìm thấy dấu trang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DanhDauTrang>> call, Throwable t) {
                Toast.makeText(BookmarkActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
