/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao2;

import Util.Matriz;
import Graficos.SerieDuasLinhas;
import Util.SelecionaCaminhoArquivo;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String args[]) throws IOException, DocumentException {
        System.out.println("**************************** MENU ****************************");
        System.out.println("Para multiplicar uma matriz A pela matriz B, usando threads para cada linha x coluna e sem usar threads, digite: ");
        System.out.println("1 - Usuário deseja entrar com os tamanhos das matrizes A e B");
        System.out.println("2 - Programa irá gerar os intervalos de forma aleatória");
        System.out.println("3 - Sair ");
        System.out.println("Opção: ");
        int opc = (new Scanner(System.in)).nextInt();
        switch (opc) {
            case 1:
                execucaoUsuario();
                break;
            case 2:
                geraMultiplosRelatorios();
                break;
        }
    }

    /*
     * Método criado para gerar duas matrizes, uma matrizA (m x k) e uma matrizB (k x n). Daí, realiza-se dois procedimentos:
     * Vale ressaltar que nesse caso o usuário entra com os tamanhos da matriz m, k e n.
     * Primeiro, multiplica-se a matrizA pela matrizB utilizando multithreads, de modo que cria-se uma thread para multiplicar cada linha
     * da matrizA por cada coluna da matrizB.
     * Depois, multiplica-se a matrizA pela matrizB utilizando apenas uma única thread.
     * Por fim, gera-se um relatório individual contendo os resultados obtidos nos dois casos, e também um relatório contendo a comparação
     * dos dois tempos de execução de cada um deles, além do resultado final obtido. Vale ressaltar que ambos métodos retornam resultados
     * idênticos.
     */
    public static void execucaoUsuario() throws IOException, DocumentException {
        System.out.println("Para gerar duas matrizes, sendo uma de tamanho M x K e a outra K x N");
        System.out.println("Digite o valor de M: ");
        int m = (new Scanner(System.in)).nextInt();
        System.out.println("Digite o valor de K: ");
        int k = (new Scanner(System.in)).nextInt();
        System.out.println("Digite o valor de N: ");
        int n = (new Scanner(System.in)).nextInt();
        System.out.println("Digite os valores limites da matriz (Pode ter valores entre ): ");

        int inicio = 0;
        int limite = 0;
        do {
            System.out.println("Inicio: ");
            inicio = (new Scanner(System.in)).nextInt();
            System.out.println("Limite: ");
            limite = (new Scanner(System.in)).nextInt();
        } while (limite <= inicio);

        // Seleciona o local para salvar o arquivo
        SelecionaCaminhoArquivo select = new SelecionaCaminhoArquivo();
        String filePath = select.selectPaste();

        Thread[][] threads = new Thread[m][n];

        if (m < 0 || k < 0 || n < 0) {
            System.err.println("Entrada precisa ser >= 0");
        } else {
            Matriz matriz = new Matriz();
            int matrizA[][] = matriz.geraMatriz(m, k, inicio, limite);
            int matrizB[][] = matriz.geraMatriz(k, n, inicio, limite);
            int matrizC[][] = new int[m][n];
            int matrizC1[][] = new int[m][n];

            ProdutosDuasMatrizes[][] produto = new ProdutosDuasMatrizes[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    produto[i][j] = new ProdutosDuasMatrizes(k, matrizA, matrizB, i, j);
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    threads[i][j] = new Thread(produto[i][j]);
                }
            }

            long horaInicioLong = System.currentTimeMillis();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    threads[i][j].start();
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    try {
                        threads[i][j].join();
                    } catch (InterruptedException ie) {
                    }
                }
            }

            long tempodeexecucao = (System.currentTimeMillis() - horaInicioLong);
            System.out.println("Tempo de execução em milisegundos usando multithreads: " + tempodeexecucao);

            // Preenche matrizC para usar o metodo comum
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrizC[i][j] = produto[i][j].getResultado();
                }
            }

            // Imprime na tela
            matriz.imprimeMatriz(matrizA, m, k, "A");
            System.out.println();
            matriz.imprimeMatriz(matrizB, k, n, "B");
            System.out.println();
            matriz.imprimeMatriz(matrizC, m, n, "C");
            System.out.println();

            String nomedoarq = "Questao_2_Execucao_Multithreads";
            CriaPDF escreve = new CriaPDF();
            String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

            // Gera um arquivo pdf individual
            escreve.geraResultadoPdf(1, matrizA, matrizB, produto, filePath, nomedoarq, m, k, n, data, tempodeexecucao);

            // Realiza o procedimento da multiplicacao através de uma unica thread
            System.out.println("***** Multiplicação das duas matrizes por meio de uma única thread *****");
            long horaInicioLong1 = System.currentTimeMillis();
            int resultado;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    resultado = 0;
                    for (int a = 0; a < k; a++) {
                        resultado += (matrizA[i][a] * matrizB[a][j]);
                    }
                    matrizC1[i][j] = resultado;
                }
            }
            long tempodeexecucao1 = (System.currentTimeMillis() - horaInicioLong1);
            System.out.println("Tempo de execução em milisegundos usando uma única thread: " + tempodeexecucao1);

            // Imprime na tela
            matriz.imprimeMatriz(matrizA, m, k, "A");
            System.out.println();
            matriz.imprimeMatriz(matrizB, k, n, "B");
            System.out.println();
            matriz.imprimeMatriz(matrizC1, m, n, "C");
            System.out.println();

            nomedoarq = "Questao_2_Execucao_Unica_thread";
            // Gera um arquivo pdf individual
            escreve.geraResultadoPdf(2, matrizA, matrizB, produto, filePath, nomedoarq, m, k, n, data, tempodeexecucao1);

            nomedoarq = "Questao_2_Execucao_Unica_e_Multi_thread";
            // Gera um arquivo pdf total
            escreve.geraRelatorioPdf(matrizA, matrizB, produto, filePath, nomedoarq, m, k, n, data, tempodeexecucao, tempodeexecucao1);
        }

    }

    /*
     * Método criado para gerar duas matrizes, uma matrizA (m x k) e uma matrizB (k x n). Daí, realiza-se dois procedimentos:
     * Gera-se de forma aleatória os tamanhos m, k e n 10 vezes, e realiza-se 10 execuções. Cada execução, faz: 
     * Primeiro, multiplica-se a matrizA pela matrizB utilizando multithreads, de modo que cria-se uma thread para multiplicar cada linha
     * da matrizA por cada coluna da matrizB.
     * Depois, multiplica-se a matrizA pela matrizB utilizando apenas uma única thread.
     * Por fim, gera-se um relatório individual contendo os resultados obtidos nos dois casos, e também um relatório contendo a comparação
     * dos dois tempos de execução de cada um deles, além do resultado final obtido. Vale ressaltar que ambos métodos retornam resultados
     * idênticos.
     */
    public static void geraMultiplosRelatorios() throws IOException, DocumentException {
        ArrayList<Long> timeexecucao = new ArrayList<Long>();
        ArrayList<Long> timeexecucao1 = new ArrayList<Long>();
        ArrayList<Long> timeexecucao2 = new ArrayList<Long>();
        ArrayList<Integer> M = new ArrayList<Integer>();
        ArrayList<Integer> K = new ArrayList<Integer>();
        ArrayList<Integer> N = new ArrayList<Integer>();

        // Seleciona o local para salvar o arquivo
        SelecionaCaminhoArquivo select = new SelecionaCaminhoArquivo();
        String filePath = select.selectPaste();

        for (int f = 0; f < 10; f++) {
            Random r = new Random();

            // Para gerar duas matrizes, sendo uma de tamanho M x K e a outra K x N
            int m = r.nextInt(1000) + 20;
            int k = r.nextInt(1000) + 20;
            int n = r.nextInt(1000) + 20;

            // Valores limites da matriz (Pode ter valores entre )
            int inicio = 0;
            int limite = 0;
            do {
                inicio = r.nextInt(3000) + 30;
                limite = r.nextInt(3000) + 30;
            } while (limite <= inicio);

            Thread[][] threads = new Thread[m][n];

            if (m < 0 || k < 0 || n < 0) {
                System.err.println("Entrada precisa ser >= 0");
            } else {
                Matriz matriz = new Matriz();
                int matrizA[][] = matriz.geraMatriz(m, k, inicio, limite);
                int matrizB[][] = matriz.geraMatriz(k, n, inicio, limite);
                int matrizC[][] = new int[m][n];
                int matrizC1[][] = new int[m][n];

                ProdutosDuasMatrizes[][] produto = new ProdutosDuasMatrizes[m][n];
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        produto[i][j] = new ProdutosDuasMatrizes(k, matrizA, matrizB, i, j);
                    }
                }

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        threads[i][j] = new Thread(produto[i][j]);
                    }
                }

                long horaInicioLong = System.currentTimeMillis();
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        threads[i][j].start();
                    }
                }

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        try {
                            threads[i][j].join();
                        } catch (InterruptedException ie) {
                        }
                    }
                }

                long tempodeexecucao = (System.currentTimeMillis() - horaInicioLong);
                System.out.println("***** Multiplicação das duas matrizes por meio de multiplas threads *****");
                System.out.println("Tempo de execução em milisegundos usando multithreads: " + tempodeexecucao);

                // Preenche matrizC para usar o metodo comum
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        matrizC[i][j] = produto[i][j].getResultado();
                    }
                }

                // Imprime na tela
//                matriz.imprimeMatriz(matrizA, m, k, "A");
//                System.out.println();
//                matriz.imprimeMatriz(matrizB, k, n, "B");
//                System.out.println();
//                matriz.imprimeMatriz(matrizC, m, n, "C");
//                System.out.println();
                // Realiza o procedimento da multiplicacao através de uma unica thread
                System.out.println("***** Multiplicação das duas matrizes por meio de uma única thread *****");
                long horaInicioLong1 = System.currentTimeMillis();
                int resultado;
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        resultado = 0;
                        for (int a = 0; a < k; a++) {
                            resultado += (matrizA[i][a] * matrizB[a][j]);
                        }
                        matrizC1[i][j] = resultado;
                    }
                }
                long tempodeexecucao1 = (System.currentTimeMillis() - horaInicioLong1);
                System.out.println("Tempo de execução em milisegundos usando uma única thread: " + tempodeexecucao1);

                // Imprime na tela
//                matriz.imprimeMatriz(matrizA, m, k, "A");
//                System.out.println();
//                matriz.imprimeMatriz(matrizB, k, n, "B");
//                System.out.println();
//                matriz.imprimeMatriz(matrizC1, m, n, "C");
//                System.out.println();
                timeexecucao.add(tempodeexecucao);
                timeexecucao.add(tempodeexecucao1);
                timeexecucao1.add(tempodeexecucao);
                timeexecucao2.add(tempodeexecucao1);
                M.add(m);
                K.add(k);
                N.add(n);
            }
        }
        
        String nomedoarq = "Questao_2_Multiplas_Execucoes_UnicaxVarias_threads";
        CriaPDF escreve = new CriaPDF();
        String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

        // Gera grafico de relatorio
        String name_img = "grafico.png";
        int dimension_x = 625;
        int dimension_y = 400;
        String title = "Multiplicação de matrizes";
        String nome_linha1 = "Com Threads";
        String nome_linha2 = "Sem Threads";
        String nome_x = "Execução";
        String nome_y = "Milisegundos";
        ArrayList<String> names = new ArrayList<String>();
        for (int g = 1; g <= 10; g++) {
            String s = "";
            s += g;
            names.add(s);
        }
        final SerieDuasLinhas demo = new SerieDuasLinhas(nome_linha1, nome_linha2, name_img, title, nome_x, nome_y, names, timeexecucao1, timeexecucao2, dimension_x, dimension_y);

        // Gera um arquivo pdf total
        escreve.geraMultiplosRelatoriosPdf(filePath, nomedoarq, M, K, N, data, timeexecucao);

        // Mostra o gráfico na tela
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
