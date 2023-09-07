package studio.androiddev.puzzle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.listener.UpdateListener;
import studio.androiddev.puzzle.PuzzleApplication;
import studio.androiddev.puzzle.R;
import studio.androiddev.puzzle.databinding.ActivityChangePwdBinding;
import studio.androiddev.puzzle.model.User;
import studio.androiddev.puzzle.utils.SecurityUtils;

public class ChangePwdActivity extends BaseActivity {
    EditText met_oldPwd;
    EditText met_newPwd;
    EditText met_confirmPwd;
    Button mbtn_confirmChangePwd;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityChangePwdBinding binding = ActivityChangePwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         met_oldPwd = binding.etPwdOld;
         met_newPwd = binding.etPwdNew;
         met_confirmPwd = binding.etConfirmNewPwd;
         mbtn_confirmChangePwd = binding.btnChangePwdConfirm;
         toolbar = binding.toolbar;

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mbtn_confirmChangePwd.setOnClickListener((view)->{
            onClick();
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

    public void onClick() {
        String oldPwd = SecurityUtils.MD5_secure(met_oldPwd.getText().toString());
        String newPwd = met_newPwd.getText().toString();
        String newConPwd = met_confirmPwd.getText().toString();
        User mUser = PuzzleApplication.getmUser();
        if (mUser.getPwd().equals(oldPwd)) {
            if (newPwd.equals(newConPwd)) {
                String s = SecurityUtils.MD5_secure(newPwd);
                mUser.setPwd(s);
                mUser.update(ChangePwdActivity.this, mUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ChangePwdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(333, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.e("main", s);
                    }
                });
            } else {
                met_confirmPwd.setError("密码输入不一致");
            }
        } else {
            Toast.makeText(ChangePwdActivity.this, "原密码输入错误，请重新输入！", Toast.LENGTH_SHORT).show();
        }
    }

    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivityForResult(intent, 1234);
    }
}
