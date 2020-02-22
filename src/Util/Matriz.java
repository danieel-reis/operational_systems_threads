/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Random;

/**
 *
 * @author daniel
 */
public class Matriz {
    //SO gasta tempo escalonando e criando threads
    /*
     * Classe criada para realizar a manipulação de matrizes e a impressão na tela.Desse modo, recebe-se
     * o tamanho da matriz e o intervalo de valores que pode estar contidos nela [inicio, limite]. Então
     * gera-se uma matriz com valores aleatórios contidos dentro desse intervalo.
     */
    
    public int[][] geraMatriz(int x, int y, int inicio, int limite) {
        int [][]matriz = new int[x][y];
        Random r = new Random();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                matriz[i][j] = r.nextInt(limite) + inicio;
            }
        }
        return matriz;
    }
    
    public void imprimeMatriz(int [][]matriz, int x, int y, String nomedamatriz) {
        System.out.println("******************** MATRIZ " + nomedamatriz.toUpperCase() + " ********************");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
