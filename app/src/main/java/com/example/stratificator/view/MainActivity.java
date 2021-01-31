package com.example.stratificator.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stratificator.R;
import com.example.stratificator.classes.Condition_elem;
import com.example.stratificator.classes.Elem;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.stratificator.Function.Mas_Elem_Functional.Create_Mass;
import static com.example.stratificator.Function.Mas_Elem_Functional.Mas_view_elem_creator;
import static com.example.stratificator.Function.Mas_Point_Creator.Create_point_mass;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<Elem> Mas_elem;
    public static ArrayList<ArrayList<Elem>> Mas_elem_view;
    public static ArrayList<View> Mas_condition_view;
    public static ArrayList<Condition_elem> Mas_elem_condition;
    public static ArrayList<PointsGraphSeries<DataPoint>> Mas_elem_point_into_two_dim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mas_elem = Create_Mass(getApplicationContext());
        Mas_elem_view = new ArrayList<>();
        Mas_condition_view = new ArrayList<>();
        Mas_elem_condition = new ArrayList<>();
        Mas_elem_point_into_two_dim = new ArrayList<>();

        findViewById(R.id.create_condition_button).setOnClickListener(this);
        findViewById(R.id.refresh_button).setOnClickListener(this);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.create_condition_button: {
                //Search for a place to add to it
                LinearLayout linear = findViewById(R.id.сondition_setting_space);

                //Creating an element of the LinereLayout type according to the template
                final View view = Create_condition(getLayoutInflater());

                //Adding a condition to the array
                Mas_condition_view.add(view);

                //Adding a condition to the screen
                linear.addView(view);
                break;
            }
            case R.id.refresh_button: {
                if (Mas_condition_view != null && Mas_condition_view.size() != 0) {
                    Mas_elem_condition.clear();
                    for (View edit_view : Mas_condition_view) {
                        try {
                            String text = ((EditText) edit_view.findViewById(R.id.cond_edittext))
                                    .getText()
                                    .toString();

                            if ((!text.matches("^[0]{1}[.]{1}[0-9]{1,6}[-]{1}[0-1]{1}[.]{1}[0-9]{1,6}$"))) {
                                throw new Exception("Invalid condition set");
                            } else {
                                String[] spliter = text.split("-");
                                float buf_elem_top = Float.parseFloat(spliter[1]);
                                float buf_elem_dow = Float.parseFloat(spliter[0]);

                                if (Mas_elem_condition.stream().noneMatch(
                                        elem ->
                                                elem.getDown() == buf_elem_dow ||
                                                        elem.getTop() == buf_elem_dow ||
                                                        elem.getDown() == buf_elem_top ||
                                                        elem.getTop() == buf_elem_top)) {
                                    Mas_elem_condition.add(
                                            new Condition_elem(
                                                    buf_elem_top,
                                                    buf_elem_dow));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (Mas_elem_condition.size() == 0) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Correctly entered conditions - 0",
                                Toast.LENGTH_SHORT);
                        break;
                    }
                    else {
                        Mas_view_elem_creator();
                        Create_point_mass();
                        GraphView graph = findViewById(R.id.graph);
                        graph.removeAllSeries();
                        Mas_elem_point_into_two_dim.forEach(graph::addSeries);

                        // enable scaling and scrolling
                        graph.getViewport().setScalable(true);
                        graph.getViewport().setScalableY(true);
                    }
                } else {
                    ((GraphView)findViewById(R.id.graph)).removeAllSeries();
                    Toast.makeText(
                            getApplicationContext(),
                            "Enter a selection condition",
                            Toast.LENGTH_SHORT);
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    public View Create_condition(@NotNull LayoutInflater inflater) {
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.condition_template, null);

        //Adding a click event handler to the delete button created using the template
        view.findViewById(R.id.cond_del_btn).setOnClickListener(
                v -> {
                    try {
                        //getting the parent view and deleting it
                        ((LinearLayout) view.getParent()).removeView(view);
                        //Deleting the same element from the array
                        Mas_condition_view.remove(view);
                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                });
        return view;
    }
}