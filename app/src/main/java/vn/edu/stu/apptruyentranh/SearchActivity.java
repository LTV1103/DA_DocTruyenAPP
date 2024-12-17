package vn.edu.stu.apptruyentranh;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.adapter.TruyenTranhAdapter;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch;
    GridView Search;
    TruyenTranhAdapter adapter;
    List<TruyenTranh> truyenTranhList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addControls();
        addEvents();
        fetchTruyenTranh();
    }

    private void addControls() {
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        Search = findViewById(R.id.Gvsearch);
        truyenTranhList = new ArrayList<>();
        adapter = new TruyenTranhAdapter(this, truyenTranhList);
        Search.setAdapter(adapter);
    }

    private void addEvents() {
        btnSearch.setOnClickListener(v -> {
            String query = edtSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchComics(query);
            } else {
                adapter.updateList(truyenTranhList);
            }
        });

        Search.setOnItemClickListener((parent, view, position, id) -> {
            TruyenTranh selectedComic = truyenTranhList.get(position);
            Toast.makeText(SearchActivity.this, "Selected: " + selectedComic.getTenTruyen(), Toast.LENGTH_SHORT).show();
        });
    }

    private String removeVietnameseAccents(String str) {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private void fetchTruyenTranh() {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<TruyenTranh>> call = apiService.getAllComics();

        call.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    truyenTranhList = response.body();
                    adapter.updateList(truyenTranhList);
                }
            }

            @Override
            public void onFailure(Call<List<TruyenTranh>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchComics(String query) {
        String normalizedQuery = removeVietnameseAccents(query).toLowerCase();

        List<TruyenTranh> filteredList = new ArrayList<>();
        for (TruyenTranh comic : truyenTranhList) {
            String normalizedComicName = removeVietnameseAccents(comic.getTenTruyen()).toLowerCase();

            if (normalizedComicName.contains(normalizedQuery)) {
                filteredList.add(comic);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(SearchActivity.this, "Không tìm thấy truyện", Toast.LENGTH_SHORT).show();
        }

        adapter.updateList(filteredList);
    }
}
