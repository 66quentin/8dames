package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;


public class Case extends androidx.appcompat.widget.AppCompatImageButton {
    private int colonne;
    private int ligne;

public Case(Context context, int colonne, int ligne) {
        super(context);

        this.colonne = colonne;
        this.ligne= ligne;

        if ((ligne + colonne) % 2 == 0) {
            this.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            this.setBackgroundColor(Color.parseColor("#00ffff"));
        }
    }
    public  int getColonne() { return colonne; }
    public int getLigne() { return ligne; }
    public void dessiner_reine(int dessiner) {
        if (dessiner==1) {
            setImageResource(R.drawable.reine);
        } else {
            setImageResource(0);
        }
    }
}
