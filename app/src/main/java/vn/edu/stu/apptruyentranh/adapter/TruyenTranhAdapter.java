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

    // ViewHolder pattern to cache views
    static class ViewHolder {
        ImageView imgCover;
        TextView tvTitle;
        TextView tvTheLoai;
    }

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
        ViewHolder viewHolder;

        // Recycle the view using the ViewHolder pattern
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_truyen_tranh, parent, false);

            // Initialize the ViewHolder and set it as the tag for the view
            viewHolder = new ViewHolder();
            viewHolder.imgCover = convertView.findViewById(R.id.img_cover);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvTheLoai = convertView.findViewById(R.id.tv_theloai);

            convertView.setTag(viewHolder); // Save the ViewHolder as a tag
        } else {
            // Reuse the ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the current comic
        TruyenTranh truyenTranh = (TruyenTranh) getItem(position);

        // Set the data
        viewHolder.tvTitle.setText(truyenTranh.getTenTruyen());
        viewHolder.tvTheLoai.setText(truyenTranh.getTacGia());

        // Handle cover image
        String imageUrl = truyenTranh.getAnhBia();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            if (!imageUrl.startsWith("http")) {
                imageUrl = "https://res.cloudinary.com/dsyeobmcl/image/upload/AnhBia/" + imageUrl;
            }

            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .centerCrop())
                    .into(viewHolder.imgCover);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(viewHolder.imgCover);
        }

        return convertView;
    }

    public void updateList(List<TruyenTranh> newList) {
        this.truyenTranhList = newList;
        notifyDataSetChanged();
    }
}
