public class Bar implements Runnable
{
public void run()
{
while (true)
{
System.out.print(".");
System.out.flush();
try 
{
Thread.sleep(1000);
}
catch (InterruptedException ex) 
{
Thread.currentThread().interrupt();
break;
}
}
System.out.println("Shutting down thread");
}

public Thread startTh()
{
Bar r = new Bar();
Thread t = new Thread(r);
t.start();
return t; 
}
public void stopTh(Thread t)
{
System.out.println("Interrupting thread\n"); 
t.interrupt(); 
} 
}//end class
