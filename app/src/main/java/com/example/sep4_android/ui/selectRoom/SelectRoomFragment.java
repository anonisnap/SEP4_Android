package com.example.sep4_android.ui.selectRoom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_android.databinding.FragmentSelectRoomBinding;
import com.example.sep4_android.model.persistence.entities.Device;
import com.example.sep4_android.ui.roomRecycler.DeviceAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SelectRoomFragment extends Fragment {

    private SelectRoomViewModel viewModel;
    private FragmentSelectRoomBinding binding;
    private RecyclerView recyclerView;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SelectRoomViewModelImpl.class);
        binding = FragmentSelectRoomBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        Log.d("Virker dette HMMM", "setText: TEST");
        setText();

        return root;
    }

    private void setText() {
        recyclerView = binding.rv;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Soter devies uden rum fra
        MutableLiveData<List<Device>> liste = new MutableLiveData();

        viewModel.getAllDevices().observe(getViewLifecycleOwner(), devices -> {
            Log.d("Virker dette HMMM", "setText: TEST" + devices);
            List tmp = new ArrayList();
            for (Device i : devices) {

                if (i.getRoomName() != null && !(i.getRoomName().equals("")) && !(i.getRoomName().equals("def"))) {
                    tmp.add(i);
                }
            }
            liste.postValue(tmp);
        });

        liste.observe(getViewLifecycleOwner(), devices -> {
            DeviceAdapter adapter = new DeviceAdapter(devices);
            recyclerView.setAdapter(adapter);

            adapter.setOnClickListener(device -> {
                viewModel.setSelectedDevice(device);

                Snackbar.make(getView(), device.getRoomName() + " selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Navigation.findNavController(root).navigate(R.id.nav_healthInspection);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}