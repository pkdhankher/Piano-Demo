package com.PianoTask;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button playSequence, pauseSequence;
    private MediaPlayer mediaPlayer;
    private EditText nodeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playSequence = (Button) findViewById(R.id.btPlaySound);
        nodeContainer = (EditText) findViewById(R.id.etNodeContainer);

        playSequence.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] nodes = getNodeFromText();
                for (int i = 0; i < nodes.length; i++) {
                    String node = nodes[i];
                    if (node.equals("."))
                        miliPause();
                    else {
                        createMediaPlayer(node);
                    }
                }
            }
        });

    }

    private String[] getNodeFromText() {
        String containerText = nodeContainer.getText().toString();
        String[] splitedNodes = containerText.split("\\s+");
        return splitedNodes;
    }

    private void createMediaPlayer(String node) {
        int rawNode = getNode(node);
        Log.d(TAG, "createMediaPlayer: " + rawNode);
        if (rawNode != 0) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, getNode(node));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.start();
        }
    }

    private int getNode(String node) {

        int checkExistence = getResources().getIdentifier(node, "raw", getPackageName());

        if (checkExistence != 0) {
            return getResources().getIdentifier(node, "raw", getPackageName());
        } else {
            Toast.makeText(MainActivity.this, "Node not exist!", Toast.LENGTH_SHORT).show();
            return 0;
        }

    }

    private void miliPause() {
        try {
            if (mediaPlayer.isPlaying()) {
                Thread.sleep(mediaPlayer.getDuration() + 10);
                Log.d(TAG, "miliPause: " + "long");
            } else {
                Thread.sleep(10);
                Log.d(TAG, "miliPause: " + "short");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
