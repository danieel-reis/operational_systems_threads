/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Questao1;

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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
    public void geraRelatorioPdf(ArrayList<Primos> resultados, String filePath, String nomedoarq, int num, String data, ArrayList<Long> tempodeexecucao) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
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
            String intervalo = "[1, " + num + "]";
            AddTable(document, intervalo, tempodeexecucao, 0);
            p.setLeading(15);
            document.add(p);
            document.add(new LineSeparator());
            p.setLeading(15);
            document.add(p);
            AddTextRelatorio(document, resultados, num, data);
            AddGrafico(document);

            document.close();

        }
    }

    public void geraMultiplosRelatoriosPdf(ArrayList<Primos> resultados, String filePath, String nomedoarq, ArrayList<Integer> num, String data, ArrayList<Long> tempodeexecucao) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
        if (filePath != null) {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + nomedoarq + ".pdf"));
            document.open();

            for (int i = 0; i < num.size(); i++) {
                document.add(new LineSeparator());
                AddCabecalho(document);
                Paragraph p = new Paragraph(" ");
                p.setLeading(5);
                document.add(p);
                document.add(new LineSeparator());
                AddLogo(document);
                p.setLeading(30);
                document.add(p);
                AddTextRelatorios(document, num.get(i), data);
                String intervalo = "[1, " + num.get(i) + "]";
                AddTable(document, intervalo, tempodeexecucao, i * 10);
                p.setLeading(15);
                document.add(p);
                // Adiciona Grafico
                Font f = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
                p = new Paragraph(" ", f);
                p.setLeading(25);
                document.add(p);
                p = new Paragraph("Gráficos", f);
                p.setAlignment(Element.ALIGN_CENTER);
                p.setLeading(15);
                document.add(p);
                AddGrafico(document, (i + 1));
                document.newPage();
            }
            document.close();
        }
    }

    public void geraPdf(ArrayList<Primos> resultados, String filePath, String nomedoarq, int num, int threads, String data, long tempodeexecucao) throws IOException, DocumentException, BadElementException, UnsupportedEncodingException, FileNotFoundException {
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
            AddText(document, resultados, num, threads, data, tempodeexecucao);
            AddGrafico(document);

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

    private void AddGrafico(Document document, int grafico) throws BadElementException, IOException, DocumentException {
        Font f = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
        Image figura = Image.getInstance("grafico" + grafico + ".png");
        figura.setAlignment(Element.ALIGN_CENTER);
        figura.scaleAbsolute(400f, 250f);
        document.add(figura);
        Paragraph p = new Paragraph(" ", f);
        p.setLeading(15);
        document.add(p);
    }

    private void AddText(Document document, ArrayList<Primos> resultado, int num, int threads, String data, long tempodeexecucao) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(30);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
        p = new Paragraph("Números primos entre [1, " + num + "]", f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
        p = new Paragraph("Quantidade de threads utilizadas: " + threads, f);
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

        String res = "";
        for (int i = 0; i < resultado.size(); i++) {
            for (int j = 0; j < resultado.get(i).getResultado().size(); j++) {
                res += resultado.get(i).getResultado().get(j) + ",";
            }
        }
        String r[] = res.split(",");

        // Salva um resultado por linha
        for (int i = 0; i < r.length; i++) {
            p = new Paragraph(r[i], f);
            p.setAlignment(Element.ALIGN_LEFT);
            p.setLeading(15);
            document.add(p);
        }

        // Salva varios resultados por linha
//        res = res.substring(0, res.length() - 1);
//        p = new Paragraph(res, f);
//        p.setAlignment(Element.ALIGN_LEFT);
//        p.setLeading(15);
//        document.add(p);
    }

    private void AddTextRelatorio(Document document, ArrayList<Primos> resultado, int num, String data) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(30);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);
        p = new Paragraph("Números primos entre [1, " + num + "]", f);
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

        String res = "";
        for (int i = 0; i < resultado.size(); i++) {
            for (int j = 0; j < resultado.get(i).getResultado().size(); j++) {
                res += resultado.get(i).getResultado().get(j) + ",";
            }
        }

        p = new Paragraph(res, f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setLeading(15);
        document.add(p);
    }

    private void AddTextRelatorios(Document document, int num, String data) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(30);
        document.add(p);
        Font f = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);
        p = new Paragraph("Números primos entre [1, " + num + "]", f);
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

    private void AddTable(Document document, String intervalo, ArrayList<Long> temposdeexecucao, int inicio) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{2f, 2f, 2f});

        Font fonte = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
        PdfPCell c1 = new PdfPCell(new Phrase("Intervalo", fonte));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantidade de Threads", fonte));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Tempo de execução em milisegundos", fonte));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        fonte = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD);
        String time = "";
        int j = inicio;
        for (int i = 0; i < 10; i++, j++) {
            time += temposdeexecucao.get(j);
            c1 = new PdfPCell(new Phrase(intervalo, fonte));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf((i + 1)), fonte));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(time, fonte));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            time = "";
        }

        table.setWidthPercentage(100.0f);
        document.add(table);
    }

}
