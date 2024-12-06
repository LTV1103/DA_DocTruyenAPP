package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;
    private ImageButton imgSearch;
    private ImageView bannerImage;
    private GridView gvTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        btnHome = findViewById(R.id.btnHome);
        btnTag = findViewById(R.id.btnTag);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnUser = findViewById(R.id.btnUser);
        imgSearch = findViewById(R.id.img_search);
        bannerImage = findViewById(R.id.bannerImage);
        gvTruyen = findViewById(R.id.gvTruyen);
    }

    private void addEvents() {

        bannerImage.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, MangaInfoActivity.class);
            startActivity(intent); });

        imgSearch.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent); });

        btnHome.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent); });
        btnTag.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, TagActivity.class);
            startActivity(intent); });
        btnShoppingList.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, BougthActivity.class);
            startActivity(intent); });
        btnBookmark.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
            startActivity(intent); });
        btnUser.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent); });
    }
}
