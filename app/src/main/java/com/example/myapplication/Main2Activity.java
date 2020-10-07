package com.example.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;





public class Main2Activity extends AppCompatActivity {


    private Case[][] grille;

    private int en_cours;

    private Jeu jeu;

    MediaPlayer mediaPlayer;

    Chronometer simpleChronometer;

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mediaPlayer = MediaPlayer.create(this, R.raw.la_musique);
        jeu = new Jeu();
        en_cours = 1;

        creer_grille();
        dessiner_grille();


        simpleChronometer = findViewById(R.id.simpleChronometer);
        simpleChronometer.start();
        Button restart = (Button) findViewById(R.id.resetChronometer);
        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                simpleChronometer.setBase(SystemClock.elapsedRealtime());

            }
        });



    }






    public void event_raffrichir(View view) {
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        raffrichir_jeu();

    }

    public void event_quitter(View view){


        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }


    public void interface_agagner() {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(this);
        dialogue.setTitle(R.string.Partie_finie)
                .setMessage(R.string.gagner);
        dialogue.setPositiveButton(R.string.Rejouer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                raffrichir_jeu();
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
            }
        });
        dialogue.setNegativeButton(R.string.Fermer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = dialogue.create();
        dialog.show();


    }



    private void creer_grille() {
        int i,j;

        Display affichage= getWindowManager().getDefaultDisplay();
        Point size = new Point();
        affichage.getSize(size);


        int largeur = size.x;
        int zone_clice = largeur/ Jeu.taille_grille;

        GridLayout grillelayout = (GridLayout) findViewById(R.id.grille_grid_layout);

        grille = new Case[Jeu.taille_grille][Jeu.taille_grille];

        for (i=0;i< Jeu.taille_grille;i++) {
            for (j=0;j < Jeu.taille_grille; j++) {
                Case case1 = new Case(this,j,i);
                case1.setOnClickListener(new event_clic_case());

                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
                cellParams.width = zone_clice;
                cellParams.height = zone_clice;

                grillelayout.addView(case1, cellParams);

                grille[j][i] = case1;
            }
        }
    }


    private void dessiner_grille() {
        int i,j;
        for ( i = 0; i < Jeu.taille_grille; i++) {
            for ( j = 0; j < Jeu.taille_grille; j++) {
                grille[j][i].dessiner_reine(jeu.position_actuelle(j,i));
            }
        }


        int b=jeu.get_A();
        if(b==1){

            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(R.string.Echec)
                    .setMessage(R.string.Perdu);
            d.setPositiveButton(R.string.Rejouer, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    raffrichir_jeu();
                    simpleChronometer.setBase(SystemClock.elapsedRealtime());
                    simpleChronometer.start();
                    jeu.setA(0);
                }
            });
            d.setNegativeButton(R.string.Quitter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    System.exit(0);
                    jeu.setA(0);
                }
            });

            AlertDialog dial = d.create();
            dial.show();
        }
    }

    private void interface_affecter_reine(int lig, int col) {
        jeu.affecter_reine(lig,col);
        dessiner_grille();

        if (jeu.a_gagner()) {
            interface_agagner();
            simpleChronometer.stop();
            en_cours = 0;

        }
    }

    private void  raffrichir_jeu() {
        jeu = new Jeu();
        en_cours = 1;
        dessiner_grille();



    }


    private class event_clic_case implements ImageButton.OnClickListener {
        @Override
        public void onClick(View view) {
            if (en_cours==1) {
                Case case1 = (Case) view;
                interface_affecter_reine(case1.getColonne(), case1.getLigne());
            }
        }
    }
}

