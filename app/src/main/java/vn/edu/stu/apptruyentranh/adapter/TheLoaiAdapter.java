package vn.edu.stu.apptruyentranh.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.stu.apptruyentranh.R;

public class TheLoaiAdapter extends BaseAdapter {
    private Context context;
    private List<String> categories;

    public TheLoaiAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra convertView để tối ưu hóa hiệu suất
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_the_loai, parent, false);
        }

        // Lấy TextView từ layout item
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        tvCategory.setText(categories.get(position)); // Gán tên thể loại

        return convertView;
    }
}