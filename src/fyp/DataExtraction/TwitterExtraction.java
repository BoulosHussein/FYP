/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp.DataExtraction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modified_components.RoundButton;

/**
 *
 * @author generals
 */
public class TwitterExtraction{    
   // JFrame home_frame ;
    ArrayList<String> keywords_list= new ArrayList<>();
    HashMap<Long,ArrayList<Long>> l = new HashMap<>(); //chaque followed aura sa liste de followers;
    
    static  ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
    
    
    public static void applicationInitialisation(){
    TwitterApplicationCredentials app = new TwitterApplicationCredentials ("","","","");
    list_app.add(app);
    }
    
    public TwitterExtraction (ArrayList<String> keywords_list){    
            this.keywords_list = keywords_list;
            try{
                distributeTasks();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
    }
    
    public void distributeTasks() throws Exception{
        //je vais creer des threads, a chaque thread je vais donner un keyword, et ce thread aura comme role de 
        if(keywords_list.size()!=0)
        {
            for(int i=0; i<keywords_list.size(); ++i)
            {
                //run a function on different thread, in which we will define the list of applications that it can use, and the keyword to work on; 
                
            
            }
        }
        else
        {
            throw new Exception("No keywords had been defined");
        }
    }

    public void frameInitialise(){
//        home_frame = new JFrame("Twitter Extraction");
//        home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       
//        JPanel keywords_container= new JPanel();
//        JPanel inputsContainer = new JPanel();
//        JButton add_keywords = new JButton("+");
//        //JButton add_keywords = new RoundButton("+");
//        
//        add_keywords.addActionListener(new ActionListener(){
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//             JTextField text_field = new JTextField();
//             //text_field
//            }
//        });
//        
//        inputsContainer.setLayout(new BoxLayout(inputsContainer,BoxLayout.Y_AXIS));
//        inputsContainer.setBounds(25, 2, 35, 65);
//
//        add_keywords.setAlignmentX(Component.CENTER_ALIGNMENT);
//        add_keywords.setAlignmentY(Component.CENTER_ALIGNMENT);
//        
//        keywords_container.setAlignmentX(Component.CENTER_ALIGNMENT);
//        keywords_container.setAlignmentY(Component.CENTER_ALIGNMENT);
//       
//        inputsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
//        inputsContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
//
//        keywords_container.add(inputsContainer);
//        keywords_container.add(add_keywords);
//        
//        
//        home_frame.add(keywords_container,BorderLayout.CENTER);
//        
//        home_frame.setSize(500,500);
//        
//        home_frame.pack();
//        
//        home_frame.setVisible(true);
//        
    }
    
   
    
}
