/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulbertemes
 */
public class Main 
{
    public Main()
    {
        new myThread();
        
        int i = 0;
        
        while(true)
            System.out.println(++i);
    }

    public static void main(String[] args)
    {
      Main main = new Main();   
    }
        
    public class myThread extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
            try {
                int n = System.in.read();
                
                if(n !=0) System.exit(0);
                
                n = 0;w
                yield();
                
            } catch (IOException ex) 
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }
}
