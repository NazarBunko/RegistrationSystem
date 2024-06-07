package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

public class Login extends JFrame {

    public JTextField name_field;
    public  JPasswordField password_field;

    public Login(){
        super("Авторизація");
        super.setBounds(810, 380, 300, 225);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = super.getContentPane();

        JLabel name = new JLabel("Логін:");
        name.setLocation(70,12);
        name.setSize(40,10);
        name_field = new JTextField("", 1);
        name_field.setLocation(70,26);
        name_field.setSize(150,25);

        JLabel pass = new JLabel("Пароль:");
        pass.setLocation(70,55);
        pass.setSize(70,14);
        password_field = new JPasswordField("", 1);
        password_field.setLocation(70,72);
        password_field.setSize(150,25);

        container.add(name);
        container.add(name_field);
        container.add(pass);
        container.add(password_field);

        JButton button = new JButton("Увійти");
        button.setLocation(70, 105);
        button.setSize(150, 30);
        container.add(button);
        button.addActionListener(new Login.ButtonEventManager());

        JButton reg = new JButton("Зареєструватись");
        reg.setLocation(70, 158);
        reg.setSize(150, 30);
        reg.setBorderPainted(false);
        reg.setContentAreaFilled(false);
        container.add(reg);
        reg.addActionListener(new Login.RegEventManager());

        JLabel empty = new JLabel("  ");
        container.add(empty);
    }

    class ButtonEventManager implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = name_field.getText();
            String passText = new String(password_field.getPassword());

            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Ви не вказали ім'я.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            if(passText.isEmpty()){
                JOptionPane.showMessageDialog(null, "Ви не вказали пароль.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
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

            boolean flag = false;
            for(int i = 0; i < n; i++){
                if(name_field.getText().equals(array[i])){
                    for(int j = 0; j < n; j++){
                        String pass1 = new String(password_field.getPassword());
                        if(pass1.equals(array[j])){
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if(!flag){
                JOptionPane.showMessageDialog(null, "Неправильні дані.", "Помилка реєстрації", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            Form form = new Form();
            form.setResizable(false);
            form.setVisible(true);
            form.setLocationRelativeTo(null);
        }
    }

    class RegEventManager implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            Registration reg = null;
            try {
                reg = new Registration();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            reg.setResizable(false);
            reg.setVisible(true);
            reg.setLocationRelativeTo(null);
        }
    }

}
