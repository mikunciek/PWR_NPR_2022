package npr;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {
    private NPR npr;

    GUI() {
        JMenu menu;
        JMenu save;
        final JMenuItem newFile, open, jpg, csv;
        JMenuItem help;

        this.npr = new NPR();
        JFrame frame = new JFrame("NPR");
        JMenuBar menuBar = new JMenuBar();

        frame.setJMenuBar(menuBar);
        frame.setSize(600, 600);
        frame.setLayout(null);

        frame.setContentPane(this.npr.myCycleCard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        menu = new JMenu("Menu");
        help = new JMenuItem("Pomoc");
        save = new JMenu("Zapisz jako");

        newFile = new JMenuItem("Nowy");
        open = new JMenuItem("Otwórz");
        jpg = new JMenuItem("jpg");
        csv = new JMenuItem("csv");

        menu.add(newFile);
        menu.add(open);
        menu.addSeparator();

        menu.add(save);
        save.add(jpg);
        save.add(csv);

        help.setMnemonic(KeyEvent.VK_H); //skrót

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //otworz plik csv
                //Najpierw odczytamy rekordy wiersz po wierszu, używając funkcji readLine()  w BufferedReader .
                //
                //Następnie podzielimy linię na tokeny na podstawie separatora przecinków:

                List<Object[]> records = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader("npr.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(";");
                        records.add(values);
                    }

                    npr.importData(records.toArray(new Object[0][0]));
                    JOptionPane.showMessageDialog(null, "Wczytano");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Błąd pliku");
                }


            }
        });

        csv.addActionListener(new ActionListener() { //przekazanie Object[][] table
            @Override
            public void actionPerformed(ActionEvent e) {
                //zapisz csv

                try {
                    FileWriter fw = new FileWriter("npr.csv");
                    String csv = "";

                    Object[][] table = npr.getBaseTable();
                    for (int i = 0; i < table.length; i++) {
                        for(int j = 0; j < table[i].length; j++) {
                            String del = j < table[i].length - 1 ? ";" : "";

                            csv += table[i][j].toString() + del;
                        }
                        csv += i < table.length - 1 ? "\n" : "";
                    }


                    fw.close();
                    JOptionPane.showMessageDialog(null, "Zapisano");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Nie zapisano");
                }

            }
        });

        jpg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                File chartFile = new File("XYLineChart.jpeg");

            }
        });


        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Pomoc");
                JPopupMenu pm = new JPopupMenu("Pomoc");
                String[] s = new String[]{
                        "ŚLUZ", "Objawy niepłodności",
                        "∅ – nic nie czuję, nic nie widzę",
                        "su – odczucie suchości",
                        "c – stała wydzielina",
                        " ",
                        "Objawy płodności",
                        "w – objaw wnikliwej obserwacji – wrażenie bulgotania,spływania kropelek wewnątrz pochwy,",
                        "    ale bez widocznego śluzu lub wilgotności na zewnątrz, czasem odczucie ciepła",
                        "wl – wilgotność odczuwana na zewnętrznych narządach płciowych",
                        " ",
                        "Śluz gorszej jakości",
                        " ",
                        "b – śluz biały, białawy",
                        "ż – śluz żółty",
                        "żt – śluz żółtawy",
                        "gr – śluz grudkowaty",
                        "m – śluz mętny",
                        "kl – śluz kleisty",
                        "S – bliżej nieokreślony śluz gorszej jakości",
                        " ",
                        "Śluz dobrej jakości",
                        " ",
                        "Bj – śluz jak surowe białko jaja",
                        "    (rozciągliwy na kilka, kilkanaście cm, przejrzysty z ewentualnymi pojedynczymi białymi smugami)",
                        "szk – śluz szklisty (rozciągliwy i przejrzysty jak szkło)",
                        "pł – śluz płynny (przejrzysty i rozciągliwy, ale ze względu na zwiększoną" +
                                "zawartość wody nitki są cieńsze i krótsze),\n",
                        "mś – odczucie mokro-ślisko, wrażenie śliskości i naoliwienia w przedsionku\n" +
                                "pochwy. Śluz jest tak wodnisty, że nie tworzy nitek",
                        " ",
                        "Szyjka",
                        "t – szyjka twarda",
                        "m – szyjka miękka",
                        ". – szyjka zamknięta",
                        "o – szyjka otwarta",
                        "V – szyjka badana dwoma palcami",
                        " ",
                        "Inne",
                        "||||||||||| – krwawienie miesiączkowe",
                        "BO – ból okołoowulacyjny",
                        "P – objaw piersiowy (napięcie, ciężkość piersi w f. poowulacyjnej)",
                        "X – seks",
                        "(.) – temperatura zakłócona",
                        "póź – późno poszłam spać. Wyraźnie późniejsze niż zwykle",
                        "alk – alkohol (większa ilość poprzedniego dnia wieczorem lub w nocy)"
                };

                for (String ss : s) {
                    pm.add(new JLabel(ss));
                }

                //frame.setSize(300, 300);
                frame.setVisible(true);
                pm.show(frame, 00, 00);

            }
        });


        menuBar.add(menu);
        menuBar.add(help);


    }

    public static void main(String[] args) {

        new GUI();

    }

}
