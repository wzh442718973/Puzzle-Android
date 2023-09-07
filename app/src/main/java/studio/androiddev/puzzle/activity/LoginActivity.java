package studio.androiddev.puzzle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import studio.androiddev.puzzle.PuzzleApplication;
import studio.androiddev.puzzle.R;
import studio.androiddev.puzzle.databinding.ActivityLoginBinding;
import studio.androiddev.puzzle.model.User;
import studio.androiddev.puzzle.utils.RegExUtil;
import studio.androiddev.puzzle.utils.SecurityUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    EditText met_phone;
    EditText met_pwd;
    ImageButton mbtn_register;
    ImageButton mbtn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bmob.initialize(this, StaticValue.bmobId);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         met_phone = binding.etPhone;
         met_pwd = binding.etPwd;
         mbtn_register = binding.btnRegister;
         mbtn_login = binding.btnLogin;

        mbtn_login.setOnClickListener(this);
        mbtn_register.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //注册按钮点击事件
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                //登录按钮点击事件
                final String phone = met_phone.getText().toString();
                final String pwd = met_pwd.getText().toString();

                if (RegExUtil.confirmPhone(phone)) {
                    if (RegExUtil.confirmPwd(pwd)) {

                        // 登录验证逻辑函数
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("phoneNum", phone);
                        query.findObjects(PuzzleApplication.getAppContext(), new FindListener<User>() {
                            @Override
                            public void onSuccess(List<User> list) {
                                if (list.size() == 1) {
                                    String pwd_MD5_Bmob = list.get(0).getPwd();
                                    String pwd_MD5_Local = SecurityUtils.MD5_secure(pwd);
                                    if (pwd_MD5_Bmob.equals(pwd_MD5_Local)) {
                                        //登陆成功后要做三件事：
                                        //1.更新Application中的User
                                        //2.启动MainActivity
                                        //3.清空输入框
                                        PuzzleApplication.setmUser(list.get(0));
                                        MainActivity.actionStart(LoginActivity.this);
                                        met_phone.setText("");
                                        met_pwd.setText("");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "输入密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (list.size() == 0) {
                                    Toast.makeText(LoginActivity.this, "不存在该账号，请注册！", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.e("main", s);
                            }
                        });

                    } else {
                        met_pwd.setError("密码格式错误,请重新输入！");
                    }

                } else {
                    met_phone.setError("手机号格式错误，请重新输入！");
                }
                break;
        }
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
