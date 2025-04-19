package com.sridharan.chakra;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class app extends Fragment  {
    private ListView appsListView;
    private ArrayAdapter<String> appAdapter;
    private List<String> appList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app, container, false);

        appsListView = view.findViewById(R.id.appsListView);

        appList = new ArrayList<>();
        appAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, appList);

        appsListView.setAdapter(appAdapter);

        loadInstalledApps();

        return view;
    }

    private void loadInstalledApps() {
        PackageManager pm = requireContext().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        appList.clear();
        for (ApplicationInfo app : apps) {
            appList.add(app.loadLabel(pm).toString());
        }
        appAdapter.notifyDataSetChanged();
    }
}