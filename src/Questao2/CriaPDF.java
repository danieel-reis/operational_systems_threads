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
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CriaPDF {

    /*
     * Classe criada para manipulação de arquivos em pdf.
     */
    public void geraRelatorioPdf(int matrizA[][], int matrizB[][], ProdutosDuasMatrizes matrizC[][], String filePath, String nomedoarq, int m, int k, int n, String data, long tempodeexecucao, long tempodeexecucao1) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
        if (filePath != null) {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + nomedoarq + ".pdf"));
            document.open();

            document.add(new LineSeparator());
            AddCabecalho(document);
            Paragraph p = new Paragraph(" ");
            p.setLeading(5);
            document.add(p);
            document.add(new LineSeparator());
            AddLogo(document);
            p.setLeading(30);
            document.add(p);
            AddTextRelatorio(document, matrizA, matrizB, matrizC, m, k, n, data, tempodeexecucao, tempodeexecucao1);

            document.close();

        }
    }

    public void geraMultiplosRelatoriosPdf(String filePath, String nomedoarq, ArrayList<Integer> m, ArrayList<Integer> k, ArrayList<Integer> n, String data, ArrayList<Long> tempodeexecucao) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
        if (filePath != null) {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + nomedoarq + ".pdf"));
            document.open();

            document.add(new LineSeparator());
            AddCabecalho(document);
            Paragraph p = new Paragraph(" ");
            p.setLeading(5);
            document.add(p);
            document.add(new LineSeparator());
            AddLogo(document);
            p.setLeading(30);
            document.add(p);
            AddTextRelatorios(document, m, k, n, data, tempodeexecucao);
            AddGrafico(document);
            
            document.close();

        }
    }

    public void geraResultadoPdf(int tipo, int matrizA[][], int matrizB[][], ProdutosDuasMatrizes matrizC[][], String filePath, String nomedoarq, int m, int k, int n, String data, long tempodeexecucao) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
        if (filePath != null) {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + nomedoarq + ".pdf"));
            document.open();

            document.add(new LineSeparator());
            AddCabecalho(document);
            Paragraph p = new Paragraph(" ");
            p.setLeading(5);
            document.add(p);
            document.add(new LineSeparator());
            AddLogo(document);
            AddText(tipo, document, matrizA, matrizB, matrizC, m, k, n, data, tempodeexecucao);

            document.close();

        }
    }

    private void AddCabecalho(Document document) throws DocumentException {
        // Adiciona o cabecalho
        Paragraph p = new Paragraph(" ");
        p.setLeading(7);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 13, Font.BOLD);
        p = new Paragraph("UNIVERSIDADE FEDERAL DE OURO PRETO ", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Aluno: Daniel Martins Reis", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Disciplina: CEA437 – Sistemas Operacionais", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Prof. Samuel Souza Brito", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Primeiro Trabalho Prático", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph(" ");
        p.setLeading(7);
        document.add(p);
    }

    private void AddLogo(Document document) throws BadElementException, IOException, DocumentException {
        // Adiciona LOGO
        Image image = Image.getInstance("logoUFOP.jpg");
        image.setAbsolutePosition(475, 700);
        document.add(image);
    }
    
    private void AddGrafico(Document document) throws BadElementException, IOException, DocumentException {
        // Adiciona Grafico
        Font f = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
        Paragraph p = new Paragraph(" ", f);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Gráfico", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(15);
        document.add(p);
        Image figura = Image.getInstance("grafico.png");
        figura.setAlignment(Element.ALIGN_CENTER);
        figura.scaleAbsolute(400f, 250f);
        document.add(figura);
    }

    private void AddText(int tipo, Document document, int matrizA[][], int matrizB[][], ProdutosDuasMatrizes[][] matrizC, int m, int k, int n, String data, long tempodeexecucao) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(30);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);

        if (tipo == 1) {
            p = new Paragraph("Execução multithreads", f);
        } else if (tipo == 2) {
            p = new Paragraph("Execução com uma única thread", f);
        }
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Tempo de execução em milisegundos: " + tempodeexecucao, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Data : " + data, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph(" ", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(10);
        document.add(p);

        p = new Paragraph("********** MATRIZ A **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        String text = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                text += matrizA[i][j] + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

        p = new Paragraph("********** MATRIZ B **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        text = "";
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                text += matrizB[i][j] + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

        p = new Paragraph("********** MATRIZ C **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        text = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                text += matrizC[i][j].getResultado() + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

    }

    private void AddTextRelatorio(Document document, int matrizA[][], int matrizB[][], ProdutosDuasMatrizes[][] matrizC, int m, int k, int n, String data, long tempodeexecucao, long tempodeexecucao1) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(30);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
        p = new Paragraph("Execução multithreads", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Tempo de execução em milisegundos: " + tempodeexecucao, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Execução com uma única thread", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Tempo de execução em milisegundos: " + tempodeexecucao1, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Data : " + data, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph(" ", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(10);
        document.add(p);

        p = new Paragraph("********** MATRIZ A **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        String text = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                text += matrizA[i][j] + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

        p = new Paragraph("********** MATRIZ B **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        text = "";
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                text += matrizB[i][j] + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

        p = new Paragraph("********** MATRIZ C **********", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        text = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                text += matrizC[i][j].getResultado() + " ";
            }
            p = new Paragraph(text, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            text = "";
        }

    }

    private void AddTextRelatorios(Document document, ArrayList<Integer> m, ArrayList<Integer> k, ArrayList<Integer> n, String data, ArrayList<Long> tempodeexecucao) throws DocumentException {
        int j = 0;
        for (int i = 0; i < tempodeexecucao.size(); i += 2, j++) {
            Paragraph p = new Paragraph(" ");
            p.setLeading(30);
            document.add(p);
            Font f = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
            p = new Paragraph("Matriz A: " + m.get(j) + " x " + k.get(j) + "    Matriz B: " + k.get(j) + " x " + n.get(j) + "    Matriz C: " + m.get(j) + " x " + n.get(j), f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            p = new Paragraph("Tempo de execução multithreads em milisegundos: " + tempodeexecucao.get(i), f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            p = new Paragraph("Tempo de execução única thread em milisegundos: " + tempodeexecucao.get(i + 1), f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            p = new Paragraph("Data : " + data, f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
            p = new Paragraph(" ", f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(10);
            document.add(p);
        }
    }
}
