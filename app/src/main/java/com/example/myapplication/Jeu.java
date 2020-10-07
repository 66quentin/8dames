package com.example.myapplication;


class Jeu {
    static final int taille_grille = 8;
    private static final int nb_reines = 8;
    private int [][] positions_reines;
    private int a=0 ;


    Jeu() {
        int i,j;
        positions_reines = new int[taille_grille][taille_grille];
        for(i=0;i<taille_grille;i++){
            for(j=0;j<taille_grille;j++){
                positions_reines[i][j]=0;
            }

        }

    }
    int get_A(){return a;}
    void setA(int a){this.a=a;}
    int position_actuelle(int lig, int col) {
        return positions_reines[lig][col];
    }

    boolean a_gagner() {
        int cpt = 0,i,j;
          for(i=0;i<taille_grille;i++) {
            for (j = 0; j < taille_grille; j++) {
             if(positions_reines[i][j]==1){
                 cpt++;
             }
            }
        }
        if(cpt==nb_reines){
            return true;

        }else return false;
    }


    void affecter_reine(int lig, int col) {

        if (positions_reines[lig][col]==1) {
            positions_reines[lig][col] = 0;
            return;// return a pour but de reapler la methode qui a ete appelé
        }else{
            positions_reines[lig][col] = 1;
            if(verification1()==true){a=1;return;}

            return;
        }

    }

    public  boolean verification1() {
        int i, j, res1, res2, res3, res4, res5, res6;
        for (i = 0; i < 8; i++) {

            res1 = 0;
            res2 = 0;
            res3 = 0;
            res4 = 0;
            res5 = 0;
            res6 = 0;
            for (j = 0; j < 8; j++) {
                if (positions_reines[i][j]==1)
                    res1++;
                if (positions_reines[j][i]==1)
                    res2++;
            }
            for (j = 0; j <=i; j++) {
                if (positions_reines[i - j][j] == 1)
                    res3++;
                if (positions_reines[7 - i + j][j]==1)
                    res5++;
            }
            for (j = 0; j < i; j++) {
                if (positions_reines[8 - i + j][8 - j - 1]==1)
                    res4++;
                if (positions_reines[j][8 - i + j]==1)
                    res6++;
            }
            if (res1 > 1 || res2 > 1 || res3 > 1 || res4 > 1 || res5 > 1 || res6 > 1)
                return true;
        }
        return false;

    }








}
