package npr;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NPR {
    JPanel myCycleCard; //
    private Object[][] baseTable;

    private JComboBox typeCervixValue;
    private JComboBox statusCervixValue;
    private JComboBox symptomsValue;
    private JComboBox colorPeriod;
    private JComboBox intensityPeriodValue;
    private JComboBox otherValue;
    private JComboBox levelCervixValue;
    private JComboBox MucusValue;
    private JLabel mucus;
    private JLabel Cervix;
    private JLabel other;
    private JCheckBox okresCheckBox;
    private JButton zapiszDaneButton;
    private JButton dataButton;
    private JTable tablePeriod;
    private JTextField hourData;
    private JTextField tempValue;
    private JPanel saveData;
    private JLabel cyclePhase;
    private JLabel ovulation;
    private JLabel nextPeriod;
    private JPanel charView;
    private JButton usuńDaneButton;
    private JButton czyśćWykresButton;
    private JTextField hour;
    private JPanel hourText;
    private JButton gerChart;


    NPR() {
        gerChart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);
                Chart chart = new Chart("Przebieg temperatury w cyklu", "Temperatura", baseTable);
                chart.pack();
                chart.setVisible(true);
            }
        });

        this.baseTable = new Object[][]{{"Data"},{"Godzina_pomiaru"}, {"Temperatura"},
                {"Okres"}, {"Kolor_krwawienia"}, {"Intensywność_krwawienia"}, {"Śluz"},
                {"Szyjka_macicy_status"}, {"Szyjka_macicy_położenie"}, {"Szyjka_macicy_typ"},
                {"Inne"},
        };
        this.updateTable();


        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                hourData.setText(new DatePicker(saveData).setPickedDate());
            }
        });
        zapiszDaneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);
                String[] dataList;
                dataList = new String[]{
                        hourData.getText(),
                        hour.getText(),
                        tempValue.getText(),
                        okresCheckBox.isSelected() ? "+" : "",
                        (String) colorPeriod.getSelectedItem(),
                        (String) intensityPeriodValue.getSelectedItem(),
                        (String) MucusValue.getSelectedItem(),
                        (String) typeCervixValue.getSelectedItem(),
                        (String) levelCervixValue.getSelectedItem(),
                        (String) statusCervixValue.getSelectedItem(),
                        (String) otherValue.getSelectedItem()
                };
                updateTableData(dataList); //aktualizacja tabeli
            }
        });
    }

    private DefaultTableModel newDataTableModel() {//wypełnianie podstawowe tabeli
        String[] columns = {"Dzień",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"};
        return new DefaultTableModel(new Object[][]{}, columns);
    }

    //aktualizacja tabeli
    private void updateTableData(String[] dataList) {

        Object[][] o = new Object[this.baseTable.length][];

        for (int i = 0; i < this.baseTable.length; i++) {
            List l = new ArrayList<>(this.baseTable[i].length + 1);
            Collections.addAll(l, this.baseTable[i]);
            Collections.addAll(l, dataList[i]);
            o[i] = l.toArray();
        }
        this.baseTable = o;
        this.updateTable();
    }

    private void updateTable() {
        int count = 0;
        DefaultTableModel m = this.newDataTableModel();
        tablePeriod.setModel(m);
        for (Object[] object : this.baseTable) {
            m.insertRow(count, object);
            count++;
        }
    }
}