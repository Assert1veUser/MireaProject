package ru.mirea.markinaa.mireaproject.ui.micro;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import ru.mirea.markinaa.mireaproject.R;
import ru.mirea.markinaa.mireaproject.databinding.FragmentCameraBinding;
import ru.mirea.markinaa.mireaproject.databinding.FragmentMicrophoneBinding;

public class MicrophoneFragment extends Fragment {

    private FragmentMicrophoneBinding binding;
    private	static	final	int	REQUEST_CODE_PERMISSION	=	200;
    private	boolean	isWork;
    private	final	String	TAG	=	MicrophoneFragment.class.getSimpleName();
    private Button recordButton	=	null;
    private	Button	playButton	=	null;
    private MediaRecorder recorder	=	null;
    private MediaPlayer player	=	null;
    private String recordFilePath = null;
    boolean	isStartRecording	=	true;
    boolean	isStartPlaying	=	true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMicrophoneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recordButton	=	binding.button;
        playButton	=	binding.button3;
        playButton.setEnabled(false);
        recordFilePath	=	(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();

        int	audioRecordPermissionStatus	=	ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.RECORD_AUDIO);
        int	storagePermissionStatus	=	ContextCompat.checkSelfPermission(this.getContext(),	android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(audioRecordPermissionStatus	==	PackageManager.PERMISSION_GRANTED	&&
                storagePermissionStatus ==	PackageManager.PERMISSION_GRANTED)	{
            isWork	=	true;
        }	else	{
            ActivityCompat.requestPermissions(this.getActivity(),	new	String[]	{android.Manifest
                            .permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View v)	{
                if	(isStartRecording)	{
                    recordButton.setText("Stop	recording");
                    playButton.setEnabled(false);
                    startRecording();
                }	else	{
                    recordButton.setText("Start	recording");
                    playButton.setEnabled(true);
                    stopRecording();
                }
                isStartRecording	=	!isStartRecording;
            }
        });
        playButton.setOnClickListener(new	View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    playButton.setText("Stop	playing");
                    recordButton.setEnabled(false);
                    startPlaying();
                } else {
                    playButton.setText("Start	playing");
                    recordButton.setEnabled(false);
                    stopPlaying();
                }
                isStartPlaying = !isStartPlaying;
            }
        });

        return root;
    }
    private	void startRecording() {
        recorder	=	new	MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try	{
            recorder.prepare();
        }	catch	(IOException e)	{
            Log.e(TAG,	"prepare()	failed");
        }
        recorder.start();
    }
    private	void	stopRecording()	{
        recorder.stop();
        recorder.release();
        recorder	=	null;
    }
    private	void	startPlaying()	{
        player	=	new	MediaPlayer();
        try	{
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        }	catch	(IOException	e)	{
            Log.e(TAG,	"prepare()	failed");
        }
    }
    private	void	stopPlaying()	{
        player.release();
        player	=	null;
    }
    @Override
    public	void	onRequestPermissionsResult(int	requestCode, @NonNull String[]	permissions,
                                                 @NonNull	int[] grantResults)	{
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        switch	(requestCode){
            case	REQUEST_CODE_PERMISSION:
                isWork		=	grantResults[0]	==	PackageManager.PERMISSION_GRANTED;
                break;
        }
        if	(!isWork	)	getActivity();
    }
}