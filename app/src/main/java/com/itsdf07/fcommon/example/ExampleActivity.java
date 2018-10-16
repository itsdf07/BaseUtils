package com.itsdf07.fcommon.example;

import android.widget.Button;
import android.widget.Toast;

import com.itsdf07.R;
import com.itsdf07.fcommon.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */
public class ExampleActivity extends BaseMvpActivity<ExamplePresenterImpl, ExampleModelImpl>
        implements ExampleContract.View {
    @BindView(R.id.btn_test)
    Button btnTest;

    @Override
    public void initPresenter() {

    }

    @Override
    public void initDataBeforeView() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initDataAfterView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example;
    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        Toast.makeText(this, presenter.textMethod(), Toast.LENGTH_SHORT).show();
    }
}
