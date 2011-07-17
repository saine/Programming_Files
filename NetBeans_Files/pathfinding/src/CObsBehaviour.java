import java.awt.AWTEvent;
import java.awt.event.*; 
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

//	SimpleBehaviorApp renders a single, rotated cube. 

public class CObsBehaviour extends Behavior
{
    private TransformGroup m_objTG; 
    private Transform3D    m_objT  = new Transform3D(); 
    private Vector3f       m_Pos   = new Vector3f(0.0f,0.0f,0.0f);
    private int            m_Length;
    private int            m_Width;
    private int            m_Height;
    private int            m_Diam;
    
    CObsBehaviour(TransformGroup objTG,int[] Dims,int Diam)
    {
        m_objTG  = objTG;
        m_Length = Dims[0];
        m_Width  = Dims[1];
        m_Height = Dims[2];
        m_Diam   = Diam;
    }
    
    public void initialize()
    { 
        // set initial wakeup condition
        this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
    }

    public void processStimulus(Enumeration criteria)
    {
        WakeupOnAWTEvent ev;
        WakeupCriterion genericEvt;
        AWTEvent[] events;
  
        while (criteria.hasMoreElements())
        {
            genericEvt = (WakeupCriterion) criteria.nextElement();
   
            if (genericEvt instanceof WakeupOnAWTEvent)
            {
                ev = (WakeupOnAWTEvent) genericEvt;
                events = ev.getAWTEvent();
                processAWTEvent(events);
            }
        }
        this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
    } 
    
    //Process a keyboard event
    private void processAWTEvent(AWTEvent[] events)
    {
        for( int n = 0; n < events.length; n++)
        {
            if( events[n] instanceof KeyEvent)
            {
                KeyEvent eventKey = (KeyEvent) events[n];

                if( eventKey.getID() == KeyEvent.KEY_PRESSED )
                {
                    int keyCode = eventKey.getKeyCode();

                    Vector3f translate = new Vector3f();

                    Transform3D t3d = new Transform3D();
                    m_objTG.getTransform(t3d);
                    t3d.get(translate);

                    switch (keyCode)
                    {
                        case KeyEvent.VK_LEFT:
                            translate.x -= 2;
                            break;

                        case KeyEvent.VK_RIGHT:
                            translate.x += 2;
                            break;
                            
                        case KeyEvent.VK_UP:
                            translate.y += 2;
                            break;
                            
                        case KeyEvent.VK_DOWN:
                            translate.y -= 2;
                            break;
                            
                        case KeyEvent.VK_F:
                            translate.z += 2;
                            break;
                            
                        case KeyEvent.VK_B:
                            translate.z -= 2;
                            break;
                            
                        case KeyEvent.VK_ESCAPE:
                            System.exit(0);
                            break;
                    }

                    //Factors for Dimensions
                    float LStep = -1+m_Length/m_Diam;
                    float WStep = -1+m_Width/m_Diam;
                    float HStep = -1+2*m_Height/m_Diam;
                    
                    //Limit Movement to Boundaries of Environment
                    if (translate.x>LStep)  translate.x =  LStep;
                    if (translate.x<-LStep) translate.x = -LStep;
                    if (translate.z>WStep)  translate.z =  WStep;
                    if (translate.z<-WStep) translate.z = -WStep;
                    if (translate.y>HStep)  translate.y =  HStep;
                    if (translate.y<1)      translate.y = 1;
                    
                    //Set Transformation
                    t3d.setTranslation(translate);
                    m_objTG.setTransform(t3d);
                }
            }
        }
    }
}