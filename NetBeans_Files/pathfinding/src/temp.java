////------------------------------------------------------------------------------
////TITLE:       CGAPATHFINDING CLASS
////AUTHOR:      PAUL BERTEMES
////CREATED: 
////VERSION:     V1.0 
////DESCRIPTION: 
////
////------------------------------------------------------------------------------
////INCLUDES AND DEFINES
////------------------------------------------------------------------------------
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import com.sun.j3d.utils.applet.MainFrame;
//import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
//import com.sun.j3d.utils.geometry.Box;
//import com.sun.j3d.utils.geometry.ColorCube;
//import com.sun.j3d.utils.geometry.Sphere;
//import com.sun.j3d.utils.universe.SimpleUniverse;
//import com.sun.j3d.utils.universe.ViewingPlatform;
//import javax.media.j3d.*;
//import javax.vecmath.Color3f;
//import javax.vecmath.Point3d;
//import javax.vecmath.Vector3d;
//import javax.vecmath.Vector3f;
//import java.applet.Applet;
//import java.awt.*;
//
//
////------------------------------------------------------------------------------
////CGAPATHFINDING CLASS DEFINITION
////------------------------------------------------------------------------------
//
//public class CGaPathFinding extends Applet implements KeyListener,
//                                                      MouseInputListener
//{
//    //Static Member Variables
//    static int     m_Length;    //Length of Environment
//    static int     m_Width;     //Width of Environment
//    static int     m_Height;    //Height of Environment
//    static int     m_PopSize;   //Number of Individuals in a Population
//    static int     m_GridScale; //Grid Overlay Factor
//    static int     m_LifeTime;  //Time used for Propagating Chromosomes 
//    static int     m_LifeCycle; //Time that each Generation Survived
//    static int     m_RecTime;   //Quickest Time to Finish
//    static int     m_Diam;      //Size of Vehicle
//    static double  m_MaxSpeed;  //Maximum Velocity
//    static double  m_MaxForce;  //Maximum Acceleration
//    static float   m_MutRate;   //Mutation Probability
//    
//    //Define Material for Floor
//    static Material m_FloorMat = new Material(new Color3f(Color.gray),
//                                              new Color3f(Color.red),
//                                              new Color3f(0.2f,0.2f,0.2f),
//                                              new Color3f(.1f, .1f, .1f),4.0f);
//
//    //Define Material for Wall
//    private final static Material m_WallMat = new Material(
//                                              new Color3f(Color.green),
//                                              new Color3f(Color.red),
//                                              new Color3f(0.2f,0.2f,0.2f),
//                                              new Color3f(0.1f,0.1f,0.1f),4.0f);
//
//    //Define 3D Environment
//    SimpleUniverse m_3dUniverse     = null; //3D Environment
//    OrbitBehavior  m_OrbitBehaviour = null; //3D Rotation Object
//    BoundingSphere m_BoundingSphere = null; //Viewing Sphere
//    
//    //Non Static Member Variables
//    CPopulation m_Pop;       //Population Space
//    CObstacle[] m_Obstacles; //Obstacle Space
//    int         m_nSize;     //Number of Obstacles
//    CObstacle   m_Goal;      //Target Point for Path Planning
//    CObstacle   m_Start;     //Start Point for Path Planning
//    Font        m_Font;      //Font Object
//    int         m_StopFlag;  //Flag used for Quitting Applet
//    
//    //Constructor for the Path Finding Algorithm
//    public CGaPathFinding(int nPopsize,int nDiam,int nGridScale,int nMaxspeed,
//                          int nMaxforce,int nLength,int nWidth,int nHeight,
//                          float fMutRate)
//    {
//        //Open Drawing Window
//        super                   ("Path Planning using GA Top View");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBackground           (Color.white);
//        setSize                 (nLength,nWidth);
//        setIgnoreRepaint        (true);
//        setVisible              (true);
//        this.addKeyListener     (this);
//        this.addMouseListener   (this);
//        
//        //Initialise Member Variables
//        m_PopSize   = nPopsize;
//        m_Diam      = nDiam;
//        m_GridScale = nGridScale;    
//        m_MaxSpeed  = nMaxspeed;
//        m_MaxForce  = nMaxforce;
//        m_Length    = nLength;
//        m_Width     = nWidth;
//        m_Height    = nHeight;
//        m_MutRate   = fMutRate;
//        m_Goal      = new CObstacle(m_Length-m_Diam,400,10,m_Diam,m_Diam,m_Diam);
//        m_Start     = new CObstacle(m_Diam,m_Width-m_Diam,10,m_Diam,m_Diam,m_Diam);
//        m_LifeTime  = m_Width/2;
//        m_RecTime   = m_LifeTime;
//        m_LifeCycle = 0;
//        m_StopFlag  = 0;
//        
//        //Initialise New Population
//        m_Pop = new CPopulation(m_PopSize,100,0,m_MutRate,nLength,nWidth,
//                                nHeight,nDiam,m_Start,m_Goal);
//
//        //Create Obstacles
//        m_nSize = 2;
//        m_Obstacles    = new CObstacle[m_nSize];
//        m_Obstacles[0] = new CObstacle(m_Length/4,40,0,10,80,10);
//        m_Obstacles[1] = new CObstacle(m_Length/2,m_Width-m_Width/2+10,0,10,
//                                       m_Width/2-10,100);
//      
//        //Load Font
//        m_Font = new Font("Arial",Font.BOLD,16);       
//    }
//    
//    //Run Routine
//    public int run()
//    {
//        //Propagate Chromosomes for a LifeCyle of LifeTime
//        if (m_LifeCycle < m_LifeTime) 
//        {
//            m_Pop.propagateGen(m_Obstacles,m_nSize,super.getGraphics());
//            if(m_Pop.targetReached() && (m_LifeCycle < m_RecTime)) 
//                m_RecTime = m_LifeCycle;
//        
//            m_LifeCycle++;
//        } 
//        else 
//        {
//            m_LifeCycle = 0;
//            m_Pop.calcFit();
////            writeGenInfo();
//            m_Pop.generateMatingPool();
//            m_Pop.generateNewPop();
//        }
//        UpdateGraph(getGraphics());
//        return(m_StopFlag);
//    }
//    
//    //Write Population Information into Log Files
//    public void writeGenInfo()
//    {
//        try
//        {
//            FileWriter     FitLog    = new FileWriter("Fitnesses.txt",true);
//            BufferedWriter FitLogOut = new BufferedWriter(FitLog);
//            FitLogOut.write(String.valueOf(m_Pop.getTotalFitness()));
//            FitLogOut.close();
//        }
//        catch (Exception e)
//        {
//            System.err.println("Error: " + e.getMessage());
//        }
//    }
//    
//    //Painting Routine for Algorithm
//    public void UpdateGraph(Graphics Graph) 
//    {
//        Graph.clearRect(0, 0, m_Length, m_Width);
//        
//        //Draw Initial and Final Locations
//        m_Start.render(Graph,Color.green,"Start");
//        m_Goal.render (Graph,Color.red,  "Goal");
//     
//        //Render Obstacles
//        for (int i=0;i<m_nSize;i++) 
//        {
//             CObstacle Obs = m_Obstacles[i];
//             Obs.render(Graph,Color.black,"");
//        }
//        
//        //Render Vehicles
////        for (int j=0;j<m_PopSize;j++) 
////        {
////             CVehicle Veh = m_Pop.m_Population[j];
////             
////        }
//    }
//    
//    public void mousePressed(MouseEvent e) 
//    {
//        //obstacles.add(new Obstacle(mouseX,mouseY));
//        m_Goal = new CObstacle(e.getX()-m_Diam/2,e.getY(),0,-m_Diam/2,
//                               m_Diam,m_Diam);
//        
//        m_RecTime = m_LifeTime;
//        
//        //Repaint all Components
//        super.repaint();
//    }
//
//    public double init()
//    {
//        
//        // Creates and sets up a canvas
//        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
//        setLayout(new BorderLayout());
//        add("Center", canvas);
//
//        universe = new SimpleUniverse(canvas);
//        ViewingPlatform viewPlatform = universe.getViewingPlatform();
//
//        // Limit the viewable sphere to a radius of 100.0f
//        boundingSphere = new BoundingSphere(new Point3d(0f, 0f, 0f), 100f);
//
//        // Create sceneRootBG, the root BranchGroup of the universe
//        BranchGroup sceneRootBG = new BranchGroup();
//
//        // A Transform3D which will apply to our whole scene
//        Transform3D sceneT3d = new Transform3D();
//
//        // Rotate scene for a nicer first angle
//        sceneT3d.setEuler(new Vector3d(Math.toRadians(35), 
//                                       Math.toRadians(310), 
//                                       Math.toRadians(330)));
//
//        // Scale whole scene down a bit
//        sceneT3d.setScale(0.008d);
//
//        // Create a TransformGroup, apply sceneT3D it
//        TransformGroup sceneTG = new TransformGroup();
//        sceneTG.setTransform(sceneT3d);
//
//        sceneTG.addChild(createLightedScene());
//
//        // Add our created scene sceneTG to root BranchGroup, sceneRootBG
//        sceneRootBG.addChild(sceneTG);
//
//        // Optimises the scene graph
//        sceneRootBG.compile();
//
//        // Add our root BranchGroup to universe
//        universe.addBranchGraph(sceneRootBG);
//
//        viewPlatform.setNominalViewingTransform();
//
//        // Add a behaviour to viewPlatform, allowing the user to rotate, zoom, and straff the scene
//        orbitBehaviour = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL | OrbitBehavior.STOP_ZOOM);
//        orbitBehaviour.setSchedulingBounds(boundingSphere);
//        viewPlatform.setViewPlatformBehavior(orbitBehaviour);
//        
//        super.setVisible(true);
//        return 1;
//    }
//    
//    public void destroy()
//    {
//        universe.removeAllLocales();
//        m_StopFlag = 1;
//    }
//    
//    //Handle Keyboard Events
//    public void keyPressed(KeyEvent e) 
//    {
//        //If Escape Key was Pressed
//        if(e.getKeyChar() == 27)
//        {
//            try 
//            {
//                destroy();
//            }catch (Throwable ex) 
//            {
//            Logger.getLogger(CGaPathFinding.class.getName()).log(Level.SEVERE, 
//                             null, ex);
//            }
//        }
//    }
//    
//    public void keyTyped(KeyEvent e)        {}
//
//    public void keyReleased(KeyEvent e)     {}
//
//    public void mouseClicked(MouseEvent e)  {}
//
//    public void mouseReleased(MouseEvent e) {}
//
//    public void mouseEntered(MouseEvent e)  {}
//
//    public void mouseExited(MouseEvent e)   {}
//
//    public void mouseDragged(MouseEvent e)  {}
//
//    public void mouseMoved(MouseEvent e)    {}
//    
//    protected Group createSceneGraph() 
//    {
//        //Create Transform Objects
//        TransformGroup sceneTG   = new TransformGroup();
//        Transform3D kitchenTG3D  = null;
//        TransformGroup kitchenTG = null;
//
//        //Create Floor Object and add to Scene
//        Appearance floorAppearance = new Appearance();
//        floorAppearance.setMaterial(floorMat);
//        sceneTG.addChild(new Box(64.0f,0.015f,64.0f,Box.GENERATE_TEXTURE_COORDS,
//                                 floorAppearance));
//
//        //Create Wall Object
//        Appearance wallAppearance = new Appearance();
//        TransparencyAttributes att;
//        att = new TransparencyAttributes(TransparencyAttributes.BLENDED,0.5f);
//        wallAppearance.setTransparencyAttributes(att);
//        wallAppearance.setMaterial(wallMat);
//        Box wall = new Box(64.0f,0.015f,64.0f,Box.GENERATE_TEXTURE_COORDS, 
//                           wallAppearance);
//
//        //Create Walls and add To Scene
//        SharedGroup wallShared = new SharedGroup();
//        wallShared.addChild(wall);
//
//        // Translate and add the first wall to kitchenTG
//        kitchenTG3D = new Transform3D();
//        kitchenTG3D.setTranslation(new Vector3f(0f, 128.0f, 0f));
//        kitchenTG = new TransformGroup(kitchenTG3D);
//        kitchenTG.addChild(new Link(wallShared));
//        sceneTG.addChild(kitchenTG);
//
//        // Translate and add the second wall to kitchenTG
//        kitchenTG3D = new Transform3D();
//        kitchenTG3D.rotZ(Math.PI / 2); // Rotate around Z by 90 degrees
//        kitchenTG3D.setTranslation(new Vector3f(-64.f, 64.0f, 0f));
//        kitchenTG = new TransformGroup(kitchenTG3D);
//        kitchenTG.addChild(new Link(wallShared));
//        sceneTG.addChild(kitchenTG);
//
//        // Translate and add the third wall to kitchenTG
//        kitchenTG3D = new Transform3D();
//        kitchenTG3D.rotZ(Math.PI / 2); // Rotate around Z by 90 degrees
//        kitchenTG3D.setTranslation(new Vector3f(64.f, 64.0f, 0f));
//        kitchenTG = new TransformGroup(kitchenTG3D);
//        kitchenTG.addChild(new Link(wallShared));
//
//        // Translate and add the fourth wall to kitchenTG
//        sceneTG.addChild(kitchenTG);
//        kitchenTG3D = new Transform3D();
//        kitchenTG3D.rotX(Math.PI / 2); // Rotate around X by 90 degrees
//        kitchenTG3D.setTranslation(new Vector3f(0.f, 64.0f, -64.0f));
//        kitchenTG = new TransformGroup(kitchenTG3D);
//        kitchenTG.addChild(new Link(wallShared));
//        sceneTG.addChild(kitchenTG);
//
//        // Move the whole scene down 1.5
//        kitchenTG3D = new Transform3D();
//        kitchenTG3D.setTranslation(new Vector3f(0f, -46.0f, 0f));
//        sceneTG.setTransform(kitchenTG3D);
//
//        // Create new BranchGroup, and add the newly created scene 'sceneTG' to it
//        Group objRoot = new BranchGroup();
//        objRoot.addChild(sceneTG);
//
//        // Return the created scene to calling method
//        return objRoot;
//    }
//
//    protected Group createLightedScene() {
//
//        Light lightSrc;
//
//        // The root node of the scene
//        Group objRootBG = new BranchGroup();
//
//        // Adds the unlit scene to objRootBG
//        objRootBG.addChild(createSceneGraph());
//
//        // Create ambient lighting
//        lightSrc = new AmbientLight(new Color3f(0.3f, 0.3f, 0.3f));
//        lightSrc.setInfluencingBounds(boundingSphere);
//        objRootBG.addChild(lightSrc);
//
////        //TODO: REMOVE - just a test
////        Sphere test = new Sphere(.1f);
////        TransformGroup test1 = new TransformGroup();
////        test1.addChild(test);
////        Transform3D SphereMove = new Transform3D();
////        SphereMove.setTranslation(new Vector3f(0.0f, 3.5f, 0.0f));
////        test1.setTransform(SphereMove);
////        objRootBG.addChild(test1);
//
//        //Return the light scene
//        return objRootBG;
//    }
//}
//
////------------------------------------------------------------------------------
////------------------------------------------------------------------------------