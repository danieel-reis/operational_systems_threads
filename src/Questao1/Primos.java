/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao1;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Primos implements Runnable {

    private int inicio;
    private int limite;
    private ArrayList<Integer> primos;

    public Primos(int inicio, int limite) {
        this.inicio = inicio;
        this.limite = limite;
        this.primos = new ArrayList<Integer>();
    }

    /*
     * Testa-se se cada número do intervalo é primo. Caso ele seja primo, será adicionado ao array de primos
     * daquele dado intervalo.
     */
    public void run() {
        for (int i = inicio; i <= limite; i++) {
//           primos.add(i);
            boolean primo = verificaPrimo(i);
            if(primo) {
                primos.add(i);
            }
        }
    }

    public boolean verificaPrimo(int numero) {
        boolean primo = true;

        if (numero == 0 || numero == 1) {
            primo = false;
        } else {
            for (int i = 2; i < numero; i++) {
                if ((numero % i) == 0) {
                    primo = false;
                    break;
                }
            }
        }
        return primo;
    }

    public ArrayList getResultado() {
        return primos;
    }
}
