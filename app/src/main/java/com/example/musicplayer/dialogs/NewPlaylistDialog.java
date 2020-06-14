package com.example.musicplayer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.musicplayer.R;
import com.example.musicplayer.views.MainFragment;

public class NewPlaylistDialog extends Dialog {

    MainFragment mFragment;

    public NewPlaylistDialog(@NonNull Context context, MainFragment fragment) {
        super(context);
        mFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_playlist);
        EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPlaylistDialog.this.dismiss();
            }
        });
        Button buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.addPlaylist(editTextTitle.getText().toString());
                NewPlaylistDialog.this.dismiss();
            }
        });
    }
}
