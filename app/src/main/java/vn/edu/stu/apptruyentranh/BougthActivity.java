package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BougthActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnTag, btnShoppingList, btnBookmark, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bougth);
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
    }

    private void addEvents() {
        btnHome.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, MainActivity.class);
            startActivity(intent); });
        btnTag.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, TagActivity.class);
            startActivity(intent); });
        btnShoppingList.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, BougthActivity.class);
            startActivity(intent); });
        btnBookmark.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, BookmarkActivity.class);
            startActivity(intent); });
        btnUser.setOnClickListener(v ->{
            Intent intent = new Intent(BougthActivity.this, UserActivity.class);
            startActivity(intent); });
    }
}