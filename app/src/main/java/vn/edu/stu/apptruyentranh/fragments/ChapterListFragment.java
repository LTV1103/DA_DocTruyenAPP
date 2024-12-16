package vn.edu.stu.apptruyentranh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.R;
import vn.edu.stu.apptruyentranh.ReadActivity;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.ChuongTruyen;

public class ChapterListFragment extends Fragment {

    private Button btnChitiet;
    private ListView listView;
    private List<ChuongTruyen> chapters; // List of ChuongTruyen objects
    private ArrayAdapter<ChuongTruyen> adapter;
    private int truyenTranhId;

    public ChapterListFragment() {
        // Required empty public constructor
    }

    public static ChapterListFragment newInstance(int truyenTranhId) {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle args = new Bundle();
        args.putInt("truyenTranhId", truyenTranhId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            truyenTranhId = getArguments().getInt("truyenTranhId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các phần tử từ layout
        btnChitiet = view.findViewById(R.id.btnChitiet);
        listView = view.findViewById(R.id.lvChapter);
        chapters = new ArrayList<>();
        adapter = new ArrayAdapter<ChuongTruyen>(getContext(), android.R.layout.simple_list_item_1, chapters) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(getItem(position).toString());  // Sử dụng phương thức toString() của ChuongTruyen
                return convertView;
            }
        };

        listView.setAdapter(adapter);

        btnChitiet.setOnClickListener(v -> {
            // Tạo và hiển thị MangaInfoFragment
            MangaInfoFragment mangaInfoFragment = MangaInfoFragment.newInstance(truyenTranhId);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mangaInfoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            ChuongTruyen selectedComic = chapters.get(position);
            Intent intent = new Intent(getActivity(), ReadActivity.class);
            intent.putExtra("chuongTitle", selectedComic.getTenChuong());
            intent.putExtra("chuongtruyenId", selectedComic.getChuongTruyenId());
            startActivity(intent);
        });

        // Lấy thông tin truyện tranh nếu ID hợp lệ
        if (truyenTranhId != -1) {
            fetchChapters(truyenTranhId);
        } else {
            Log.e("ChapterListFragment", "Không có truyenTranhId");
        }
    }

    private void fetchChapters(int truyenTranhId) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        Call<List<ChuongTruyen>> call = apiService.getChaptersByTruyenTranhId(truyenTranhId);

        call.enqueue(new Callback<List<ChuongTruyen>>() {
            @Override
            public void onResponse(Call<List<ChuongTruyen>> call, Response<List<ChuongTruyen>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chapters.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("ChapterListFragment", "Không thể tải danh sách chương: " + response.message());
                    Toast.makeText(getContext(), "Không thể tải danh sách chương", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChuongTruyen>> call, Throwable t) {
                Log.e("ChapterListFragment", "Lỗi khi tải chương", t);
                Toast.makeText(getContext(), "Lỗi khi tải chương: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
