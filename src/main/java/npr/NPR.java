package npr;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class NPR {
    JPanel myCycleCard; //
    private Object[][] baseTable;
    private Object[][] phaseTable;

    private JComboBox typeCervixValue;
    private JComboBox statusCervixValue;
    private JComboBox symptomsValue;
    private JComboBox colorPeriod;
    private JComboBox intensityPeriodValue;
    private JComboBox otherValue;
    private JComboBox levelCervixValue;
    private JComboBox MucusValue;
    private JCheckBox periodCheckBox;
    private JButton saveDaneButton;
    private JButton dataButton;
    private JTable tablePeriod;
    private JTextField hourData;
    private JTextField tempValue;
    private JPanel saveData;
    private JButton deleteDaneButton;
    private JTextField hour;
    private JButton gerChart;
    private JTextField shortestValue;
    private JLabel dayInfo;
    private JLabel phaseFirstLenght;
    private JPanel hourText;
    private JLabel mucus;
    private JLabel other;
    private JLabel Cervix;
    private JLabel cycleDay;
    private JLabel lenghtCycleFirst;
    private JLabel shortestCycle;


    NPR() {


        firstStatusPeriod();


        gerChart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);
                Chart chart = new Chart("Przebieg temperatury w cyklu", "Temperatura", baseTable);
                chart.pack();
                chart.setVisible(true);
            }
        });

        this.baseTable = new Object[][]{{"Data"}, {"Godzina_pomiaru"}, {"Temperatura"},
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
        saveDaneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);
                String[] dataList;
                dataList = new String[]{
                        hourData.getText(),
                        hour.getText(),
                        tempValue.getText(),
                        periodCheckBox.isSelected() ? "+" : "",
                        (String) colorPeriod.getSelectedItem(),
                        (String) intensityPeriodValue.getSelectedItem(),
                        (String) MucusValue.getSelectedItem(),
                        (String) typeCervixValue.getSelectedItem(),
                        (String) levelCervixValue.getSelectedItem(),
                        (String) statusCervixValue.getSelectedItem(),
                        (String) otherValue.getSelectedItem()
                };
                updateTableData(dataList); //aktualizacja tabeli
                phaseCycle();
                dayCycle();
            }
        });

        deleteDaneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);

                Object[][] del = new Object[baseTable.length][baseTable[0].length - 1];

                if (baseTable[0].length == 1) {
                    return;
                }
                for (int i = 0; i < baseTable.length; i++) {
                    for (int j = 0; j < baseTable[i].length-1; j++) {
                        del[i][j] = baseTable[i][j];
                    }
                }
                baseTable = del;
                updateTable();
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
        dayCycle();

    }

    public void importData(Object[][] tableData) {
        this.baseTable = tableData;
        this.updateTable();
    }

    public Object[][] getBaseTable() {
        return baseTable;
    }

    private void firstStatusPeriod() {

/*
        if (periodCheckBox.isSelected()) {

            intensityPeriodValue.setEnabled(true);
            colorPeriod.setEnabled(true);

        } else {
            intensityPeriodValue.setEnabled(false);
            colorPeriod.setEnabled(false);
        }
*/

    }

    private void phaseCycle() {

        int valueCycle = parseInt(shortestValue.getText()) - 20;
        phaseFirstLenght.setText(String.valueOf(valueCycle));
    }


    private void dayCycle() {

        int day = baseTable[0].length - 1;
        dayInfo.setText(String.valueOf(day));

    }


}
