package ru.mirea.markinaa.mireaproject.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.security.keystore.KeyGenParameterSpec;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.markinaa.mireaproject.R;
import ru.mirea.markinaa.mireaproject.databinding.FragmentMicrophoneBinding;
import ru.mirea.markinaa.mireaproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String mainKeyAlias = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;

        try {
            mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences secureSharedPreferences;
        try {
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "profile",
                    mainKeyAlias,
                    getActivity().getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        String name = secureSharedPreferences.getString("secureName", "Имя");
        String surname = secureSharedPreferences.getString("secureSurname", "Фамилия");
        String secondName = secureSharedPreferences.getString("secureSecondName",
                "Отчество");
        String email = secureSharedPreferences.getString("secureEmail", "Почта");

        binding.editTextName.setText(name);
        binding.editTextSurname.setText(surname);
        binding.editTextSecondName.setText(secondName);
        binding.editTextEmail.setText(email);

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secureSharedPreferences.edit().putString("secureName", binding.editTextName
                        .getText().toString()).apply();
                secureSharedPreferences.edit().putString("secureSurname", binding
                        .editTextSurname.getText().toString()).apply();
                secureSharedPreferences.edit().putString("secureSecondName", binding
                        .editTextSecondName.getText().toString()).apply();
                secureSharedPreferences.edit().putString("secureEmail", binding
                        .editTextEmail.getText().toString()).apply();
            }
        });

        return root;
    }
}