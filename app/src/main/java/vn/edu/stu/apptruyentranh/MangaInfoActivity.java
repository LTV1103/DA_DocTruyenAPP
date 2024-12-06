package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MangaInfoActivity extends AppCompatActivity {

    Button btnChapter, btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manga_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addControls() {

        btnChapter = findViewById(R.id.btnChapter);
        btnRead = findViewById(R.id.btnRead);
    }

    private void addEvents() {

        btnChapter.setOnClickListener(v ->{
            Intent intent = new Intent(MangaInfoActivity.this, ChapterListActivity.class);
            startActivity(intent); });
    }
}