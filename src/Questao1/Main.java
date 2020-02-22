/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao1;

import Graficos.SerieLinha;
import Util.SelecionaCaminhoArquivo;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException, DocumentException {
        System.out.println("**************************** MENU ****************************");
        System.out.println("Para encontrar a quantidade de primos no intervalo de 1 até n, digite: ");
        System.out.println("1 - Usuário deseja entrar com o intervalo e a quantidade de threads utilizadas");
        System.out.println("2 - Usuário deseja entrar com o intervalo e o programa irá executar com 1,2,3,4,5,6,7,8,9 e 10 threads");
        System.out.println("3 - Programa irá gerar os intervalos e irá executar com 1,2,3,4,5,6,7,8,9 e 10 threads");
        System.out.println("4 - Sair ");
        System.out.println("Opção: ");
        int opc = (new Scanner(System.in)).nextInt();
        switch (opc) {
            case 1:
                execucaoUsuario();
                break;
            case 2:
                geraRelatorio();
                break;
            case 3:
                geraMultiplosRelatorios();
                break;
        }
    }

    /*
     * Metodo criado para gerar automaticamente o relatório, ou seja, encontrar todos números primos existentes
     * no intervalo de 1 até o número digitado pelo usuário, e realizar o procedimento utilizando a quantidade de threads
     * definida pelo usuario. Desse modo, gera um arquivo contendo a execução do processo e o resultado final obtido.
     */
    public static void execucaoUsuario() throws IOException, DocumentException {
        int inicio, limite;
        System.out.println("Digite um número inteiro positivo: ");
        int numero = (new Scanner(System.in)).nextInt();
        System.out.println("Digite a quantidade de threads: ");
        int qtd = (new Scanner(System.in)).nextInt();
        Thread[] threads = new Thread[qtd];

        if (numero < 0) {
            System.err.println("Entrada precisa ser >= 0");
        } else {
            ArrayList<Primos> primo = new ArrayList<Primos>();
            inicio = 0;
            limite = numero / qtd;
            int tam = numero / qtd;

            for (int i = 0; i < qtd; i++) {
                primo.add(new Primos(inicio, limite));
                inicio = limite + 1;
                limite += tam;
                if ((numero - limite) < tam) {
                    limite = numero;
                }
            }

            for (int i = 0; i < qtd; i++) {
                threads[i] = new Thread(primo.get(i));
            }

            long horaInicioLong = System.currentTimeMillis();
            for (int i = 0; i < qtd; i++) {
                threads[i].start();
            }

            for (int i = 0; i < qtd; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException ie) {
                }
            }

            long tempodeexecucao = (System.currentTimeMillis() - horaInicioLong);

            // Imprime na tela
            System.out.println("Primos de [1, " + numero + "]");
            for (int i = 0; i < qtd; i++) {
                System.out.println("Thread " + (i + 1) + ": " + primo.get(i).getResultado());
            }

            // Seleciona o local para salvar o arquivo
            SelecionaCaminhoArquivo select = new SelecionaCaminhoArquivo();
            String filePath = select.selectPaste();

            String nomedoarq;
            do {
                nomedoarq = JOptionPane.showInputDialog(null, "Digite o nome para salvar o arquivo?", "Pergunta", JOptionPane.PLAIN_MESSAGE);
            } while (nomedoarq == null || nomedoarq == "");
            CriaPDF escreve = new CriaPDF();
            String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

            // Gera um arquivo pdf
            escreve.geraPdf(primo, filePath, nomedoarq, numero, qtd, data, tempodeexecucao);
        }
    }

    /*
     * Metodo criado para gerar automaticamente o relatório, ou seja, encontrar todos números primos existentes
     * no intervalo de 1 até o número digitado pelo usuário, e realizar o procedimento utilizando 1 thread, 2, 3 até 10
     * threads, para que se possa comparar o tempo de execução desse mesmo valor com o uso de quantidades de threads
     * diferentes. Desse modo, gera cada arquivo individual contendo a execução do processo, e ao final de tudo gera
     * um arquivo contendo uma tabela apresentando quantidade de threads X tempo de execução, e o resultado final obtido.
     */
    public static void geraRelatorio() throws IOException, DocumentException {
        int inicio, limite;
        System.out.println("Digite um número inteiro positivo: ");
        int numero = (new Scanner(System.in)).nextInt();
        // Seleciona o local para salvar o arquivo
        SelecionaCaminhoArquivo select = new SelecionaCaminhoArquivo();
        String filePath = select.selectPaste();
        ArrayList<Primos> primo = new ArrayList<Primos>();
        ArrayList<Long> tempos = new ArrayList<Long>();

        for (int qtd = 1; qtd <= 10; qtd++) {
            Thread[] threads = new Thread[qtd];

            if (numero < 0) {
                System.err.println("Entrada precisa ser >= 0");
            } else {
                primo = null;
                primo = new ArrayList<Primos>();
                inicio = 0;
                limite = numero / qtd;
                int tam = numero / qtd;

                for (int i = 0; i < qtd; i++) {
                    primo.add(new Primos(inicio, limite));
                    inicio = limite + 1;
                    limite += tam;
                    if ((numero - limite) < tam) {
                        limite = numero;
                    }
                }

                for (int i = 0; i < qtd; i++) {
                    threads[i] = new Thread(primo.get(i));
                }

                long horaInicioLong = System.currentTimeMillis();
                for (int i = 0; i < qtd; i++) {
                    threads[i].start();
                }

                for (int i = 0; i < qtd; i++) {
                    try {
                        threads[i].join();
                    } catch (InterruptedException ie) {
                    }
                }

                long tempodeexecucao = (System.currentTimeMillis() - horaInicioLong);
                tempos.add(tempodeexecucao);

                // Imprime na tela
                System.out.println("Primos de [1, " + numero + "]");
                for (int i = 0; i < qtd; i++) {
                    System.out.println("Thread " + (i + 1) + ": " + primo.get(i).getResultado());
                }
                System.out.println("Tempo de execução em milisegundos: " + tempodeexecucao);

                String nomedoarq = "Questao_1_Execucao_" + qtd + "_Threads";
                CriaPDF escreve = new CriaPDF();
                String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

                // Gera um arquivo pdf individual
//                escreve.geraPdf(primo, filePath, nomedoarq, numero, qtd, data, tempodeexecucao);
            }
        }

        String nomedoarq = "Questao_1_Execucao_1_a_10_Threads";
        CriaPDF escrevee = new CriaPDF();
        String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

        // Gera grafico de relatorio
        String name_img = "grafico.png";
        int dimension_x = 625;
        int dimension_y = 400;
        String title = "Encontra números primos no intervalo de 1 a " + numero;
        String nome_x = "Threads";
        String nome_y = "Milisegundos";
        ArrayList<Integer> qtd_threads = new ArrayList<Integer>();
        for (int g = 1; g <= 10; g++) {
            qtd_threads.add(g);
        }
        final SerieLinha demo = new SerieLinha(name_img, title, nome_x, nome_y, qtd_threads, tempos, dimension_x, dimension_y);

        // Gera um arquivo pdf total
        escrevee.geraRelatorioPdf(primo, filePath, nomedoarq, numero, data, tempos);

        // Mostra o gráfico na tela
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    /*
     * Classe criada para gerar automaticamente o relatório, ou seja, encontrar todos números primos existentes
     * no intervalo de 1 até o número gerado automaticamente (entre 10000 e 50000), e realizar o procedimento utilizando 1 thread, 2, 3 até 10
     * threads, para que se possa comparar o tempo de execução desse mesmo valor com o uso de quantidades de threads
     * diferentes. Desse modo, gera um arquivo contendo uma tabela apresentando quantidade de threads X tempo de execução,
     * e o resultado final obtido. Dentro desse arquivo, coloca a execução com 10 intervalos.
     */
    public static void geraMultiplosRelatorios() throws IOException, DocumentException {
        // Seleciona o local para salvar o arquivo
        SelecionaCaminhoArquivo select = new SelecionaCaminhoArquivo();
        String filePath = select.selectPaste();
        ArrayList<Primos> primo = new ArrayList<Primos>();
        ArrayList<Long> tempos = new ArrayList<Long>();
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        ArrayList<Long> times = new ArrayList<Long>();

        // Preenche o array de numeros máximos dos intervalos.
        for (int a = 0; a < 10; a++) {
            Random r = new Random();
            int num = r.nextInt(50000) + 500;
            numeros.add(num);
        }

        for (int a = 0; a < 10; a++) {
            times = null;
            times = new ArrayList<Long>();
            int inicio, limite;

            for (int qtd = 1; qtd <= 10; qtd++) {
                Thread[] threads = new Thread[qtd];
//                System.out.println(numeros.get(a));
                if (numeros.get(a) < 0) {
                    System.err.println("Entrada precisa ser >= 0");
                } else {
                    primo = null;
                    primo = new ArrayList<Primos>();
                    inicio = 0;
                    limite = numeros.get(a) / qtd;
                    int tam = numeros.get(a) / qtd;

                    for (int i = 0; i < qtd; i++) {
                        primo.add(new Primos(inicio, limite));
                        inicio = limite + 1;
                        limite += tam;
                        if ((numeros.get(a) - limite) < tam) {
                            limite = numeros.get(a);
                        }
                    }

                    for (int i = 0; i < qtd; i++) {
                        threads[i] = new Thread(primo.get(i));
                    }

                    long horaInicioLong = System.currentTimeMillis();
                    for (int i = 0; i < qtd; i++) {
                        threads[i].start();
                    }

                    for (int i = 0; i < qtd; i++) {
                        try {
                            threads[i].join();
                        } catch (InterruptedException ie) {
                        }
                    }

                    long tempodeexecucao = (System.currentTimeMillis() - horaInicioLong);
                    tempos.add(tempodeexecucao);
                    times.add(tempodeexecucao);

                    // Imprime na tela
                    System.out.println("Primos de [1, " + numeros.get(a) + "]");
                    for (int i = 0; i < qtd; i++) {
                        System.out.println("Thread " + (i + 1) + ": " + primo.get(i).getResultado());
                    }
                    System.out.println("Tempo de execução em milisegundos: " + tempodeexecucao);
                }
            }
            // Gera grafico de relatorio
            String name_img = "grafico" + (a+1) + ".png";
            int dimension_x = 625;
            int dimension_y = 400;
            String title = "Encontra números primos no intervalo de 1 a " + numeros.get(a);
            String nome_x = "Threads";
            String nome_y = "Milisegundos";
            ArrayList<Integer> qtd_threads = new ArrayList<Integer>();
            for (int g = 1; g <= 10; g++) {
                qtd_threads.add(g);
            }
            new SerieLinha(name_img, title, nome_x, nome_y, qtd_threads, times, dimension_x, dimension_y);
        }
        String nomedoarq = "Questao_1_Multiplas_Execucoes_1_a_10_Threads";
        CriaPDF escrevee = new CriaPDF();
        String data = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));

        // Gera um arquivo pdf total
        escrevee.geraMultiplosRelatoriosPdf(primo, filePath, nomedoarq, numeros, data, tempos);

    }
}
