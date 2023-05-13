package ru.mirea.markinaa.mireaproject.ui.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.mirea.markinaa.mireaproject.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment implements SensorEventListener{

    private FragmentSensorBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sensorManager =
                (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return root;
    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorListener) this);
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            binding.textViewAzimuth.setText("Azimuth: " + valueAzimuth);
            binding.textViewPitch.setText("Pitch: " + valuePitch);
            binding.textViewRoll.setText("Roll: " + valueRoll);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}