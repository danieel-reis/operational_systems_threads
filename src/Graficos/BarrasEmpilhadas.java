/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

/**
 *
 * @author daniel
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.StandardGradientPaintTransformer;

public class BarrasEmpilhadas extends ApplicationFrame {

    public BarrasEmpilhadas(String name_img, final String title, int dimension_x, int dimension_y, double FCFS[][], int tam_FCFS, double SJFN[][],  int tam_SJFN, 
            double SJFP[][], int tam_SJFP, double RR[][], int tam_RR) throws FileNotFoundException, IOException {
        super(title);
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double tamanho;
        int qtd = 0, j = 0;
        for(int i = 0; i < tam_FCFS; i++, qtd++) {
            tamanho = FCFS[i][3] - FCFS[i][2]; // Tempo em que acabou de executar - tempo em que começou a executar
            dataset.addValue(tamanho, "P"+i+"_1", "FCFS");
        }
        for(int i = 0; i < tam_SJFN; i++) {
            if(i%qtd==0) {
                j++;
            }
            tamanho = SJFN[i][3] - SJFN[i][2]; // Tempo em que acabou de executar - tempo em que começou a executar
            dataset.addValue(tamanho, "P"+i%qtd+"_"+j, "SJFN");
        }
        j = 0;
        for(int i = 0; i < tam_SJFP; i++) {
            if(i%qtd==0) {
                j++;
            }
            tamanho = SJFP[i][3] -SJFP[i][2]; // Tempo em que acabou de executar - tempo em que começou a executar
            dataset.addValue(tamanho, "P"+i%qtd+"_"+j, "SJFP");
        }
        j = 0;
        for(int i = 0; i < tam_RR; i++) {
            if(i%qtd==0) {
                j++;
            }
            tamanho = RR[i][3] - RR[i][2]; // Tempo em que acabou de executar - tempo em que começou a executar
            dataset.addValue(tamanho, "P"+i%qtd+"_"+j, "RR");
        }

        final JFreeChart chart = createChart(dataset, title, tam_FCFS, tam_SJFN, tam_SJFP, tam_RR);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(dimension_x, dimension_y));
        setContentPane(chartPanel);
        
        OutputStream arquivo = new FileOutputStream(name_img);
        ChartUtilities.writeChartAsPNG(arquivo, chart, dimension_x, dimension_y);
    }
    
    private JFreeChart createChart(final CategoryDataset dataset, String title, int tam_FCFS, int tam_SJFN, int tam_SJFP, int tam_RR) {

        final JFreeChart chart = ChartFactory.createStackedBarChart(title, "Entrada/Saída de Processos", "Tempo de execução", dataset, PlotOrientation.VERTICAL, true, true, false);

        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        KeyToGroupMap map = new KeyToGroupMap("G1");
        int qtd = 0;
        for(int i = 0; i < tam_FCFS; i++, qtd++) {
            map.mapKeyToGroup("P"+i+"_1", "FCFS");
        }
        int j = 0;
        for(int i = 0; i < tam_SJFN; i++) {
            if(i%qtd==0) {
                j++;
            }
            map.mapKeyToGroup("P"+i%qtd+"_"+j, "SJFN");
        }
        j = 0;
        for(int i = 0; i < tam_SJFP; i++) {
            if(i%qtd==0) {
                j++;
            }
            map.mapKeyToGroup("P"+i%qtd+"_"+j, "SJFP");
        }
        j = 0;
        for(int i = 0; i < tam_RR; i++) {
            if(i%qtd==0) {
                j++;
            }
            map.mapKeyToGroup("P"+i%qtd+"_"+j, "RR");
        }

        renderer.setSeriesToGroupMap(map);
        renderer.setItemMargin(0.0);
        renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        plot.setRenderer(renderer);
        //plot.setFixedLegendItems(new LegendItemCollection());
        return chart;

    }
}
