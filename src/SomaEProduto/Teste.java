package SomaEProduto;

import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        int limite = (new Scanner(System.in)).nextInt();
        Thread[] threads = new Thread[2];

        if (limite < 0) {
            System.err.println("Entrada precisa ser >= 0");
        } else {
            Somatorio soma = new Somatorio(limite);
            Produtorio produto = new Produtorio(limite);
            threads[0] = new Thread(soma);
            threads[1] = new Thread(produto);

            for (int i = 0; i < 2; i++) {
                threads[i].start();
            }

            for (int i = 0; i < 2; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException ie) {
                }
            }

            System.out.println("Somatorio de [1, " + limite + "]: " + soma.getResultado());
            System.out.println("Produtorio de [1, " + limite + "]: " + produto.getResultado());
        }
    }

}
