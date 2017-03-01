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
    HashMap<Long,ArrayList<Long>> list_followers = new HashMap<>(); //chaque followed aura sa liste de followers;
    static  ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
    ArrayList<Extraction_thread> runnableThread= new ArrayList<>();
    ArrayList<Thread> threadList= new ArrayList<>();
    int depth;
    
    public static void applicationInitialisation()
    {
        TwitterApplicationCredentials app1 = new TwitterApplicationCredentials ("cYxtEVybhlxh2Oq9bo9hXpebA","va7Le5CL73sZzCmUGLN8ulaQkhBLvS3KEU9cy1kUeHqmzBhWwJ","799227064624803841-bOBepZnuSnl6B8d82S4sTGTyWeWTk9E","p8taDFsbEkXIdHujJ01q3wDSADTgcv86WkXA19tw20P0U");
        list_app.add(app1);
        TwitterApplicationCredentials app2 = new TwitterApplicationCredentials("uniKTsNEKd6tapvbgS08C5S3y","kT31lNlC2qdsJY4kqFFMP9ZyUJaYtGTsRoYuW324M1USWI0g17","799227064624803841-dqBHhCdNfX9UZWZlgGsAKuGMqV9vaHB","sySZ11TsZc6NqKoTXeEyrULNufgEK06MLEyrK4gacF4rw");
        list_app.add(app2);
        TwitterApplicationCredentials app3 = new TwitterApplicationCredentials("VpNCvZF2zp7ajl7RXcTXyGwP7","pwIN1SpuqHmRHltW6Hq5gblHxZD56ZYF2yCt6mj3qD4UUt6uop","799227064624803841-oTAnwLe029fhFt7AUMnjwMvkdNL1RTR","Dil1HBHEythkQXkHKmwtLiDrWtXoKL9QjrTsFXdUlKOnc");
        list_app.add(app3);
        TwitterApplicationCredentials app4 = new TwitterApplicationCredentials("m767Yp8kFbRUTlzydeqC1Hpiq","2acjpeUpQVF21ec3bRV46NHRUiwdYzLs9GT82Jqg3zuMo7Wfqk","799227064624803841-Z1dHfMbiKG0Ad0D0fqI0oFI7a9xjAdx","yEgzD8NaGbX5A4kLWSXPstvaX0LB6P2yXHh2mlAGVBCAj");
        list_app.add(app4);
        TwitterApplicationCredentials app5 = new TwitterApplicationCredentials("riwCB5ID1WnCya0IT18QdImas","YLllFUVLUAUpjdRQTCQTrSsMfnW7BXzQGheOpHM8eOPtiNNHx5","799227064624803841-BcF6tyzqIlCqyh9PShXSMzP2d3xxlKf","IQAT2W22ZQto7gFJ7ZcTCeOvbVqQcUxUCLTi5jLYFU4Hg");
        list_app.add(app5);
    }
    

    public TwitterExtraction (ArrayList<String> keywords_list,String depth)
    {    
            this.keywords_list = keywords_list;
            this.depth= Integer.parseInt(depth);
            applicationInitialisation();
            try{
                distributeTasks();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
    }
    
    public void distributeTasks() throws Exception
    {
        //je vais creer des threads, a chaque thread je vais donner un keyword, et ce thread aura comme role de 
        if(keywords_list.size()!=0)
        {
            for(int i=0; i<keywords_list.size(); ++i)
            {
                //run a function osn different thread, in which we will define the list of applications that it can use, and the keyword to work on:
                int part =  list_app.size()/keywords_list.size();
                Extraction_thread thread1 = new Extraction_thread(i*part,part,list_app,depth,keywords_list.get(i));   
                Thread a =new Thread(thread1);
                runnableThread.add(thread1);
                threadList.add(a);
                a.start();
            }
            //waiting for all threads
            for(int i=0; i<threadList.size();++i)
            {
                threadList.get(i).join();
            }
            //all threads had finished their work;
            for(int j=0;j<runnableThread.size();++j)
            {
 //               je prendrais tous les followers trouver par chaque thread et je les metttrai dans la liste principale de ce thread;
               for (Long key : runnableThread.get(j).followers.keySet()) 
               {
                   if(!list_followers.containsKey(key))
                       list_followers.put(key, runnableThread.get(j).followers.get(key));
               }
            }
        }
        else
        {
            throw new Exception("No keywords had been defined");
        }
        System.out.println("Extraction of data had finished:");

        for (Long key : list_followers.keySet()) 
        {
            System.out.println(""+key+" : "+list_followers.get(key).size());
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
