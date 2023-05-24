package meshtrafficsimulator.view;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JButton buttonStop;
    private JButton buttonStart;
    private JPanel panelMail;
    private JTextField textFieldMaxVehicles;
    private JTable table1;

    public Home() {
        setContentPane(panelMail);
        setTitle("Simulador de Tráfego em Malha Viária");
        setSize(800, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start");
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop");
            }
        });
    }
}
