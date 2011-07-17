//------------------------------------------------------------------------------
//TITLE:       COBSTACLE CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED:     16/07/2010
//VERSION:     V2.0 
//DESCRIPTION: This class implements the obstacles in the 3 dimensional 
//             environment. The properties of each obstacle are the start coords
//             , length, width and height. Furthermore, this class contains 
//             functionalities for drawing the object on screen and to check for
//             collisions with a 3 dimensional point.
//
//Functions: 
//
//      1.  public         CObstacle(int x,int y,int z, int l,int w,int h)
//      2.  public void       render(Graphics Graph,Color Col,String sLabel) 
//      3.  public boolean  contains(double[] dP) 
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import java.awt.event.*; 
import java.util.Enumeration;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import com.sun.j3d.utils.geometry.Box;
import java.awt.AWTEvent;
import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

//------------------------------------------------------------------------------
//COBSTACLE CLASS DEFINITION
//------------------------------------------------------------------------------

public class CObstacle extends Behavior 
{
    int m_x; //x Axis Position
    int m_y; //y Axis Position
    int m_z; //z Axis Position
    int m_l; //x Axis Dimension
    int m_w; //y Axis Dimension
    int m_h; //z Axis Dimension
    
    private TransformGroup m_objTG; 
    private Transform3D    m_objT  = new Transform3D(); 
    private Vector3f       m_Pos   = new Vector3f(0.0f,0.0f,0.0f);
    private int            m_Length;
    private int            m_Width;
    private int            m_Height;
    private int            m_Diam;

    //Constructor [XPos, YPos, ZPos, Length, Width, Height]
    public CObstacle(int x,int y,int z, int l,int w,int h) 
    {
        m_x = x;
        m_y = y;
        m_z = z;
        m_l = l;
        m_w = w;
        m_h = h;  
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
        
    //Drawing Function
    public void render(BranchGroup sceneRoot,Color3f Col,int nDiam,int[] Dims,
                       boolean Moveable,Canvas3D Can) 
    { 
        m_Length = Dims[0];
        m_Width  = Dims[1];
        m_Height = Dims[2];
        m_Diam   = nDiam;
        
        //Create Tranform and Appearance Objects
        TransformGroup        objTG   = null; 
        Transform3D           objT    = new Transform3D();
        Appearance            App     = new Appearance();
        ColoringAttributes    CA      = new ColoringAttributes();
        
        //Create Box and PickTranlateBehavior 
        CA.setColor(Col);
        App.setColoringAttributes(CA);
        Box obs = new Box(m_l/nDiam,m_w/nDiam,m_h/nDiam,App);
        objT.setTranslation(new Vector3f(2*m_x/nDiam-((Dims[0]-1)/nDiam),
                                        (2*m_z/nDiam)+1,
                                        -2*m_y/nDiam+((Dims[1]-1)/nDiam)));
        objTG = new TransformGroup(objT);
        
        //Make Object Moveable
        if(Moveable)
        {
            objTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            objTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
            objTG.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

            //Create Moving Behaviour
//            CObsBehaviour myRotationBehavior = new CObsBehaviour(objTG,Dims,nDiam); 
            this.setSchedulingBounds(new BoundingSphere());
            sceneRoot.addChild(this);
        } 
        objTG.addChild(obs);
        sceneRoot.addChild(objTG); 
        m_objTG  = objTG;
    }

    //Point Collision Detection
    public boolean contains(double[] dP) 
    {
        if(dP[0]>=m_x & dP[0]<=m_x+m_l &
           dP[1]>=m_y & dP[1]<=m_y+m_w &
           dP[2]>=m_z & dP[2]<=m_z+m_h)
            return true;
        else
            return false;
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
                            m_x -=m_Diam;
                            translate.x -= 2;
                            break;

                        case KeyEvent.VK_RIGHT:
                            m_x +=m_Diam;
                            translate.x += 2;
                            break;
                            
                        case KeyEvent.VK_UP:
                            m_z +=m_Diam;
                            translate.y += 2;
                            break;
                            
                        case KeyEvent.VK_DOWN:
                            m_z -=m_Diam;
                            translate.y -= 2;
                            break;
                            
                        case KeyEvent.VK_F:
                            m_y -=m_Diam;
                            translate.z += 2;
                            break;
                            
                        case KeyEvent.VK_B:
                            m_y +=m_Diam;
                            translate.z -= 2;
                            break;
                            
                        case KeyEvent.VK_ESCAPE:
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
                    
                    if(m_x<0)              m_x = 0;
                    if(m_x>m_Length-m_Diam)m_x = m_Length-m_Diam;
                    if(m_y<0)              m_y = 0;
                    if(m_y>m_Width-m_Diam) m_y = m_Width-m_Diam;
                    if(m_z<0)              m_z = 0;
                    if(m_z>m_Height-m_Diam)m_z = m_Height-m_Diam;
                    
                    //Set Transformation
                    t3d.setTranslation(translate);
                    m_objTG.setTransform(t3d);
                }
            }
        }
    }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------