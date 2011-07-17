//------------------------------------------------------------------------------
//TITLE:       CGAPATHFINDING CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED: 
//VERSION:     V1.0 
//DESCRIPTION: 
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.Vector3d;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;


//------------------------------------------------------------------------------
//CGAPATHFINDING CLASS DEFINITION
//------------------------------------------------------------------------------

public class CGaPathFinding extends Applet
{
    //Static Member Variables
    static int      m_Length;    //Length of Environment
    static int      m_Width;     //Width of Environment
    static int      m_Height;    //Height of Environment
    static int      m_PopSize;   //Number of Individuals in a Population
    static int      m_GridScale; //Grid Overlay Factor
    static int      m_LifeTime;  //Time used for Propagating Chromosomes 
    static int      m_LifeCycle; //Time that each Generation Survived
    static int      m_RecTime;   //Quickest Time to Finish
    static int      m_Diam;      //Size of Vehicle
    static double   m_MaxSpeed;  //Maximum Velocity
    static double   m_MaxForce;  //Maximum Acceleration
    static float    m_MutRate;   //Mutation Probability
    static Material m_FloorMat;  //Floor Material
    static Material m_WallMat;   //Wall Material
    static Material m_ObsMat;    //Obstacle Material
    
    //Non Static Member Variables
    Frame          m_Frame;          //Applet Frame
    BranchGroup    m_sceneRoot;      //Root Graphics Object
    Canvas3D       m_Canvas;         //3D Window
    SimpleUniverse m_3dUniverse;     //3D Environment
    BoundingSphere m_BoundingSphere; //Viewing Sphere
    CPopulation    m_Pop;            //Population Space
    Timer          m_Timer;          //Timer for Triggering Run Function
    CObstacle      m_Goal;           //Target Point for Path Planning
    CObstacle      m_Start;          //Start Point for Path Planning
    CObstacle[]    m_Obstacles;      //Obstacle Space
    int            m_Method;         //GA Strategy
    Font           m_Font;           //Font Object
    int            m_StopFlag;       //Flag used for Quitting Applet
    int            m_ObsCount;       //Number of Obstacles
    int            m_currMaxGen;     //Store Generation Number for m_currMaxFit
    double         m_currMaxFit;     //Store max Finess for all Generations 
    
    //Constructor for the Path Finding Algorithm
    public CGaPathFinding(int nPopsize,int nDiam,int nGridScale,int nMaxspeed,
                          int nMaxforce,int nLength,int nWidth,int nHeight,
                          float fMutRate,int Method)
    {   
        //Initialise Member Variables
        m_currMaxFit     = 0;
        m_currMaxGen     = 0;
        m_Timer          = null;
        m_Frame          = null;
        m_3dUniverse     = null; 
        m_BoundingSphere = null;
        m_ObsCount       = 0;
        m_PopSize        = nPopsize;
        m_Diam           = nDiam;
        m_GridScale      = nGridScale;    
        m_MaxSpeed       = nMaxspeed;
        m_MaxForce       = nMaxforce;
        m_Length         = nLength;
        m_Width          = nWidth;
        m_Height         = nHeight;
        m_MutRate        = fMutRate;
        m_LifeTime       = m_Length/2;
        m_RecTime        = m_LifeTime;
        m_Method         = Method;
        m_LifeCycle      = 0;
        m_StopFlag       = 0;
        m_Obstacles      = new CObstacle[512]; 
        m_FloorMat       = new Material(new Color3f(Color.gray),
                                        new Color3f(Color.red),
                                        new Color3f(0.2f,0.2f,0.2f),
                                        new Color3f(.1f, .1f, .1f),4.0f);
        m_WallMat        = new Material(new Color3f(Color.green),
                                        new Color3f(Color.red),
                                        new Color3f(0.2f,0.2f,0.2f),
                                        new Color3f(0.1f,0.1f,0.1f),4.0f);
    }
    
    //Set StartPoint for Chromosomes
    public void setStartPoint(int xPos,int yPos,int zPos)
    {
        m_Start = new CObstacle(xPos,yPos,zPos,m_Diam,m_Diam,m_Diam);
    }
    
    //Set GoalPoint for Chromosomes
    public void setEndPoint(int xPos,int yPos,int zPos)
    {
        m_Goal = new CObstacle(xPos,yPos,zPos,m_Diam,m_Diam,m_Diam);
    }
    
    //Creaste Obstacles by Assembling cubes of Diam^3 
    public void createObstacle(int xPos,int yPos,int zPos,int nLength,
                               int nWidth, int nHeight)
    {
        for(int i=0;i<nLength;i+=m_Diam)
        {
            for(int j=0;j<nWidth;j+=m_Diam)
            {
                for(int k=0;k<nHeight;k+=m_Diam)
                {
                    CObstacle Obs = new CObstacle(xPos+i,yPos+j,zPos+k,m_Diam,
                                                  m_Diam,m_Diam);
                    m_Obstacles[m_ObsCount++] = Obs;
                }
            }
        }
    }
    
    //Initialise Window Frame
    public void initialiseGA()
    {
        //Initialise New Population
        m_Pop = new CPopulation(m_PopSize,m_MutRate,m_Length,m_Width,
                                m_Height,m_Diam,m_Start,m_Goal);
        
        //Initialise Graphics Window
        m_Frame = new MainFrame(this,900,900);
    }
    
    //Initialise 3d Graph (triggered by MainFrame())
    @Override
    public void init()
    {
        //Create new Canvas and set Layout
        m_Canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        setLayout(new BorderLayout());
        add("Center",m_Canvas);

        //Initialise 3D Universe
        m_3dUniverse = new SimpleUniverse(m_Canvas);

        //Set Scaling Factor and Rotation of Scene
        TransformGroup sceneTG   = new TransformGroup();
        Transform3D    sceneScaT = new Transform3D();
        Transform3D    sceneRotT = new Transform3D();
        
        sceneScaT.setScale  (.03d);
        sceneRotT.setEuler  (new Vector3d(45.0d,45.0d,45.0d));
        sceneScaT.mul       (sceneRotT);
        sceneTG.setTransform(sceneScaT);
        
        //Add Lighting to Scene
        sceneTG.addChild(createLightedScene());
        
        //Create main BranchGroup
        m_sceneRoot = new BranchGroup();
        m_sceneRoot.addChild(sceneTG); 
        m_sceneRoot.compile();

        // Add our root BranchGroup to universe
        m_3dUniverse.addBranchGraph(m_sceneRoot);
        m_3dUniverse.getViewingPlatform().setNominalViewingTransform();

        //Add Rotation Behaviour to Scene
        OrbitBehavior OrbBehav = new OrbitBehavior(m_Canvas, 
                                     OrbitBehavior.REVERSE_ALL|
                                     OrbitBehavior.STOP_ZOOM);
        
        OrbBehav.setSchedulingBounds(new BoundingSphere());
        m_3dUniverse.getViewingPlatform().setViewPlatformBehavior(OrbBehav); 
        
        //Initialise Triggering for Run Function
        m_Timer = new Timer();
        m_Timer.scheduleAtFixedRate(new RunTask(),100,5);
    }
    
    //Write Population Information into Log Files
    public void writeGenInfo()
    {
        try
        {
            //Get Best Fitness and respective Chromosome Information
            double[]   bestFit  = {0};
            boolean[]  Finished = {false};
            CDNA   bestChrom    = m_Pop.getMaxChrom(bestFit,Finished);
            String Title        = "Gen_"+String.valueOf(m_Pop.getCurrGeneration());
 
            //Save fitness and Generation if Performance improvement
            if(bestFit[0]>=m_currMaxFit && m_Pop.targetReached())
            {
                m_currMaxFit = bestFit[0];
                m_currMaxGen = m_Pop.getCurrGeneration();
            }
            //Write Information to Log Files
            FileWriter     SummLog    = new FileWriter("_Summary.txt");
            BufferedWriter SummLogOut = new BufferedWriter(SummLog);
            FileWriter     BestLog    = new FileWriter(Title+".txt");
            BufferedWriter BestLogOut = new BufferedWriter(BestLog);
            FileWriter     FitLog     = new FileWriter("Fitnesses.txt",true);
            BufferedWriter FitLogOut  = new BufferedWriter(FitLog);
            
            //Save Fitness Information
            FitLogOut.write(String.valueOf(m_Pop.getCurrGeneration()));
            FitLogOut.write(",");
            FitLogOut.write(String.valueOf(m_Pop.getTotalFitness()));
            FitLogOut.write(",");
            FitLogOut.write(String.valueOf(bestFit[0]));
            FitLogOut.write("\n");
            FitLogOut.close ();
            
            //Save Best Individuals
            bestChrom.printChromFile(BestLogOut);
            BestLogOut.close();
            
            //Save Summary Information  
            SummLogOut.write("Total_Gen:       " +String.valueOf(m_Pop.getCurrGeneration()));
            SummLogOut.write(" \n");
            SummLogOut.write("Best_Gen:        " +String.valueOf(m_currMaxGen));
            SummLogOut.write(" \n");
            SummLogOut.write("Maximum_Fit:     " +String.valueOf(m_currMaxFit));
            SummLogOut.write(" \n");
            SummLogOut.write("Start_Pointx:    " +String.valueOf(m_Start.m_x));
            SummLogOut.write(" \n");
            SummLogOut.write("Start_Pointy:    " +String.valueOf(m_Start.m_y));
            SummLogOut.write(" \n");
            SummLogOut.write("Start_Pointz:    " +String.valueOf(m_Start.m_z));
            SummLogOut.write(" \n");
            SummLogOut.write("Goal_Pointx:     " +String.valueOf(m_Goal .m_x));
            SummLogOut.write(" \n");
            SummLogOut.write("Goal_Pointy:     " +String.valueOf(m_Goal.m_y));
            SummLogOut.write(" \n");
            SummLogOut.write("Goal_Pointz:     " +String.valueOf(m_Goal.m_z));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_PopSize:   " +String.valueOf(m_PopSize));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_Length:    " +String.valueOf(m_Length));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_Width:     " +String.valueOf(m_Width));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_Height:    " +String.valueOf(m_Height));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_GridcSale: " +String.valueOf(m_GridScale));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_MaxSpeed:  " +String.valueOf(m_MaxSpeed));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_MaxAcc:    " +String.valueOf(m_MaxForce));
            SummLogOut.write(" \n");
            SummLogOut.write("Envir_MutRate:   " +String.valueOf(m_MutRate));
            SummLogOut.write(" \n");
            SummLogOut.write("Life_Time:       " +String.valueOf(m_LifeTime));
            SummLogOut.write(" \n");
            SummLogOut.write("Diameter:        " +String.valueOf(m_Diam));
            SummLogOut.write("\n");
            
            //Write Obstacle Locations
            for(int i=0;i<m_ObsCount;i++)
            {
                String ObsTitle = "Obstacle_"+ String.valueOf(i);
                SummLogOut.write(ObsTitle+"_x:    "+String.valueOf( m_Obstacles[i].m_x));
                SummLogOut.write(" \n");
                SummLogOut.write(ObsTitle+"_y:    "+String.valueOf( m_Obstacles[i].m_y));
                SummLogOut.write(" \n");
                SummLogOut.write(ObsTitle+"_z:    "+String.valueOf( m_Obstacles[i].m_z));
                SummLogOut.write(" \n");
            }
            
            SummLogOut.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
 
    //Create Graphical Content Hierarchy
    public BranchGroup createSceneGraph() 
    {
        //Root Branch Object
        BranchGroup sceneRoot  = new BranchGroup();
        
        //Create Transform Objects
        TransformGroup roomTG   = new TransformGroup();
        Transform3D    roomTraT = new Transform3D();
        Transform3D    roomRotT = new Transform3D();
        
        //Create Appearence and Material Objects
        TransparencyAttributes att;
        att = new TransparencyAttributes(TransparencyAttributes.BLENDED,0.5f);
        
        Appearance floorApp = new Appearance();
        Appearance wallApp  = new Appearance();

        //Create Floor Object and add to Scene
        floorApp.setMaterial(m_FloorMat);
        Box Floor = new Box (m_Length/m_Diam,0.015f,m_Width/m_Diam,
                             Box.GENERATE_TEXTURE_COORDS,floorApp);
        sceneRoot.addChild  (Floor);
        
        //Create Back Wall and add to Scene
        roomTraT  = new Transform3D();
        wallApp.setMaterial(m_WallMat);
        wallApp.setTransparencyAttributes(att);
        
        Box BWall = new Box    (m_Length/m_Diam,0.015f,m_Height/m_Diam,
                                Box.GENERATE_TEXTURE_COORDS,wallApp);
        roomRotT.rotX          (Math.PI/2.0d);
        roomTraT.setTranslation(new Vector3f(0,-m_Width/m_Diam,-m_Height/m_Diam));
        
        roomRotT.mul               (roomTraT);
        roomTG = new TransformGroup(roomRotT);
        roomTG.addChild            (BWall);
        sceneRoot.addChild         (roomTG);
        
        //Create Front Wall and add to Scene
        roomTraT  = new Transform3D();
        wallApp.setMaterial(m_WallMat);
        wallApp.setTransparencyAttributes(att);
        
        Box FWall = new Box    (m_Length/m_Diam,0.015f,m_Height/m_Diam,
                                Box.GENERATE_TEXTURE_COORDS,wallApp);
        roomRotT.rotX          (Math.PI/2.0d);
        roomTraT.setTranslation(new Vector3f(0,m_Width/m_Diam,-m_Height/m_Diam));
        
        roomRotT.mul               (roomTraT);
        roomTG = new TransformGroup(roomRotT);
        roomTG.addChild            (FWall);
        sceneRoot.addChild         (roomTG);
        
        //Create Right Wall and add to Scene
        roomTraT  = new Transform3D();
        Box RWall = new Box    (m_Height/m_Diam,0.015f,m_Width/m_Diam,
                                Box.GENERATE_TEXTURE_COORDS,wallApp);
        roomRotT.rotZ          (Math.PI/2.0d);
        roomTraT.setTranslation(new Vector3f(m_Height/m_Diam,-m_Length/m_Diam,0));
        roomRotT.mul           (roomTraT);
        
        roomTG = new TransformGroup(roomRotT);
        roomTG.addChild(RWall);
        sceneRoot.addChild(roomTG);
        
        //Create Left Wall and add to Scene
        roomTraT  = new Transform3D();
        Box LWall = new Box(m_Height/m_Diam,0.015f,m_Width/m_Diam,
                            Box.GENERATE_TEXTURE_COORDS,wallApp);
        
        roomRotT.rotZ          (Math.PI/2.0d);
        roomTraT.setTranslation(new Vector3f(m_Height/m_Diam,m_Length/m_Diam,0));
        roomRotT.mul           (roomTraT);
        
        roomTG = new TransformGroup(roomRotT);
        roomTG.addChild   (LWall);
        sceneRoot.addChild(roomTG);
        

        //Render Start and Goal Points
        int[] Dims = {m_Length,m_Width,m_Height};
        
        m_Goal.render (sceneRoot,new Color3f(1.0f,0.0f,0.0f),m_Diam,Dims,true, 
                       m_Canvas);
        m_Start.render(sceneRoot,new Color3f(0.0f,1.0f,0.0f),m_Diam,Dims,false,
                       m_Canvas);

        //Render Obstacles
        for(int i=0;i<m_ObsCount;i++)
        {
            CObstacle Obs = m_Obstacles[i];
            Obs.render(sceneRoot,new Color3f(0.0f,0.0f,0.0f),m_Diam,Dims,false,
                       m_Canvas);
        }
        //Render Individuals
        for(int j=0;j<m_PopSize;j++)
        {
            CVehicle Veh = m_Pop.m_Population[j];
            double[] Pos = {m_Start.m_x,m_Start.m_y,m_Start.m_z};
            Veh.render(sceneRoot,new Color3f(0.0f,0.0f,1.0f),m_Canvas,Pos,Dims,
                       m_Diam);
        }
        //Return Created Scene
        return sceneRoot;
    }
  
    public BranchGroup createLightedScene() 
    {
        Light LightSrc;

        //Add Unlit Scene To Root  
        BranchGroup objRoot = new BranchGroup();
        objRoot.addChild(createSceneGraph());

        //Create ambient lighting
        LightSrc = new AmbientLight(new Color3f(0.3f, 0.3f, 0.3f));
        LightSrc.setInfluencingBounds(new BoundingSphere());
        objRoot.addChild(LightSrc);

        //Return the Scene Plus Lighting
        return objRoot;
    }
    
    public Frame getFrame()
    {
        return(m_Frame);
    }
    
    public int stopApp()
    {
        System.exit(0);
        return(0);
    }
    
    @Override
    public void destroy()
    {
    }
    
    //Main Run Function (Put as a Class to implement Timed Behaviour)
    class RunTask extends TimerTask  
    { 
        //Run Routine
        public void run()
        { 
            //Propagate Chromosomes for a LifeCyle of LifeTime
            if (m_LifeCycle < m_LifeTime) 
            {
                m_Pop.propagateGen(m_Obstacles,m_ObsCount);

                if(m_Pop.targetReached()&(m_LifeCycle < m_RecTime)) 
                    m_RecTime = m_LifeCycle;

                m_LifeCycle++;
            }
            else 
            {
                m_Pop.targetReached();
                System.out.print("Generation: ");
                System.out.println(m_Pop.getCurrGeneration());
                m_LifeCycle = 0;
                m_Pop.calcFit();
                writeGenInfo();

                //Generate New Population
                m_Pop.generateMatingPool();
                m_Pop.generateNewPop(0);
            }
        }
    }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------