package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Registration extends JFrame {

    public JTextField name_field;
    public  JPasswordField password_field;
    public JRadioButton male, female;
    public JCheckBox checkBox;

    public Registration() throws IOException {
        super("Реєстрація");
        //super.setBounds(785, 330, 350, 175);
        super.setBounds(0, 0, 350, 175);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container container = super.getContentPane();
        container.setLayout(new GridLayout(5, 2, 2, 3));

        JLabel name = new JLabel("  Введіть ім'я: ");
        name_field = new JTextField("", 1);

        JLabel password = new JLabel("  Введіть пароль: ");
        password_field = new JPasswordField("", 1);

        container.add(name);
        container.add(name_field);
        container.add(password);
        container.add(password_field);

        JLabel selectsex = new JLabel("  Виберіть стать:");
        JLabel empty = new JLabel("  ");

        male = new JRadioButton("Чоловік");
        female = new JRadioButton("Жінка");

        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);

        container.add(selectsex);
        container.add(empty);
        container.add(male);
        container.add(female);

        checkBox = new JCheckBox("Вам є 18 років.");

        container.add(checkBox);

        JButton button = new JButton("Зареєструватись");
        container.add(button);

        button.addActionListener(new ButtonEventManager());
    }

    class ButtonEventManager implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String name = name_field.getText();
            String pass = new String(password_field.getPassword());

            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Ви не вказали ім'я.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            if(pass.isEmpty()){
                JOptionPane.showMessageDialog(null, "Ви не вказали пароль.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(name.length() < 2){
                JOptionPane.showMessageDialog(null, "Ім'я має бути більше одного символа.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(pass.length() < 2){
                JOptionPane.showMessageDialog(null, "Пароль має бути більше одного символа.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(male.isSelected() == false && female.isSelected() == false){
                JOptionPane.showMessageDialog(null, "Ви не вказали вашу стать.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            char sex;
            if(male.isSelected()){
                sex = 'm';
            }
            else{
                sex = 'w';
            }

            char check = '+';
            if(!checkBox.isSelected()){
                check = '-';
            }

            String file = "userdata.txt";

            int n = 0;
            try(FileReader reader = new FileReader(file))
            {
                int c;
                while((c = reader.read()) != -1){
                    if((char)c == '\n'){
                        n++;
                    }
                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            n += 2;
            String[] array = new String[n];

            try(FileReader reader = new FileReader(file))
            {
                int c, i = 0;
                array[i] = "";
                while((c = reader.read()) != -1){
                    if((char)c == '\n'){
                        i++;
                        array[i] = "";
                        continue;
                    }
                    array[i] += (char)c;
                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }

            for(int i = 0; i < n; i++){
                if(name_field.getText().equals(array[i])){
                    JOptionPane.showMessageDialog(null, "Таке ім'я вже зареєстровано.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }

            try {
                FileWriter f = new FileWriter(file ,true);
                f.write(name_field.getText() + "\n" + pass + "\n" + sex + "\n" + check + "\n\n");
                f.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            setVisible(false);
            Login login = new Login();
            login.setResizable(false);
            login.setVisible(true);
            login.setLocationRelativeTo(null);

        }
    }

}
