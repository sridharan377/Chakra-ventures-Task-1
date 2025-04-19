package com.sridharan.chakra;

import static android.widget.Toast.*;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;
import android.widget.Toast.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int MIC_PERMISSION_CODE = 101;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        ImageButton cameraButton = view.findViewById(R.id.camera);
        ImageButton micButton = view.findViewById(R.id.mic);

        cameraButton.setOnClickListener(v -> requestCameraPermission());
        micButton.setOnClickListener(v -> requestMicPermission());
        return view;
    }
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) requireContext(),
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            Toast.makeText(requireContext(), getString(R.string.camera_permission_already_granted), LENGTH_SHORT).show();
        }
    }

    private void requestMicPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) requireContext(),
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MIC_PERMISSION_CODE);
        } else {
            Toast.makeText(requireContext(), getString(R.string.microphone_permission_already_granted), LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeText(requireContext(), getString(R.string.camera_permission_granted), LENGTH_SHORT).show();
            } else {
                makeText(requireContext(), getString(R.string.camera_permission_denied), LENGTH_SHORT).show();
            }
        } else if (requestCode == MIC_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), getString(R.string.microphone_permission_granted), LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), getString(R.string.microphone_permission_denied), LENGTH_SHORT).show();
            }
        }
    }

}