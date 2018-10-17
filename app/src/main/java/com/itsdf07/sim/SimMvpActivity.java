package com.itsdf07.sim;

import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.itsdf07.R;
import com.itsdf07.fcommon.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/16
 */
public class SimMvpActivity extends BaseMvpActivity<SimPresenterImpl, SimModelImpl>
        implements SimContract.View {

    @BindView(R.id.btnReadSimInfo)
    Button btnReadSimInfo;
    @BindView(R.id.tvSimInfo)
    TextView tvSimInfo;

    @Override
    public void initPresenter() {
        presenter.setVM(this, model);
    }

    @Override
    public void initDataBeforeView() {

    }

    @Override
    public void initView() {
        //TextView内容过多时，可滚动
        tvSimInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initDataAfterView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sim;
    }

    @OnClick(R.id.btnReadSimInfo)
    public void onViewClicked() {
        presenter.readSimInfo();
    }

    @Override
    public void displaySimInfo(String info) {
        tvSimInfo.setText(info);
    }
}
