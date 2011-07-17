//------------------------------------------------------------------------------
//TITLE:       CVEHICLE CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED:     15/07/2010
//VERSION:     V2.0 
//DESCRIPTION: 
//
//Functions: 
//
//      1.  public                    CDNA(int nLength) 
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import com.sun.j3d.utils.geometry.Sphere;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

//------------------------------------------------------------------------------
//CVEHICLE CLASS DEFINITION
//------------------------------------------------------------------------------

public class CVehicle 
{
    //Member Variables
    double[][] m_Locs;
    double[] m_Loc;         //Current Location
    double[] m_Target;      //Goal Location
    double[] m_Vel;         //Velocity Vector
    double[] m_Acc;         //Acceleration Vector
    double   m_fRecDist;    //Minimum Distance To Target
    double   m_fFit;        //Vehicle Fitness
    CDNA     m_Genes;       //Gene Information for Vehicle
    boolean  m_Stopped;     //If Collision is Detected Vehicle is Stopped
    int      m_nFinish;     //If Vehicle reached Goal it gets a Finish Number
    int      m_nDiam;       //Diameter of Cells
    int      m_nLength;     //Length of Environment
    int      m_nWidth;      //Width of Environment
    int      m_nHeight;     //Height of Environment
    int      m_nGridScale;  //Scaling Factor of Cells
    double   m_MaxSpeed;    //Maximum Speed
    double   m_MaxForce;    //Maximum Acceleration per Iteration
    int      m_LocCount;    //Number of Iterations to Finish
    
    //Pointer to Transform Object
    TransformGroup m_objTG;
    
    //Constructor for CVehicle Class
    public CVehicle(double[] Loc,double[] Tar,CDNA DNA,int nFnsh,double fRecDst,
                    int nDiam,int nLength,int nWidth,int nHeight,int nGrid) 
          
    {
        //Initialise Member Variables
        m_Acc        = new double[3];
        m_Vel        = new double[3];   
        m_Loc        = Loc;
        m_Target     = Tar;
        m_Genes      = DNA;
        m_Stopped    = false;
        m_nFinish    = nFnsh;
        m_fRecDist   = fRecDst;
        m_nDiam      = nDiam;
        m_nLength    = nLength;
        m_nWidth     = nWidth;
        m_nHeight    = nHeight;
        m_nGridScale = nGrid;
        m_MaxSpeed   = 4.0f; 
        m_MaxForce   = 1.0f;
        m_LocCount   = 0;
//        m_Locs      = new double[3000][3];
    }
  
    //Calculate Fintess of Chromosome
    public void calcFitness() 
    {
        //Retrive Distance Travelled by Vehicle
        double fDist = m_fRecDist;
        if(fDist < m_nDiam/2)      fDist      = 1.0f;
        
        //Fitness is based on finishing quicker and on getting closer to the 
        //target The m_nFinish variable is a measure of the order in which the
        //Individuals arrive at the goal location and, hence, how fast they got 
        //to the Target.
        m_fFit  = ((1.0f/Math.pow(m_nFinish,1.5f))*(1.0f/Math.pow(fDist,6.0f)));
        
        if((m_LocCount!=0) && (fDist == 1))
            m_fFit += 1.0f/Math.pow(m_LocCount,4.0f);  
        
    }
    
    //Set Finishing Order
    public void setFinish(int nFinish) 
    {
        m_nFinish = nFinish;
    }
  
    //Propagate the Vehicle through the environment for 1 Iteration
    public void run(CObstacle[] Obs, int nSize) 
    {
        if(!m_Stopped) 
        {
            //Update Position Information for Vehicle
            Update();
          
            //Check for Collisions
            if ((Borders())|(Obstacles(Obs,nSize))) 
            {
                 m_Stopped = true;
            }
        } 
    }
  
    //Check for Collision with Environment Edges 
    public boolean Borders() 
    {
        if((m_Loc[0]<0)         |(m_Loc[1]<0)        |m_Loc[2]<0|
           (m_Loc[0]>=m_nLength)|(m_Loc[1]>=m_nWidth)|(m_Loc[2]>=m_nHeight))
            return true;
        else
            return false;
    } 
  
    //Calculate Euclidean Distance Between two Points
    public double dist(double[] P1,double[] P2)   
    {
        double xdiff = P2[0]-P1[0];
        double ydiff = P2[1]-P1[1];
        double zdiff = P2[2]-P1[2];
        double temp  = Math.pow(xdiff,2)+Math.pow(ydiff,2)+Math.pow(zdiff,2);

        return(Math.sqrt(temp));     
    }
  
    //Collison Check between a 3d Vector and a Cube
    public boolean contains(double[] Loc,double[] Dest)
    {
      if(m_Loc[0]>=m_Target[0] && m_Loc[0]<=m_Target[0]+m_nDiam &&
         m_Loc[1]>=m_Target[1] && m_Loc[1]<=m_Target[1]+m_nDiam &&
         m_Loc[2]>=m_Target[2] && m_Loc[2]<=m_Target[2]+m_nDiam)
          return true;
      else
          return false;
    }
  
    //Check if Vehicle has Reached Target
    public boolean Finished() 
    {
        //Calculate Distanc between Current Location and Goal Location
        double fDist = dist(m_Loc,m_Target);
      
        //If Finished Quicker Than Before save New Time
        if(fDist < m_fRecDist) m_fRecDist = fDist;

        //Check if Vehicle is in Target
        if(contains(m_Loc,m_Target))
        {
            contains(m_Loc,m_Target);
            return true;
        }
        return false;
    }
  
    //Check for Collision with Obstacle
    public boolean Obstacles(CObstacle[] Obstacles, int nSize) 
    {
        //Parse through all Obstacles
        for(int i=0;i<nSize;i++) 
        {
            CObstacle Obs = Obstacles[i];
            if (Obs.contains(m_Loc)) return true;
        }
        return false;
    }
  
    //Return Fitness Value
    public double getFitness() 
    {
        return m_fFit;
    }

    //Return Genes
    CDNA getGenes() 
    {
        return m_Genes;
    }

    //Return Stopped State
    public boolean Stopped() 
    {
        return m_Stopped;
    }
  
    //Limit Value between MIN and MAX
    public double constrain(double nVal,double dMIN, double dMAX)
    {
        if(nVal < dMIN) nVal = dMIN;
        if(nVal > dMAX) nVal = dMAX;
      
        return nVal;
    }
    
    //Update Position of Chromosome
    void Update() 
    {
        if (!Finished()) 
        {
            //Select Gene based on Current Location within the Cells of the 
            //Environment 
            double x = (double) m_Loc[0]/m_nDiam;
            double y = (double) m_Loc[1]/m_nDiam;
            double z = (double) m_Loc[2]/m_nDiam;
        
            //Limit Values so They do not Go over the Edges
            x = constrain(x,0,-1+m_nLength/m_nDiam);
            y = constrain(y,0,-1+m_nWidth/m_nDiam);
            z = constrain(z,0,-1+m_nHeight/m_nDiam);

            //Get the Acceleration Vector from Chromosome Information
            double xFactor = x;
            double yFactor = (y*m_nLength/m_nDiam);
            double zFactor = (z*(m_nWidth/m_nDiam)*(m_nLength/m_nDiam));
            double temp2   = xFactor+yFactor+zFactor;
            double[] Genes = m_Genes.getGene(Math.floor(temp2));
            
            m_Acc[0] = Genes[0]*m_MaxForce;
            m_Acc[1] = Genes[1]*m_MaxForce;
            m_Acc[2] = Genes[2]*m_MaxForce;

            m_Vel[0] += m_Acc[0];
            m_Vel[1] += m_Acc[1];
            m_Vel[2] += m_Acc[2];
            
            //Update Vehicle Velocity
            m_Vel[0] = constrain(m_Vel[0],-m_MaxSpeed,m_MaxSpeed);
            m_Vel[1] = constrain(m_Vel[1],-m_MaxSpeed,m_MaxSpeed);
            m_Vel[2] = constrain(m_Vel[2],-m_MaxSpeed,m_MaxSpeed);
        
            //Update Position Information for Vehicle
            m_Loc[0] += m_Vel[0];
            m_Loc[1] += m_Vel[1];
            m_Loc[2] += m_Vel[2];
            
//            m_Locs[m_LocCount][0] = m_Loc[0];
//            m_Locs[m_LocCount][1] = m_Loc[1];
//            m_Locs[m_LocCount][2] = m_Loc[2];
//            m_LocCount++;
            
            //Reset Acceleration Values
            m_Acc[0] = 0;
            m_Acc[1] = 0;
            m_Acc[2] = 0;
            
            //Create Transformation Variables
            Vector3f translate = new Vector3f();
            Transform3D t3d = new Transform3D();
            m_objTG.getTransform(t3d);
            t3d.get(translate);
            
            //Move the Object to its current Location
            int nDiam = m_nDiam;
            int[] Dims = {m_nLength,m_nWidth,m_nHeight};
            translate.x = 2*(float)m_Loc[0]/nDiam-((Dims[0]-1)/nDiam);
            translate.y = (2*(float)m_Loc[2]/nDiam)+1;
            translate.z = -2*(float)m_Loc[1]/nDiam+((Dims[1]-1)/nDiam);

            //Set Transformation
            t3d.setTranslation(translate);
            m_objTG.setTransform(t3d);
        }
    }
    
    public void writePath(int nGen)
    {
                try
                {
                String Title = "_Finished_"+nGen+".txt";
                FileWriter     FinLog    = new FileWriter(Title);
                BufferedWriter FinLogOut = new BufferedWriter(FinLog);
                
                for(int i=0;i<m_LocCount;i++)
                {
                    FinLogOut.write(String.valueOf(m_Locs[i][0]));
                    FinLogOut.write(",");
                }
                FinLogOut.write("\n");
                for(int j=0;j<m_LocCount;j++)
                {
                    FinLogOut.write(String.valueOf(m_Locs[j][1]));
                    FinLogOut.write(",");
                }
                FinLogOut.write("\n");
                for(int k=0;k<m_LocCount;k++)
                {
                    FinLogOut.write(String.valueOf(m_Locs[k][2]));
                    FinLogOut.write(",");
                }
                FinLogOut.close();
                }
                catch (Exception e)
                {
                    System.err.println("Error: " + e.getMessage());
                }
    }

    //Drawing Function
    public void render(BranchGroup sceneRoot,Color3f Col,Canvas3D Can,
                       double[] Pos,int[] Dims,int nDiam) 
    { 
        //Create Tranform and Appearance Objects
        Transform3D           objT      = new Transform3D();
        Appearance            App       = new Appearance();
        ColoringAttributes    CA        = new ColoringAttributes();
        Vector3f              translate = new Vector3f();
        
        //Create SPhere and Set Behaviour
        CA.setColor(Col);
        App.setColoringAttributes(CA);
        
        //Move Object to its Starting Point
        translate.x = 2*(float)Pos[0]/nDiam-((Dims[0])/nDiam);
        translate.y = (2*(float)Pos[1]/nDiam)+2;
        translate.z = -2*(float)Pos[2]/nDiam+((Dims[1])/nDiam);
        objT.setTranslation(translate);
        m_objTG = new TransformGroup(objT);
        m_objTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        m_objTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 

        //Add Moving Behaviour to Scene Root
        m_objTG.addChild(new Sphere(0.3f,App));
        sceneRoot.addChild(m_objTG); 
    }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------