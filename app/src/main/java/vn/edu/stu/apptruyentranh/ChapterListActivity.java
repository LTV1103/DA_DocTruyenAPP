package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.ChuongTruyen;

public class ChapterListActivity extends AppCompatActivity {

    private Button btnChitiet, btnRead;
    private TextView tvTitle, tvStatus;
    private ListView listView;
    private List<ChuongTruyen> chapters; // List of ChuongTruyen objects
    private ArrayAdapter<ChuongTruyen> adapter;
    private int truyenTranhId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        addControls();
        addEvents();

        // Get data from Intent
        truyenTranhId = getIntent().getIntExtra("truyenTranhId", -1);
        String title = getIntent().getStringExtra("title");
        String status = getIntent().getStringExtra("status");

        // Display data on UI
        if (title != null) tvTitle.setText(title);
        if (status != null) tvStatus.setText(status);

        // Fetch chapters if truyenTranhId is valid
        if (truyenTranhId != -1) {
            fetchChapters(truyenTranhId);
        } else {
            Log.e("ChapterListActivity", "Không có truyenTranhId");
            Toast.makeText(this, "Không có mã truyện tranh", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        btnChitiet = findViewById(R.id.btnChitiet);
        btnRead = findViewById(R.id.btnRead);
        listView = findViewById(R.id.lvChapter);
        tvTitle = findViewById(R.id.tvTitle);
        tvStatus = findViewById(R.id.tvStatus); // Ensure the ID is correct
        chapters = new ArrayList<>();
        adapter = new ArrayAdapter<ChuongTruyen>(this, android.R.layout.simple_list_item_1, chapters) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getTenChuong());  // Display the chapter title
                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }

    private void addEvents() {
        btnChitiet.setOnClickListener(v -> {
            Intent intent = new Intent(ChapterListActivity.this, MangaInfoActivity.class);
            intent.putExtra("truyenTranhId", truyenTranhId);
            startActivity(intent);
        });

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            ChuongTruyen selectedComic = chapters.get(position); // Get the clicked ChuongTruyen
            Intent intent = new Intent(ChapterListActivity.this, ReadActivity.class);
            intent.putExtra("chuongTitle", selectedComic.getTenChuong());
            intent.putExtra("chuongtruyenId", selectedComic.getChuongTruyenId());
            startActivity(intent);
        });
    }

    private void fetchChapters(int truyenTranhId) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<ChuongTruyen>> call = apiService.getChaptersByTruyenTranhId(truyenTranhId);

        call.enqueue(new Callback<List<ChuongTruyen>>() {
            @Override
            public void onResponse(Call<List<ChuongTruyen>> call, Response<List<ChuongTruyen>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chapters.addAll(response.body()); // Add fetched chapters to the list
                    adapter.notifyDataSetChanged(); // Notify adapter about data change
                } else {
                    Log.e("ChapterListActivity", "Không thể tải danh sách chương: " + response.message());
                    Toast.makeText(ChapterListActivity.this, "Không thể tải danh sách chương", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChuongTruyen>> call, Throwable t) {
                Log.e("ChapterListActivity", "Lỗi khi tải chương", t);
                Toast.makeText(ChapterListActivity.this, "Lỗi khi tải chương: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
