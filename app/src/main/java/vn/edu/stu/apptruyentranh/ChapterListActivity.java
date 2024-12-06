package vn.edu.stu.apptruyentranh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity {

    Button btnChitiet, btnRead;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chapter_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addControls() {
        btnChitiet = findViewById(R.id.btnChitiet);
        btnRead = findViewById(R.id.btnRead);
        listView = findViewById(R.id.lvChapter);

        ArrayList<String> dummyItems = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            dummyItems.add("Chapter " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dummyItems);
        listView.setAdapter(adapter);
    }

    private void addEvents() {
        btnChitiet.setOnClickListener(v -> {
            Intent intent = new Intent(ChapterListActivity.this, MangaInfoActivity.class);
            startActivity(intent);
        });
    }
}
