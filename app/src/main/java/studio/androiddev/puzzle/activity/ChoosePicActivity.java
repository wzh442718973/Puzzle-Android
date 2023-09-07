package studio.androiddev.puzzle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import studio.androiddev.puzzle.R;
import studio.androiddev.puzzle.adapter.ChoosePicGridViewAdapter;
import studio.androiddev.puzzle.databinding.ActivityChoosePicBinding;

public class ChoosePicActivity extends BaseActivity {

    Toolbar toolbar;
    GridView picContainer;

    public static final int [] icons={
            R.drawable.default1,
            R.drawable.default2,
            R.drawable.default3,
            R.drawable.default4,
            R.drawable.default5,
            R.drawable.default6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityChoosePicBinding binding = ActivityChoosePicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        picContainer = binding.picContainer;


        setSupportActionBar(toolbar);
        initView();

    }

    private void initView() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        picContainer.setAdapter(new ChoosePicGridViewAdapter(ChoosePicActivity.this, R.layout.choose_pic_gridview_item));
        picContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GameActivity.actionStart(ChoosePicActivity.this, position);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChoosePicActivity.class);
        context.startActivity(intent);
    }

}
