package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TagActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;
    private ImageButton imgSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tag);
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
    }

    private void addEvents() {
        imgSearch.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, SearchActivity.class);
            startActivity(intent); });
        btnHome.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, MainActivity.class);
            startActivity(intent); });
        btnTag.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, TagActivity.class);
            startActivity(intent); });
        btnShoppingList.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, BougthActivity.class);
            startActivity(intent); });
        btnBookmark.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, BookmarkActivity.class);
            startActivity(intent); });
        btnUser.setOnClickListener(v ->{
            Intent intent = new Intent(TagActivity.this, UserActivity.class);
            startActivity(intent); });
    }
}