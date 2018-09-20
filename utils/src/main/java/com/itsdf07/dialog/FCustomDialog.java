package com.itsdf07.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itsdf07.utils.R;

/**
 * @Description 自定义交互对话框，双按钮 （确定\取消 或者 是\否）+ 单独按钮
 * @Author itsdf07
 * @Time 2018/9/20
 */

public class FCustomDialog extends Dialog {
    public FCustomDialog(@NonNull Context context) {
        super(context);
    }

    public FCustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected FCustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private View layout;
        private FCustomDialog dialog;

        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new FCustomDialog(context, R.style.FCustomDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.fview_customdialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * @param positiveButtonText 文案
         * @param listener           按钮监听事件
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * @param negativeButtonText 文案
         * @param listener           按钮监听事件
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param singleButtonText 文案
         * @param listener         按钮监听事件
         * @return
         */
        public Builder setSingleButton(String singleButtonText, View.OnClickListener listener) {
            this.singleButtonText = singleButtonText;
            this.singleButtonClickListener = listener;
            return this;
        }

        /**
         * 创建单按钮对话框
         *
         * @return
         */
        public FCustomDialog createSingleButtonDialog() {
            showSingleButton();
            layout.findViewById(R.id.btn_single).setOnClickListener(singleButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“返回”
            if (singleButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_single)).setText(singleButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_single)).setText("返回");
            }
            create();
            return dialog;
        }

        /**
         * 创建双按钮对话框
         *
         * @return
         */
        public FCustomDialog createDoubleButtonDialog() {
            showDoubleButton();
            layout.findViewById(R.id.btn_positive).setOnClickListener(positiveButtonClickListener);
            layout.findViewById(R.id.btn_negative).setOnClickListener(negativeButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“是”和“否”
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_positive)).setText(positiveButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_positive)).setText("是");
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_negative)).setText(negativeButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_negative)).setText("否");
            }
            create();
            return dialog;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private void create() {
            if (message != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.tv_message)).setText(message);
            } else if (contentView != null) {       //如果使用Builder的setContentview()方法传入了布局，则使用传入的布局
                ((LinearLayout) layout.findViewById(R.id.layout_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.layout_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            dialog.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
        }

        /**
         * 显示双按钮布局，隐藏单按钮
         */
        private void showDoubleButton() {
            layout.findViewById(R.id.layout_btn_single).setVisibility(View.GONE);
            layout.findViewById(R.id.layout_double_btn).setVisibility(View.VISIBLE);
        }

        /**
         * 显示单按钮布局，隐藏双按钮
         */
        private void showSingleButton() {
            layout.findViewById(R.id.layout_btn_single).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.layout_double_btn).setVisibility(View.GONE);
        }

    }
}
