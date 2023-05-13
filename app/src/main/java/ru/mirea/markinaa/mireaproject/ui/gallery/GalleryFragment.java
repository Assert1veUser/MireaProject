package ru.mirea.markinaa.mireaproject.ui.gallery;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import ru.mirea.markinaa.mireaproject.R;
import ru.mirea.markinaa.mireaproject.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private boolean isWork = false;
    private int PermissionCode = 200;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWork) {
                    binding.imageButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable
                            .ic_gallery_fragment_pause,null));
                    binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable
                            .kremlin, null));
                    isWork = true;
                    Intent serviceIntent = new Intent(GalleryFragment.this.getContext(),
                            PlayerService.class);
                    ContextCompat.startForegroundService(GalleryFragment.this.getContext(),	serviceIntent);
                }
                else{
                    binding.imageButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable
                            .ic_play_arrow, null));

                    isWork = false;
                    getActivity().stopService(
                            new	Intent(GalleryFragment.this.getContext(),	PlayerService.class));
                }

            }
        });
        if (ContextCompat.checkSelfPermission(this.getContext(), POST_NOTIFICATIONS) == PackageManager
                .PERMISSION_GRANTED){
            Log.d(GalleryFragment.class.getSimpleName().toString(),	"Разрешения получены");
        }	else	{
            Log.d(GalleryFragment.class.getSimpleName().toString(),	"Нет разрешений!");
            ActivityCompat.requestPermissions(this.getActivity(),	new	String[]{POST_NOTIFICATIONS,
                    FOREGROUND_SERVICE},	PermissionCode);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}