package vn.edu.stu.apptruyentranh.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_truyen_tranh, parent, false);
        }

        TruyenTranh truyenTranh = (TruyenTranh) getItem(position);

        ImageView imgCover = convertView.findViewById(R.id.img_cover);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvAuthor = convertView.findViewById(R.id.tv_author);
        TextView tvDescription = convertView.findViewById(R.id.tv_description);

        tvTitle.setText(truyenTranh.getTenTruyen());
        // Sử dụng Glide để tải hình ảnh
        Glide.with(context)
                .load(truyenTranh.getAnhBia())
                .placeholder(R.drawable.ic_launcher_background) // Hình nền khi tải ảnh
                .error(R.drawable.ic_launcher_foreground) // Hình nền khi lỗi
                .into(imgCover);

        return convertView;
    }
}
