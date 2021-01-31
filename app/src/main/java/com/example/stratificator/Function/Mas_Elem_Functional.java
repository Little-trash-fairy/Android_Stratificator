package com.example.stratificator.Function;

import android.content.Context;

import com.example.stratificator.classes.Condition_elem;
import com.example.stratificator.classes.Elem;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.stratificator.view.MainActivity.Mas_elem;
import static com.example.stratificator.view.MainActivity.Mas_elem_condition;
import static com.example.stratificator.view.MainActivity.Mas_elem_view;

public class Mas_Elem_Functional {

    @NotNull
    public static ArrayList<Elem> Create_Mass(Context context) {
        ArrayList<Elem> mas_elem = new ArrayList<>();
        try (InputStreamReader File_reader = new InputStreamReader(context.getAssets().open("Data_elem.txt"))) {
            BufferedReader reader = new BufferedReader(File_reader);
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] wordsArr = line.split(" ");
                    mas_elem.add(new Elem
                            (
                                    Double.parseDouble(wordsArr[0]),
                                    Double.parseDouble(wordsArr[1])
                            ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mas_elem;
    }

    public static void Mas_view_elem_creator() {
        Mas_elem_view.clear();
        for (Condition_elem elem_cond : Mas_elem_condition) {
            ArrayList<Elem> buf = new ArrayList<>();

            Mas_elem.forEach(elem ->
            {
                double size = elem.getSize();
                if ((size <= elem_cond.getTop()) &&
                        (size >= elem_cond.getDown())) {
                    buf.add(new Elem(elem.getElem(), elem.getSize()));
                }
            });
            Mas_elem_view.add(buf);
        }
    }
}
