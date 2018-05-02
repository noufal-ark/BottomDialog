package com.ark.samplebottomdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ark.bottomdialog.activity.BottomDialog;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndShowBottomDialog();
            }
        });
    }

    private void createAndShowBottomDialog() {
        // Implement BottomDialog from lib.
        BottomDialog bottomDialog = new BottomDialog(this)
                .setTitle("Sample Dialog")
                .setMessage(R.string.lorem)
                .setPositiveButton(android.R.string.ok, new BottomDialog.OnPositiveClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DemoActivity.this, "Positive Button Clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        if (((AppCompatCheckBox) findViewById(R.id.customViewCheckBox)).isChecked()) {

            bottomDialog.setView(R.layout.custom_dialog_view);

            // Access dialog custom inflated view
            View inflatedView = bottomDialog.getInflatedView();
            Button button = inflatedView.findViewById(R.id.customButton);
            button.setText("Click Here !");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DemoActivity.this, "I'm a custom button", Toast.LENGTH_SHORT).show();
                }
            });

        }

        if (((AppCompatCheckBox) findViewById(R.id.iconCheckBox)).isChecked())
            bottomDialog.setIcon(R.drawable.message);

        bottomDialog.show();
    }
}
