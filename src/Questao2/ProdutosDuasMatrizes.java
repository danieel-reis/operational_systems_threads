/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao2;

/**
 *
 * @author daniel
 */
public class ProdutosDuasMatrizes implements Runnable {

    private int k, linha, coluna, resultado;
    private int matrizA[][], matrizB[][];

    public ProdutosDuasMatrizes(int k, int matrizA[][], int matrizB[][], int linha, int coluna) {
        this.k = k;
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.linha = linha;
        this.coluna = coluna;
    }

    public void run() {
        multiplica_linhaXcoluna();
    }

    /*
     * Cada thread faz a multiplicação de todos valores de uma linha x coluna específica, e soma-os.
     * Por fim, esse resultado obtido representa a posição [linha][coluna] da matriz resultado.
     */
    public void multiplica_linhaXcoluna() {
        resultado = 0;
        for (int i = 0; i < k; i++) {
            resultado += (matrizA[linha][i] * matrizB[i][coluna]);
//            System.out.println("   " + matrizA[linha][i] + " * " + matrizB[i][coluna]);
//            System.out.println("---");
        }
    }

    public int getResultado() {
        return resultado;
    }
}
