package studio.androiddev.puzzle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import studio.androiddev.puzzle.R;
import studio.androiddev.puzzle.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ImageButton beginButton;
    ImageButton rankButton;
    ImageButton settingButton;
    ImageButton exitButton;
    Toolbar toolbar;
    ImageButton userButton;

    //用于记录两次按下返回键的间隔
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         beginButton = binding.beginButton;
         rankButton = binding.rankButton;
         settingButton = binding.settingButton;
         exitButton = binding.exitButton;
         toolbar = binding.toolbar;
         userButton = binding.userButton;


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        PuzzleApplication.initDishManager(4);


        beginButton.setOnClickListener(this);
        rankButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        userButton.setOnClickListener((view)->{
            this.onClick();
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.beginButton:
                ChoosePicActivity.actionStart(MainActivity.this);
                break;
            case R.id.rankButton:
                RankActivity.actionStart(MainActivity.this);
                break;
            case R.id.settingButton:
                SettingActivity.actionStart(MainActivity.this);
                break;
            case R.id.exitButton:
                ActivityManager.finishAll();
                break;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //在两秒内两次按下返回键退出程序
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, getString(R.string.click_again_to_exit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void onClick() {
        UserInfoActivity.actionStart(MainActivity.this);
    }
}
