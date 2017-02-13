package net.anumbrella.lkshop.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.utils.BaseUtils;

/**
 * author：Anumbrella
 * Date：16/6/7 下午1:43
 */
@SuppressLint("InflateParams")
public class PromptDialog extends Dialog {

    private Context context;

    public static final int VIEW_STYLE_NORMAL = 0x00000001;
    public static final int VIEW_STYLE_TITLEBAR = 0x00000002;
    public static final int VIEW_STYLE_TITLEBAR_SKYBLUE = 0x00000003;

    public static final int BUTTON_COUNT_ZERO = 0x00000000;
    public static final int BUTTON_COUNT_ONE = 0x00000001;
    public static final int BUTTON_COUNT_TWO = 0x00000002;
    public static final int BUTTON_COUNT_THREE = 0x00000003;

    public static final int BUTTON_1 = 0x00000001;
    public static final int BUTTON_2 = 0x00000002;
    public static final int BUTTON_3 = 0x00000003;

    protected PromptDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected PromptDialog(Context context) {
        this(context, R.style.PromptDialogStyle);
    }

    protected PromptDialog(Context context, boolean cancelableOnTouchOutside) {
        this(context);
        this.setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int marginBorder = BaseUtils.dp2px(context, 30);
        params.width = BaseUtils.getScreenWidth(context) - marginBorder * 2;
        window.setAttributes(params);
    }

    @SuppressLint({"NewApi", "InflateParams"})
    public static class Builder {

        private PromptDialog dialog;
        private Context context;

        private CharSequence title;
        private CharSequence message;
        private CharSequence button1Text;
        private CharSequence button2Text;
        private CharSequence button3Text;
        private int button1TextColor;
        private int button2TextColor;
        private int button3TextColor;
        private int titleColor;
        private int messageColor;
        private float button1Size;
        private float button2Size;
        private float button3Size;
        private float titleSize;
        private float messageSize;
        private ColorStateList titleColorStateList;
        private ColorStateList messageColorStateList;
        private ColorStateList button1ColorStateList;
        private ColorStateList button2ColorStateList;
        private ColorStateList button3ColorStateList;
        private int titlebarGravity;

        private Drawable icon;
        private boolean cancelable = true;
        private boolean canceledOnTouchOutside;
        private View view;
        private int viewStyle;

        private PromptDialog.OnClickListener button1Listener;
        private PromptDialog.OnClickListener button2Listener;
        private PromptDialog.OnClickListener button3Listener;

        private int button1Flag;
        private int button2Flag;
        private int button3Flag;

        public Builder(Context context, int theme) {
            dialog = new PromptDialog(context, theme);
            this.context = context;
            initData();
        }

        public Builder(Context context) {
            dialog = new PromptDialog(context);
            this.context = context;
            initData();
        }

        private void initData() {
            this.button1TextColor = Color.parseColor("#808080");
            this.button2TextColor = Color.parseColor("#808080");
            this.button3TextColor = Color.parseColor("#808080");
            this.messageColor = Color.parseColor("#696969");
            this.titleColor = Color.BLACK;

            this.button1Size = 16;
            this.button2Size = 16;
            this.button3Size = 16;
            this.messageSize = 15;
            this.titleSize = 18;

            this.titlebarGravity = Gravity.CENTER;
        }

        public Context getContext() {
            return context;
        }

        public Builder setTitleBarGravity(int titlebarGravity) {
            this.titlebarGravity = titlebarGravity;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }


        public Builder setTitle(int titleResId) {
            this.title = context.getResources().getString(titleResId);
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setTitleColor(ColorStateList titleColor) {
            this.titleColorStateList = titleColor;
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public Builder setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setIcon(int iconResId) {
            this.icon = context.getResources().getDrawable(iconResId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int messageResId) {
            this.message = context.getResources().getString(messageResId);
            return this;
        }

        public Builder setMessageColor(int color) {
            this.messageColor = color;
            return this;
        }

        public Builder setMessageColor(ColorStateList color) {
            this.messageColorStateList = color;
            return this;
        }

        public Builder setMessageSize(float size) {
            this.messageSize = size;
            return this;
        }

        public Builder setButton1(CharSequence text, PromptDialog.OnClickListener listener) {
            this.button1Text = text;
            this.button1Listener = listener;
            button1Flag = 1;
            return this;
        }

        public Builder setButton1(int textId, PromptDialog.OnClickListener listener) {
            this.button1Text = context.getResources().getString(textId);
            this.button1Listener = listener;
            button1Flag = 1;
            return this;
        }

        public Builder setButton1TextColor(int color) {
            this.button1TextColor = color;
            return this;
        }

        public Builder setButton1TextColor(ColorStateList color) {
            this.button1ColorStateList = color;
            return this;
        }

        public Builder setButton1Size(float button1Size) {
            this.button1Size = button1Size;
            return this;
        }

        public Builder setButton2(CharSequence text, PromptDialog.OnClickListener listener) {
            this.button2Text = text;
            this.button2Listener = listener;
            button2Flag = 2;
            return this;
        }

        public Builder setButton2(int textId, PromptDialog.OnClickListener listener) {
            this.button2Text = context.getResources().getString(textId);
            this.button2Listener = listener;
            button2Flag = 2;
            return this;
        }

        public Builder setButton2TextColor(int color) {
            this.button2TextColor = color;
            return this;
        }

        public Builder setButton2TextColor(ColorStateList color) {
            this.button2ColorStateList = color;
            return this;
        }

        public Builder setButton2Size(float button2Size) {
            this.button2Size = button2Size;
            return this;
        }

        public Builder setButton3(CharSequence text, PromptDialog.OnClickListener listener) {
            this.button3Text = text;
            this.button3Listener = listener;
            button3Flag = 4;
            return this;
        }

        public Builder setButton3(int textId, PromptDialog.OnClickListener listener) {
            this.button3Text = context.getResources().getString(textId);
            this.button3Listener = listener;
            button3Flag = 4;
            return this;
        }

        public Builder setButton3TextColor(int color) {
            this.button3TextColor = color;
            return this;
        }

        public Builder setButton3TextColor(ColorStateList color) {
            this.button3ColorStateList = color;
            return this;
        }

        public Builder setButton3Size(float button3Size) {
            this.button3Size = button3Size;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceled) {
            this.canceledOnTouchOutside = canceled;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        /**
         * you can set view style, and need not set others,including message style,title style,etc.
         */
        public Builder setViewStyle(int style) {
            this.viewStyle = style;

            switch (style) {
                case VIEW_STYLE_TITLEBAR:
                    this.titlebarGravity = Gravity.LEFT;
                    break;

                case VIEW_STYLE_TITLEBAR_SKYBLUE:
                    this.titleColor = Color.WHITE;
                    this.titlebarGravity = Gravity.LEFT;
                    break;

                default:
                    break;
            }
            return this;
        }

        @SuppressLint("InflateParams")
        public PromptDialog create() {

            if (dialog == null) {
                return null;
            }

            View mView = null;
            LinearLayout mTitleBar = null;
            TextView mTitle = null;
            TextView mMessage = null;
            TextView btnLeft = null;
            TextView btnCenter = null;
            TextView btnRight = null;
            LinearLayout addView = null;
            LinearLayout btnView = null;
            View btnDivider1 = null;
            View btnDivider2 = null;
            View msgBtnDivider = null;

            switch (viewStyle) {
                case VIEW_STYLE_TITLEBAR:
                    mView = LayoutInflater.from(context).inflate(R.layout.fynn_prompt_dialog_titlebar, null);
                    break;
                case VIEW_STYLE_TITLEBAR_SKYBLUE:
                    mView = LayoutInflater.from(context).inflate(R.layout.fynn_prompt_dialog_titlebar_skyblue, null);
                    break;
                case VIEW_STYLE_NORMAL:
                default:
                    mView = LayoutInflater.from(context).inflate(R.layout.fynn_prompt_dialog_normal, null);
                    break;
            }

            mTitleBar = (LinearLayout) mView.findViewById(R.id.titlebar);
            mTitle = (TextView) mView.findViewById(R.id.title);
            mMessage = (TextView) mView.findViewById(R.id.message);
            addView = (LinearLayout) mView.findViewById(R.id.layout_addview);
            btnLeft = (TextView) mView.findViewById(R.id.button_left);
            btnCenter = (TextView) mView.findViewById(R.id.button_center);
            btnRight = (TextView) mView.findViewById(R.id.button_right);
            btnDivider1 = (View) mView.findViewById(R.id.btn_divider1);
            btnDivider2 = (View) mView.findViewById(R.id.btn_divider2);
            msgBtnDivider = (View) mView.findViewById(R.id.msg_btn_divider);
            btnView = (LinearLayout) mView.findViewById(R.id.btn_view);

            if ((title != null) || (icon != null)) {
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(title);
                mTitle.setTextSize(titleSize);
                mTitle.setTextColor(titleColor);
                if (titleColorStateList != null) {
                    mTitle.setTextColor(titleColorStateList);
                }
                mTitle.setCompoundDrawables(icon, null, null, null);
                mTitleBar.setGravity(titlebarGravity);
            } else {
                mTitle.setVisibility(View.GONE);
            }

            if (message != null) {
                mMessage.setVisibility(View.VISIBLE);
                mMessage.setText(message);
                mMessage.setTextSize(messageSize);
                mMessage.setTextColor(messageColor);
                if (messageColorStateList != null) {
                    mMessage.setTextColor(messageColorStateList);
                }
            } else {
                mMessage.setVisibility(View.GONE);
            }

            if (view != null) {
                addView.removeAllViews();
                addView.addView(view);
                addView.setGravity(Gravity.CENTER);
            }

            int btnCountFlag = button1Flag + button2Flag + button3Flag;
            switch (btnCountFlag) {
                //one button
                case 1:
                case 5:
                    btnCenter.setVisibility(View.VISIBLE);
                    btnLeft.setVisibility(View.GONE);
                    btnRight.setVisibility(View.GONE);
                    btnCenter.setBackgroundResource(R.drawable.fynn_prompt_dialog_btn_single_selector);
                    if (button1Text != null) {
                        btnCenter.setText(button1Text);
                        btnCenter.setTextSize(button1Size);
                        btnCenter.setTextColor(button1TextColor);
                        if (button1ColorStateList != null) {
                            btnCenter.setTextColor(button1ColorStateList);
                        }
                        if (button1Listener != null) {
                            btnCenter.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button1Listener.onClick(dialog, BUTTON_1);
                                }
                            });
                        }
                    }
                    break;

                case 3:
                    //two button
                    btnLeft.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnCenter.setVisibility(View.GONE);
                    btnDivider1.setVisibility(View.VISIBLE);
                    btnDivider2.setVisibility(View.GONE);

                    if (button1Text != null) {
                        btnLeft.setText(button1Text);
                        btnLeft.setTextSize(button1Size);
                        btnLeft.setTextColor(button1TextColor);

                        if (button1ColorStateList != null) {
                            btnLeft.setTextColor(button1ColorStateList);
                        }

                        if (button1Listener != null) {
                            btnLeft.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button1Listener.onClick(dialog, BUTTON_1);
                                }
                            });
                        }
                    }

                    if (button2Text != null) {
                        btnRight.setText(button2Text);
                        btnRight.setTextSize(button2Size);
                        btnRight.setTextColor(button2TextColor);

                        if (button2ColorStateList != null) {
                            btnRight.setTextColor(button2ColorStateList);
                        }

                        if (button2Listener != null) {
                            btnRight.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button2Listener.onClick(dialog, BUTTON_2);
                                }
                            });
                        }
                    }
                    break;
                case 7:
                    //three button
                    btnLeft.setVisibility(View.VISIBLE);
                    btnCenter.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnDivider1.setVisibility(View.VISIBLE);
                    btnDivider2.setVisibility(View.VISIBLE);

                    if (button1Text != null) {
                        btnLeft.setText(button1Text);
                        btnLeft.setTextSize(button1Size);
                        btnLeft.setTextColor(button1TextColor);

                        if (button1ColorStateList != null) {
                            btnLeft.setTextColor(button1ColorStateList);
                        }

                        if (button1Listener != null) {
                            btnLeft.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button1Listener.onClick(dialog, BUTTON_1);
                                }
                            });
                        }
                    }
                    if (button2Text != null) {
                        btnCenter.setText(button2Text);
                        btnCenter.setText(button2Text);
                        btnCenter.setTextSize(button2Size);
                        btnCenter.setTextColor(button2TextColor);

                        if (button2ColorStateList != null) {
                            btnCenter.setTextColor(button2ColorStateList);
                        }

                        if (button2Listener != null) {
                            btnCenter.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button2Listener.onClick(dialog, BUTTON_2);
                                }
                            });
                        }
                    }

                    if (button3Text != null) {
                        btnRight.setText(button3Text);
                        btnRight.setTextSize(button3Size);
                        btnRight.setTextColor(button3TextColor);

                        if (button3ColorStateList != null) {
                            btnRight.setTextColor(button3ColorStateList);
                        }

                        if (button3Listener != null) {
                            btnRight.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    button3Listener.onClick(dialog, BUTTON_3);
                                }
                            });
                        }
                    }
                    break;

                default:
                    btnView.setVisibility(View.GONE);
                    msgBtnDivider.setVisibility(View.GONE);
                    break;
            }

            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

            dialog.setContentView(mView);
            return dialog;
        }

        public PromptDialog show() {
            create().show();
            return dialog;
        }


    }

    public interface OnClickListener {
        void onClick(Dialog dialog, int which);
    }
}