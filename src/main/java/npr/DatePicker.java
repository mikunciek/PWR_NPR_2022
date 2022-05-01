package npr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatePicker { //przycisk
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] dataButton = new JButton[49]; //49

    public DatePicker(JPanel parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = {"Nd", "Pn", "Wt", "Śr", "Cz", "Pt", "Sb"};
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < dataButton.length; x++) {
            final int selection = x;
            dataButton[x] = new JButton();
            dataButton[x].setFocusPainted(false);
            dataButton[x].setBackground(Color.white);

            if (x > 6)
                dataButton[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = dataButton[selection].getActionCommand();
                        d.dispose();
                    }
                });
            if (x < 7) {
                dataButton[x].setText(header[x]);
                dataButton[x].setForeground(Color.blue);
            }
            p1.add(dataButton[x]);
        }

        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<>");
        PopupMenu next = new PopupMenu();
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);

        displayDate();
        d.setVisible(true);
    }

    public void displayDate() {
        for (int y = 7; y < dataButton.length; y++) //zmiana x na y
            dataButton[y].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int z = 6 + dayOfWeek, day = 1; day <= daysInMonth; z++, day++) //zmiana x na z
            dataButton[z].setText("" + day);
        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}
