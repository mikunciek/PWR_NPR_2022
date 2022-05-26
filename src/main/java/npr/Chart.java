package npr;

import java.io.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
Klasa Chart służy do generowania wykresu na podstawie tabeli zawierajacej dane od użytkownika.
Umożliwia też zapis wykresu jako jpg
 */
public class Chart extends ApplicationFrame {


    public Chart(String applicationTitle, String chartTitle, Object[][] table) {
        super(applicationTitle);

        //tworzenie wykresu xy

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Dzień",
                "Temperatura",
                createDataset(table),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(560, 367)); //rozmiar okna

        final XYPlot plot = xylineChart.getXYPlot();

        //ustalenie wartości osi
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(1, 31);
        domain.setTickUnit(new NumberTickUnit(1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(36.00, 40.00);
        range.setTickUnit(new NumberTickUnit(0.5));

        //kolor linii na wykresie
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public Chart(String title) {
        super(title);
    }

    //pobieranie danych z tablicy
    private XYDataset createDataset(Object[][] table) {   //obiekty [a b c] czyli index a [0] ale [[a d] b c] czyli index d = [0][1]

        final XYSeries npr = new XYSeries("Temperatura");
        for (int i = 1; i < table[2].length; i++) {

            npr.add(i, Double.parseDouble(table[2][i].toString()));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(npr);
        return dataset;
    }

    //zapis jako jpg
    public static void jpg(Object[][] table) throws Exception {

        final XYSeries npr = new XYSeries("Temperatura");
        for (int i = 1; i < table[2].length; i++) {
            npr.add(1, Double.parseDouble(table[2][i].toString()));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(npr);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "NPR",
                "Dzień",
                "Temperatura",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */

        File chartFile = new File("NPR.jpeg");
        ChartUtils.saveChartAsPNG(chartFile, xylineChart, width, height);
    }
}