package vn.edu.stu.apptruyentranh.fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.apptruyentranh.R;
import vn.edu.stu.apptruyentranh.api.APIClient;
import vn.edu.stu.apptruyentranh.api.APIService;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;
public class MangaInfoFragment extends Fragment {
    private TextView tvAuthor, tvTag, tvDescribe;
    private Button btnChapter;
    private int truyenTranhId;
    public MangaInfoFragment() {
        // Required empty public constructor
    }
    public static MangaInfoFragment newInstance(int truyenTranhId) {
        MangaInfoFragment fragment = new MangaInfoFragment();
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_info, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ánh xạ các phần tử từ layout
        tvAuthor = view.findViewById(R.id.tvAuthor);
        tvTag = view.findViewById(R.id.tvTag);
        tvDescribe = view.findViewById(R.id.tvDescribe);
        btnChapter = view.findViewById(R.id.btnChapter);
        btnChapter.setOnClickListener(v -> {
            // Thực hiện chuyển đổi sang ChapterListFragment
            ChapterListFragment chapterListFragment = ChapterListFragment.newInstance(truyenTranhId);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, chapterListFragment)
                    .addToBackStack(null)
                    .commit();
        });
        // Lấy thông tin truyện tranh nếu ID hợp lệ
        if (truyenTranhId != -1) {
            fetchTruyenTranh(truyenTranhId);
        } else {
            Log.e("MangaInfoFragment", "Không có truyenTranhId");
        }
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
                    Log.e("MangaInfoFragment", "Phản hồi không thành công: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<TruyenTranh> call, Throwable t) {
                Log.e("MangaInfoFragment", "Lỗi khi tìm nạp dữ liệu", t);
            }
        });
    }
    private void updateUI(TruyenTranh truyenTranh) {
        tvAuthor.setText(truyenTranh.getTacGia());
        tvTag.setText(truyenTranh.getTheLoai());
        tvDescribe.setText(truyenTranh.getMoTa());
    }
}