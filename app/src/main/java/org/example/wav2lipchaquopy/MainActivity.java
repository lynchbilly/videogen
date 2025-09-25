package org.example.wav2lipchaquopy;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import android.database.Cursor;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private ProgressBar progressBar;
    private TextView statusText;
    private VideoView videoView;
    private Handler handler = new Handler();

    private String imagePath = "";
    private String audioPath = "";
    private static final int PICK_IMAGE = 1;
    private static final int PICK_AUDIO = 2;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        Button selectImageBtn = findViewById(R.id.selectImageButton);
        Button selectAudioBtn = findViewById(R.id.selectAudioButton);
        Button generateBtn = findViewById(R.id.generateButton);
        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);
        videoView = findViewById(R.id.videoView);

        selectImageBtn.setOnClickListener(v -> pickFile(PICK_IMAGE, "image/*"));
        selectAudioBtn.setOnClickListener(v -> pickFile(PICK_AUDIO, "audio/*"));

        generateBtn.setOnClickListener(v -> {
            if (imagePath.isEmpty() || audioPath.isEmpty()) {
                statusText.setText("Select image and audio first!");
                return;
            }
            progressBar.setProgress(0);
            statusText.setText("Starting...");
            videoView.setVisibility(VideoView.GONE);
            new Thread(this::runWav2Lip).start();
        });
    }

    private void pickFile(int requestCode, String type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String destPath = getExternalFilesDir(null) + "/" + getFileName(uri);

            try (InputStream in = getContentResolver().openInputStream(uri);
                 FileOutputStream out = new FileOutputStream(destPath)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            } catch (Exception e) {
                statusText.setText("Failed to copy file: " + e.getMessage());
                return;
            }

            if (requestCode == PICK_IMAGE) {
                imagePath = destPath;
                statusText.setText("Image selected");
            } else if (requestCode == PICK_AUDIO) {
                audioPath = destPath;
                statusText.setText("Audio selected");
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst())
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
