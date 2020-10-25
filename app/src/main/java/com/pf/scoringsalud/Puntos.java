package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;

import java.util.ArrayList;

public class Puntos extends AppCompatActivity implements OnBarClickedListener {

    private ChartProgressBar mChart;
    private ChartProgressBar mChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        final ArrayList<BarData> dataList = new ArrayList<>();
        final ArrayList<BarData> dataList2 = new ArrayList<>();
        //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        BarData data = new BarData("Lun", 3.4f, "3.4€");
        dataList.add(data);
        data = new BarData("Mar", 8f, "8€");
        dataList.add(data);
        data = new BarData("Mie", 1.8f, "1.8€");
        dataList.add(data);
        data = new BarData("Jue", 7.3f, "7.3€");
        dataList.add(data);
        data = new BarData("Vie", 6.2f, "6.2€");
        dataList.add(data);
        data = new BarData("Sab", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Dom", 1.3f, "3.3€");
        dataList.add(data);


        mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

        mChart.setDataList(dataList);
        mChart.build();
        mChart.setOnBarClickedListener(this);










        BarData dataM = new BarData("Ene", 3.4f, "3.4€");
                dataList2.add(dataM);

                dataM = new BarData("Jul", 4.3f, "3.3€");
                dataList2.add(dataM);
                dataM = new BarData("Ago", 7.3f, "3.3€");
                dataList2.add(dataM);
                dataM = new BarData("Sep", 2.3f, "3.3€");
                dataList2.add(dataM);
                dataM = new BarData("Oct", 1.3f, "3.3€");
                dataList2.add(dataM);
                dataM = new BarData("Nov", 8.3f, "3.3€");
                dataList2.add(dataM);
                dataM = new BarData("Dic", 3.3f, "3.3€");
                dataList2.add(dataM);


                mChart2 = (ChartProgressBar) findViewById(R.id.ChartProgressBar2);

                mChart2.setDataList(dataList2);
                mChart2.build();
                mChart2.setOnBarClickedListener(this);












    }

    @Override
    public void onBarClicked(int index) {
        Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
    }




}