package ru.mirea.markinaa.mireaproject.ui.createFile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ru.mirea.markinaa.mireaproject.MainActivity;
import ru.mirea.markinaa.mireaproject.R;
import ru.mirea.markinaa.mireaproject.databinding.FragmentCreateFileBinding;
import ru.mirea.markinaa.mireaproject.databinding.FragmentSensorBinding;

public class CreateFileFragment extends Fragment {
    private FragmentCreateFileBinding binding;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "createFile.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateFileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream outputStream;
                try {
                    outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(binding.editText.getText().toString().getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {
            public void run() {
                binding.editText.post(new Runnable() {
                    public void run() {
                        binding.editText.setText(getTextFromFile());
                    }
                });
            }
        }).start();


        return root;
    }
    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = getActivity().openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return null;
    }
}