package studio.androiddev.puzzle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;
import studio.androiddev.puzzle.R;
import studio.androiddev.puzzle.databinding.ActivityRegisterBinding;
import studio.androiddev.puzzle.model.User;
import studio.androiddev.puzzle.utils.RegExUtil;
import studio.androiddev.puzzle.utils.SecurityUtils;

public class RegisterActivity extends BaseActivity {


    EditText etPhoneReg;
    EditText etPwdRegister;
    EditText etPwdConfirmRegister;
    ImageButton btnRegisterRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        etPhoneReg = binding.etPhoneReg;
        etPwdRegister = binding.etPwdRegister;
        etPwdConfirmRegister = binding.etPwdConfirmRegister;
        btnRegisterRes = binding.btnRegisterRes;

        btnRegisterRes.setOnClickListener((view)->{
            onClick();
        });
    }

    private void regSaveInLocal(User user) {

    }

    private void regPushToBmob(User user) {

        user.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterActivity.this, "数据存储失败,请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onClick() {
        if (RegExUtil.confirmPhone(etPhoneReg.getText().toString())) {
            if (RegExUtil.confirmPwd(etPwdRegister.getText().toString())) {
                if (etPwdRegister.getText().toString().equals(etPwdConfirmRegister.getText().toString())) {

                    User temp = new User();
                    temp.setPhoneNum(etPhoneReg.getText().toString());
                    String pwd_MD5 = SecurityUtils.MD5_secure(etPwdRegister.getText().toString());
                    temp.setPwd(pwd_MD5);
                    temp.setCreateTime(new BmobDate(new Date()));
                    temp.setImgUrl("null");
                    temp.setMailNum("待添加");
                    temp.setNickName("待添加");
                    regPushToBmob(temp);
                    regSaveInLocal(temp);

                } else {
                    etPwdConfirmRegister.setError("两次密码输入不一致！");
                }

            } else {
                etPwdRegister.setError("密码至少为8位数字或字符");
            }

        } else {
            etPhoneReg.setError("手机号码格式错误");
        }
    }
}
