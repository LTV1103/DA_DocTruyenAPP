package vn.edu.stu.apptruyentranh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.stu.apptruyentranh.R;

public class ReadAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageUrls;

    // Sửa constructor để nhận List<String> (các URL hình ảnh)
    public ReadAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the view for each item (each image)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_read, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        String imageUrl = imageUrls.get(position);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);

        return convertView;
    }
}
