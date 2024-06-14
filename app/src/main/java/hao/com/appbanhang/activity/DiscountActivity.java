package hao.com.appbanhang.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import hao.com.appbanhang.R;

public class DiscountActivity extends AppCompatActivity {
    ImageView imageView;
    TextView noidung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        initView();
        initData();
    }

    private void initData() {
        String nd = getIntent().getStringExtra("noidung");
        String url = getIntent().getStringExtra("url");
        noidung.setText(nd);
        Glide.with(this).load(url).into(imageView);
    }

    private void initView() {
        imageView = findViewById(R.id.km_image);
        noidung = findViewById(R.id.km_noidung);
    }
}