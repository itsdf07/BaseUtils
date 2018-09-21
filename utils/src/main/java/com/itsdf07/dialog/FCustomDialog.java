package com.itsdf07.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
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
        private static final float BUTTON_FONTSIZE = 16f;
        private static final float CONTENT_FONTSIZE = 18f;
        public Context context;
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private int positiveButtonBgResId = 0;
        private int negativeButtonBgResId = 0;
        private int singleButtonBgResId = 0;
        private int dialogBgResId = 0;
        private int positiveButtonFontColorId = 0;
        private int negativeButtonFontColorId = 0;
        private int singleButtonFontColorId = 0;
        private float textFontSizeButton = BUTTON_FONTSIZE;
        private float textFontSizeContent = CONTENT_FONTSIZE;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private View layout;
        private FCustomDialog dialog;

        public Builder(Context context) {
            this.context = context;
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
         * @param bgResId            R.drawable.fbtn_bg_3f8ee9_style
         * @param fontColor          R.color.color_black
         * @param listener           按钮监听事件
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, int bgResId, int fontColor, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonBgResId = bgResId;
            this.positiveButtonFontColorId = fontColor;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * @param negativeButtonText 文案
         * @param bgResId            R.drawable.fbtn_bg_3f8ee9_style
         * @param fontColor          R.color.color_black
         * @param listener           按钮监听事件
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, int bgResId, int fontColor, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonBgResId = bgResId;
            this.negativeButtonFontColorId = fontColor;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param singleButtonText 文案
         * @param bgResId          R.drawable.fbtn_bg_3f8ee9_style
         * @param fontColor        R.color.color_black
         * @param listener         按钮监听事件
         * @return
         */
        public Builder setSingleButton(String singleButtonText, int bgResId, int fontColor, View.OnClickListener listener) {
            this.singleButtonText = singleButtonText;
            this.singleButtonBgResId = bgResId;
            this.singleButtonFontColorId = fontColor;
            this.singleButtonClickListener = listener;
            return this;
        }


        /**
         * 按钮字体大小
         *
         * @param textFontSizeButton
         * @return
         */
        public Builder setTextFontSizeButton(float textFontSizeButton) {
            this.textFontSizeButton = textFontSizeButton;
            return this;
        }

        /**
         * 窗口消息内容字体大小
         *
         * @param textFontSizeContent
         * @return
         */
        public Builder setTextFontSizeContent(float textFontSizeContent) {
            this.textFontSizeContent = textFontSizeContent;
            return this;
        }

        public Builder setBackground(int bgResId) {
            this.dialogBgResId = bgResId;
            return this;
        }

        /**
         * 创建单按钮对话框
         *
         * @return
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public FCustomDialog createSingleButtonDialog() {
            showSingleButton();
            layout.findViewById(R.id.btn_single).setOnClickListener(singleButtonClickListener);
            if (dialogBgResId != 0) {
                (layout.findViewById(R.id.layout_dialog)).setBackgroundDrawable(context.getDrawable(dialogBgResId));//R.drawable.fbtn_bg_3f8ee9_style
            }
            //如果传入的按钮文字为空，则使用默认的“返回”
            if (singleButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_single)).setText(singleButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_single)).setText("返回");
            }
            if (singleButtonBgResId != 0) {
                ((Button) layout.findViewById(R.id.btn_single)).setBackgroundDrawable(context.getDrawable(singleButtonBgResId));//R.drawable.fbtn_bg_3f8ee9_style
            }
            if (singleButtonFontColorId != 0) {
                ((Button) layout.findViewById(R.id.btn_single)).setTextColor(context.getResources().getColor(singleButtonFontColorId));//R.color.color_black
            }
            if (textFontSizeButton != BUTTON_FONTSIZE) {
                ((Button) layout.findViewById(R.id.btn_single)).setTextSize(textFontSizeButton);
            }
            if (textFontSizeContent != CONTENT_FONTSIZE) {
                ((TextView) layout.findViewById(R.id.tv_message)).setTextSize(textFontSizeContent);
            }

            create();
            return dialog;
        }

        /**
         * 创建双按钮对话框
         *
         * @return
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public FCustomDialog createDoubleButtonDialog() {
            showDoubleButton();
            layout.findViewById(R.id.btn_positive).setOnClickListener(positiveButtonClickListener);
            layout.findViewById(R.id.btn_negative).setOnClickListener(negativeButtonClickListener);
            if (dialogBgResId != 0) {
                (layout.findViewById(R.id.layout_dialog)).setBackgroundDrawable(context.getDrawable(dialogBgResId));//R.drawable.fbtn_bg_3f8ee9_style
            }
            //如果传入的按钮文字为空，则使用默认的“是”和“否”
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_positive)).setText(positiveButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_positive)).setText("是");
            }
            if (positiveButtonBgResId != 0) {
                ((Button) layout.findViewById(R.id.btn_positive)).setBackgroundDrawable(context.getDrawable(R.drawable.fbtn_bg_3f8ee9_style));
            }
            if (positiveButtonFontColorId != 0) {
                ((Button) layout.findViewById(R.id.btn_positive)).setTextColor(context.getResources().getColor(R.color.color_black));
            }
            if (textFontSizeButton != BUTTON_FONTSIZE) {
                ((Button) layout.findViewById(R.id.btn_positive)).setTextSize(textFontSizeButton);
            }

            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_negative)).setText(negativeButtonText);
            } else {
                ((Button) layout.findViewById(R.id.btn_negative)).setText("否");
            }
            if (negativeButtonBgResId != 0) {
                ((Button) layout.findViewById(R.id.btn_negative)).setBackgroundDrawable(context.getDrawable(negativeButtonBgResId));//R.drawable.fbtn_bg_3f8ee9_style
            }
            if (negativeButtonFontColorId != 0) {
                ((Button) layout.findViewById(R.id.btn_negative)).setTextColor(context.getResources().getColor(negativeButtonFontColorId));//R.color.color_black
            }
            if (textFontSizeButton != BUTTON_FONTSIZE) {
                ((Button) layout.findViewById(R.id.btn_negative)).setTextSize(textFontSizeButton);
            }

            if (textFontSizeContent != CONTENT_FONTSIZE) {
                ((TextView) layout.findViewById(R.id.tv_message)).setTextSize(textFontSizeContent);
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
//使用方式
//private FCustomDialog.Builder builder;
//private FCustomDialog mDialog;

//builder = new FCustomDialog.Builder(this);

//mDialog = builder.setMessage(alertText)
//.setTextFontSizeButton(15f)
//.setTextFontSizeContent(40f)
//.setPositiveButton(confirmText, R.drawable.fbtn_bg_3f8ee9_style, 0, conFirmListener)
//.setNegativeButton(cancelText, 0, R.color.color_black, cancelListener)
//.createDoubleButtonDialog();
//mDialog.show();