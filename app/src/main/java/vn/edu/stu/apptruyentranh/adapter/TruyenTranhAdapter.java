package vn.edu.stu.apptruyentranh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.edu.stu.apptruyentranh.R;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public class TruyenTranhAdapter extends BaseAdapter {
    private Context context;
    private List<TruyenTranh> truyenTranhList;

    public TruyenTranhAdapter(Context context, List<TruyenTranh> truyenTranhList) {
        this.context = context;
        this.truyenTranhList = truyenTranhList;
    }

    @Override
    public int getCount() {
        return truyenTranhList.size();
    }

    @Override
    public Object getItem(int position) {
        return truyenTranhList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu convertView là null, tạo mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_truyen_tranh, parent, false);
        }

        // Lấy dữ liệu của truyện tranh từ danh sách
        TruyenTranh truyenTranh = (TruyenTranh) getItem(position);

        // Tìm các view trong item
        ImageView imgCover = convertView.findViewById(R.id.img_cover);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);

        // Hiển thị tên truyện
        tvTitle.setText(truyenTranh.getTenTruyen());

        // Xử lý URL ảnh bìa
        String imageUrl = truyenTranh.getAnhBia();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Nếu URL không phải là URL đầy đủ, bổ sung domain
            if (!imageUrl.startsWith("http")) {
                imageUrl = "https://res.cloudinary.com/dsyeobmcl/image/upload/AnhBia/" + imageUrl;
            }

            // Sử dụng Glide để tải ảnh
            Glide.with(context)
                    .load(imageUrl)  // URL ảnh truyện
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định khi đang tải
                            .error(R.drawable.ic_launcher_foreground)     // Ảnh lỗi khi không tải được
                            .centerCrop())  // Đảm bảo ảnh đầy đủ và cắt bỏ phần không cần thiết
                    .into(imgCover);
        } else {
            // Nếu không có URL ảnh, sử dụng ảnh mặc định
            Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(imgCover);
        }

        return convertView;
    }
}
