package com.example.stratificator.Function;

import android.graphics.Color;

import com.example.stratificator.classes.Elem;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import static com.example.stratificator.view.MainActivity.Mas_elem_point_into_two_dim;
import static com.example.stratificator.view.MainActivity.Mas_elem_view;

public class Mas_Point_Creator {

    public static void Create_point_mass() {
        Mas_elem_point_into_two_dim.clear();
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        for (ArrayList<Elem> elem_mas : Mas_elem_view) {
            try {
                PointsGraphSeries<DataPoint> buf = new PointsGraphSeries<>();

                buf.setShape(PointsGraphSeries.Shape.POINT);
                buf.setColor(Color.rgb(r, g, b));

                elem_mas.forEach(elem ->
                        buf.appendData(
                                new DataPoint(elem.getElem(), elem.getSize()), true, 1000)
                );
                r = rand.nextFloat();
                g = rand.nextFloat();
                b = rand.nextFloat();
                Mas_elem_point_into_two_dim.add(buf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
