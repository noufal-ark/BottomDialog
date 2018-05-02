package com.ark.bottomdialog.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ark.bottomdialog.R;
import com.ark.bottomdialog.utils.Utils;

/**
 * Created by Muhammed Noufal Koorikkadan on 01/05/2018
 * mail us : noufal.ark@gmail.com
 */
public class BottomDialog {


    private Context context;
    private Dialog dialog;
    private int buttonColor = -1;
    private int backgroundColor = -1;
    private boolean showButtons = false;

    private TextView titleTextView;
    private TextView messageTextView;
    private ImageView iconImageView;
    private Button positiveButton;
    private Button negativeButton;
    private RelativeLayout textContainer;
    private LinearLayout messageContainer;

    private View inflatedView;

    public BottomDialog(Context context) {
        this.context = context;
        init(context);
    }

    public BottomDialog setTitle(CharSequence title) {
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(title);
        return this;
    }

    public BottomDialog setTitle(@StringRes int titleRes) {
        setTitle(context.getResources().getString(titleRes));
        return this;
    }

    public BottomDialog setMessage(CharSequence message) {
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message);
        return this;
    }

    public BottomDialog setMessage(@StringRes int messageRes) {
        setMessage(context.getResources().getString(messageRes));
        return this;
    }

    public BottomDialog setIcon(Drawable icon) {
        showIcon();
        iconImageView.setImageDrawable(icon);
        return this;
    }

    public BottomDialog setIcon(Bitmap icon) {
        showIcon();
        iconImageView.setImageBitmap(icon);
        return this;
    }

    public BottomDialog setIcon(@DrawableRes int iconRes) {
        showIcon();
        iconImageView.setImageResource(iconRes);
        return this;
    }

    public BottomDialog setPositiveButton(CharSequence text, final OnPositiveClickListener onPositiveClickListener) {
        positiveButton.setVisibility(View.VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (onPositiveClickListener != null)
                    onPositiveClickListener.onClick(view);
            }
        });
        showButtons = true;
        return this;
    }

    public BottomDialog setNegativeButton(CharSequence text, final OnNegativeClickListener onNegativeClickListener) {
        negativeButton.setVisibility(View.VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (onNegativeClickListener != null)
                    onNegativeClickListener.onClick(view);
            }
        });
        showButtons = true;
        return this;
    }

    public BottomDialog setPositiveButton(@StringRes int textRes, OnPositiveClickListener onPositiveClickListener) {
        setPositiveButton(context.getResources().getString(textRes), onPositiveClickListener);
        return this;
    }

    public BottomDialog setNegativeButton(@StringRes int textRes, OnNegativeClickListener onNegativeClickListener) {
        setNegativeButton(context.getResources().getString(textRes), onNegativeClickListener);
        return this;
    }

    public BottomDialog setButtonsColor(@ColorInt int buttonsColor) {
        this.buttonColor = buttonsColor;
        return this;
    }

    public BottomDialog setButtonsColorRes(@ColorRes int buttonsColorRes) {
        this.buttonColor = ContextCompat.getColor(context, buttonsColorRes);
        return this;
    }

    public BottomDialog setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public BottomDialog setBackgroundColorRes(@ColorRes int backgroundColorRes) {
        this.backgroundColor = ContextCompat.getColor(context, backgroundColorRes);
        return this;
    }

    public BottomDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    public BottomDialog setView(View view) {
        messageContainer.addView(view);
        if (inflatedView == null)
            inflatedView = view;
        return this;
    }

    public BottomDialog setView(@LayoutRes int layoutRes) {
        inflatedView = View.inflate(context, layoutRes, null);
        setView(inflatedView);
        return this;
    }

    public View getInflatedView() {
        return inflatedView;
    }

    public void show() {
        if (backgroundColor == -1)
            backgroundColor = Utils.getThemeBgColor(context);
        if (backgroundColor != -1) {
            dialog.findViewById(R.id.mainDialogContainer).setBackgroundColor(backgroundColor);
            titleTextView.setTextColor(Utils.getTextColor(backgroundColor));
            messageTextView.setTextColor(Utils.getTextColorSec(backgroundColor));
        }

        if (!showButtons)
            textContainer.setPadding(0, 0, 0, 0);
        else {
            int color;
            if (buttonColor != -1)
                color = buttonColor;
            else color = Utils.getAccentColor(context);
            negativeButton.setTextColor(color);
            Utils.setButton(backgroundColor, color, positiveButton, true);
            Utils.setButton(backgroundColor, color, negativeButton, false);
            positiveButton.setTextColor(Utils.buttonTextColor(color));
        }

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    private void init(Context context) {
        dialog = new Dialog(context, R.style.BottomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomdialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        titleTextView = dialog.findViewById(R.id.dialogTitle);
        messageTextView = dialog.findViewById(R.id.dialogMessage);
        iconImageView = dialog.findViewById(R.id.dialogIcon);
        positiveButton = dialog.findViewById(R.id.buttonPositive);
        negativeButton = dialog.findViewById(R.id.buttonNegative);
        textContainer = dialog.findViewById(R.id.textContainer);
        messageContainer = dialog.findViewById(R.id.messageContainer);
    }

    private void showIcon() {
        iconImageView.setVisibility(View.VISIBLE);
    }

    public interface OnPositiveClickListener {
        public void onClick(View v);
    }

    public interface OnNegativeClickListener {
        public void onClick(View v);
    }
}
