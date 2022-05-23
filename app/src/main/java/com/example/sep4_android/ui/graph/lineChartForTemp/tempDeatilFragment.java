package com.example.sep4_android.ui.graph.lineChartForTemp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.sep4_android.databinding.FragmentHumidityDetailBinding;
import com.example.sep4_android.model.persistence.entities.Measurement;
import com.example.sep4_android.ui.graph.DesignForGraph.Design;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class tempDeatilFragment extends Fragment {

    private LineViewModelImpl viewModel;
    private FragmentHumidityDetailBinding binding;
    private LineChart lineChart;
    private Design design;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(LineViewModelImpl.class);
        binding = FragmentHumidityDetailBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        design = new Design();
        bindings();
        observers();
        viewModel.findAllHealthDataByDevice();
        return root;
    }

    private void observers() {
        viewModel.getAllHealthDataByDevice().observe(getViewLifecycleOwner(), measurements -> {
            if (measurements != null) {
                ArrayList<Entry> tempMesurements = new ArrayList<>();
                int i =0;
                double sum =0;
                for (Measurement measurement:measurements)
                {
                    i++;
                    sum= measurement.getTemperature() + sum;
                    tempMesurements.add(new Entry(i, (float) measurement.getTemperature()));
                }
                design.setAvg(lineChart, (float) average(sum,i));
                inputDataToChart(tempMesurements);
                //fixme Timestamp skal bruges på x istedet for 1 ,2 og 3
            }
        });}




    private void bindings() {
        lineChart = binding.lcDetailHumidity;
    }



    private void inputDataToChart(ArrayList<Entry> test) {
        LineDataSet lineDataSet = new LineDataSet(test, "Tempature");
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        design.lineChartDesign(lineChart);
        design.lineDataSet(lineDataSet);
        lineDataSet.setDrawCircles(true);




    }
    private double average(double b,int a )
    {
        double avg = b/a;
        System.out.println("Her får vi average fra metoden average fra temp  "+avg);
        lineChart.setAlpha((float) avg);
        return avg;
    }

}