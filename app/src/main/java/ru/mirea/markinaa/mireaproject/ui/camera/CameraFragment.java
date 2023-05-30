package ru.mirea.markinaa.mireaproject.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.markinaa.mireaproject.R;
import ru.mirea.markinaa.mireaproject.databinding.FragmentCameraBinding;
import ru.mirea.markinaa.mireaproject.databinding.FragmentGalleryBinding;
import ru.mirea.markinaa.mireaproject.ui.gallery.GalleryFragment;


public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;

    private Uri imageUri;
    public Boolean isWork = true;
    private	static	final	int REQUEST_CODE_PERMISSION = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCameraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int	cameraPermissionStatus	=	ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.CAMERA);
        int	storagePermissionStatus	=	ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if	(cameraPermissionStatus	==	PackageManager.PERMISSION_GRANTED	&&
                storagePermissionStatus ==	PackageManager.PERMISSION_GRANTED)	{
            isWork	=	true;
        }	else	{
            ActivityCompat.requestPermissions(this.getActivity(),	new	String[]	{android.Manifest
                            .permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }

       try {
            File	photoFile	=	createImageFile();
            String	authorities	=	getActivity().getApplicationContext().getPackageName()	+
                    ".fileprovider";
            imageUri	=	FileProvider.getUriForFile(this.getContext(),	authorities,
                    photoFile);
       } catch (IOException e) {
            e.printStackTrace();
       }

       try {
            createImageFile();
       } catch (IOException e) {
            e.printStackTrace();
       }
       ActivityResultCallback<ActivityResult> callback	=	new	ActivityResultCallback<ActivityResult>()	{
            @Override
            public	void	onActivityResult(ActivityResult	result)	{
                if	(result.getResultCode()	==	Activity.RESULT_OK)	{
                    Intent data	=	result.getData();
                    binding.imageView4.setImageURI(imageUri);
                }
            }
       };
        ActivityResultLauncher<Intent> cameraActivityResultLauncher	=		registerForActivityResult(
                new	ActivityResultContracts.StartActivityForResult(),
                callback);
        binding.button2.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                Intent	cameraIntent	=	new	Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if	(isWork)	{
                    try	{
                        File	photoFile	=	createImageFile();
                        String	authorities	=	getActivity().getApplicationContext().getPackageName()	+
                                ".fileprovider";
                        imageUri	=	FileProvider.getUriForFile(CameraFragment.this.getContext(),
                                authorities,	photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,	imageUri);
                        cameraActivityResultLauncher.launch(cameraIntent);
                    }	catch	(IOException	e)	{
                        e.printStackTrace();
                    }
                }
            }
        });

        return root;
    }
    private File createImageFile()	throws IOException {
        String	timeStamp	=	new SimpleDateFormat("yyyyMMdd_HHmmss",	Locale.ENGLISH)
                .format(new Date());
        String	imageFileName	=	"IMAGE_"	+	timeStamp	+	"_";
        File	storageDirectory	=	getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return	File.createTempFile(imageFileName,	".jpg",	storageDirectory);
    }
    @Override
    public	void onRequestPermissionsResult(int	requestCode, @NonNull String[]	permissions,
                                              @NonNull	int[]	grantResults)	{
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        switch	(requestCode){
            case	REQUEST_CODE_PERMISSION:
                isWork		=	grantResults[0]	==	PackageManager.PERMISSION_GRANTED;
                break;
        }
        if	(!isWork){
            getActivity().finish();
        }
    }
}