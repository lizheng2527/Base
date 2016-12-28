package com.zdhx.androidbase.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zdhx.androidbase.ECApplication;
import com.zdhx.androidbase.R;
import com.zdhx.androidbase.SystemConst;
import com.zdhx.androidbase.entity.ResultVo;
import com.zdhx.androidbase.entity.User;
import com.zdhx.androidbase.ui.base.BaseActivity;
import com.zdhx.androidbase.util.GsonUtil;
import com.zdhx.androidbase.util.StringUtil;
import com.zdhx.androidbase.util.volley.Params;
import com.zdhx.androidbase.util.volley.ResultListenerImpl;
import com.zdhx.androidbase.util.volley.VolleyUtils;
import com.zdhx.androidbase.view.dialog.ECProgressDialog;

import static com.zdhx.androidbase.R.id.btn_left;

/**
 * @Title: LoginActivity.java
 * @Package com.xinyulong.seagood.ui.account
 * @Description: TODO(登陆页面)
 * @date 2016-5-3 下午4:12:54
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private Activity context;

    private EditText etxt_username, etxt_pwd;

    private TextView txt_regist, txt_re_pwd;

    private ECProgressDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        dialog = new ECProgressDialog(context);
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, -1, "登录", this);
        ImageButton btnqq = (ImageButton) findViewById(R.id.ibtn_login_qq);
        ImageButton btnWeiChat = (ImageButton) findViewById(R.id.ibtn_login_weichat);
        ImageButton btnSina = (ImageButton) findViewById(R.id.ibtn_login_sina);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btnqq.setOnClickListener(this);
        btnWeiChat.setOnClickListener(this);
        btnSina.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        etxt_username = (EditText) findViewById(R.id.etxt_username);
        etxt_pwd = (EditText) findViewById(R.id.etxt_pwd);
        txt_regist = (TextView) findViewById(R.id.txt_regist);
        txt_regist.setOnClickListener(this);
        txt_re_pwd = (TextView) findViewById(R.id.txt_re_pwd);
        txt_re_pwd.setOnClickListener(this);

        if (ECApplication.getInstance().getCurrentUser() != null) {
            etxt_username.setText(ECApplication.getInstance().getCurrentUser().getLoginName());
            etxt_pwd.setText(ECApplication.getInstance().getCurrentUser().getLoginPsw());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_left:
                finish();
                break;
            case R.id.txt_regist:
                break;
            case R.id.txt_re_pwd:
                break;
            case R.id.btn_login:
                if (StringUtil.isBlank(getStringByUI(etxt_username))) {
                    doToast("用户名不能为空  ");
                    return;
                }
                if (StringUtil.isBlank(getStringByUI(etxt_pwd))) {
                    doToast("密码不能为空");
                    return;
                }
                dialog.setPressText("正在登录，请稍候");
                dialog.show();
                VolleyUtils.requestService(SystemConst.LOGIN_URL, Params.getLoginParams("1", getStringByUI(etxt_username), getStringByUI(etxt_pwd)), new ResultListenerImpl(
                        this) {
                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);
                        LoginVo vo = GsonUtil.deser(response, LoginVo.class);
                        if (vo == null) {
                            doToast(context.getResources().getString(R.string.msg_wso_error));
                            return;
                        }
                        if (vo.getResult() == 1) {
                            if (vo.getList() != null && vo.getList().size() > 0) {
                                User user = vo.getList().get(0);
                                user.setLoginName(getStringByUI(etxt_username));
                                user.setLoginPsw(getStringByUI(etxt_pwd));
                                user.setLogin(true);
                                ECApplication.getInstance().saveUser(user);
                                doToast("登录成功");
                                finish();
                            } else {
                                doToast("登录失败，请重试");
                            }
                        } else {
                            doToast(vo.getMsg());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError() {
                        super.onError();
                        dialog.dismiss();
                    }
                },false);

                break;
            default:
                break;
        }
    }

    class LoginVo extends ResultVo<User> {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

    }
}
