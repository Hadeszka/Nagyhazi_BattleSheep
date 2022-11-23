import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel;
    private JButton start;
    private JButton save;
    private JButton load;
    private JButton quit;

    public MainMenu(JPanel p){
        panel = p;
        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hmmmmm");

            }
        });
        start.setBounds(800, 200, 80, 30);
        panel.add(start);
        /*save = new JButton("Save");
        load = new JButton("Load");
        quit = new JButton("Quit");
        panel.add(save);
        panel.add(load);
        panel.add(quit);*/
    }

    public JPanel getPanel() {
        return panel;
    }

    public void Start(){}

    public void Save(){}

    public void Load(){}

    public void Quit(){}
}
