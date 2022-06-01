package npr;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class NPR {
    //elementy stworzone w NPR.form
    private Object[][] baseTable;
    private Object[][] phaseTable;
    JPanel myCycleCard;
    private JPanel saveData;
    private JPanel hourText;
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
    private JLabel cycleDay;
    private JLabel lenghtCycleFirst;
    private JLabel shortestCycle;
    private JLabel dayInfo;
    private JLabel phaseFirstLength;
    private JTextField hourData;
    private JTextField tempValue;
    private JTextField hour;
    private JTextField shortestValue;
    private JCheckBox periodCheckBox;
    private JButton saveDaneButton;
    private JButton dataButton;
    private JButton deleteDaneButton;
    private JButton gerChart;
    private JTable tablePeriod;
    private JLabel whatPhase;
    private JLabel whatPhaseValue;

    /*
    Klasa NPR obsługuje NPR.form. Zawiera wydarzenia oraz następujące funkcje:
    - tworzenie i aktualizowanie tabeli
    - obliczenie fazy i dnia cyklu
    - ukrywanie niektórych przycisków
     */

    NPR() {

        //stworzenie podstawowej tabeli zawierającej 1 kolumnę
        this.baseTable = new Object[][]{{"Data"}, {"Godzina_pomiaru"}, {"Temperatura"},
                {"Okres"}, {"Kolor_krwawienia"}, {"Intensywność_krwawienia"}, {"Śluz"},
                {"Szyjka_macicy_status"}, {"Szyjka_macicy_położenie"}, {"Szyjka_macicy_typ"},
                {"Inne"},
        };
        this.updateTable();

        //Wydarzenia

        //dostępność opcji w zależności od wyboru pola "okres"
        //jeszcze musi być ustalenie stanu początkowego
        intensityPeriodValue.setEnabled(false);
        colorPeriod.setEnabled(false);

        periodCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (periodCheckBox.isSelected()) {

                    intensityPeriodValue.setEnabled(true);
                    colorPeriod.setEnabled(true);

                } else {
                    intensityPeriodValue.setEnabled(false);
                    colorPeriod.setEnabled(false);
                }
            }
        });

        //generowanie wykresu,po naciśnięciu przycisku
        gerChart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//wydarzenie gdy klinięcie myszki
                super.mouseClicked(e);
                Chart chart = new Chart("Przebieg temperatury w cyklu", "Temperatura", baseTable);
                chart.pack();
                chart.setVisible(true);
                chart.setLocation(600, 290); //lokalizacja wykresu
            }
        });


        //wyświetlanie kalendarza
        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                hourData.setText(new DatePicker(saveData).setPickedDate());
            }
        });

        //zapisywanie danych wprowadzonych przez użytkownika
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
                updateTableData(dataList);//aktualizacja tabeli
                phaseCycle(); //aktualizacja fazy cyklu
                dayCycle(); //aktualizacja dnia cyklu
                whatCycle();
            }
        });

        //usuwanie danych - ostatniej kolumny,np w przypadku błędu
        deleteDaneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Object[][] del = new Object[baseTable.length][baseTable[0].length - 1];

                if (baseTable[0].length == 1) {
                    return;
                }

                for (int i = 0; i < baseTable.length; i++) {
                    for (int j = 0; j < baseTable[i].length - 1; j++) {
                        del[i][j] = baseTable[i][j];
                    }
                }
                baseTable = del;
                updateTable();
            }
        });
    }


    //stworzenie podstawowej tabeli - wypełnianie 1 nagłówka
    private DefaultTableModel newDataTableModel() {
        String[] columns = {"Dzień",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"};
        return new DefaultTableModel(new Object[][]{}, columns);
    }

    //aktualizacja tabeli danymi
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

    //wczytanie danych do tabeli
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

    //pobranie danych
    public void importData(Object[][] tableData) {
        this.baseTable = tableData;
        this.updateTable();
    }

    //podstawowa tabela
    public Object[][] getBaseTable() {
        return baseTable;
    }

    //Obliczenia

    //długość fazy względnej, w przypadku kobiety niedoświadczonej
    private void phaseCycle() {
        int valueCycle = parseInt(shortestValue.getText()) - 20;
        phaseFirstLength.setText(String.valueOf(valueCycle));
    }

    //dzień cyklu, na podstawie ostatnio wypełnionej kolumny tabeli
    private void dayCycle() {
        int day = baseTable[0].length - 1;
        dayInfo.setText(String.valueOf(day));
    }

    //określenie fazy - jeszcze faza względnej niepłodności

    private void whatCycle(){

        int day = baseTable[0].length - 1;
        int valueCycle = parseInt(shortestValue.getText()) - 20;

        if(periodCheckBox.isSelected()){
            whatPhaseValue.setText("Okres");
        }else {
            if (8< day && day < 12) {
                whatPhaseValue.setText("Faza płodności");
            }else if (12 < day && day < 16){
                whatPhaseValue.setText("Owulacja?");
            }
            else if (17 < day){
                whatPhaseValue.setText("Faza niepłodności");
            }

        }

    }

}
