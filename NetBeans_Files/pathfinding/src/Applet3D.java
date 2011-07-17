//------------------------------------------------------------------------------
//TITLE:       APPLET CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED:     15/08/2010
//VERSION:     V1.0 
//DESCRIPTION: This class is used to run the Genetic algorithm as a standalone 
//             applet
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import java.applet.Applet;

//------------------------------------------------------------------------------
//APPLET CLASS DEFINITION
//------------------------------------------------------------------------------

public class Applet3D extends Applet
{


    public static void main(String[] args) 
    {
            //Initialise GA Parameters
        int nPopsize    = 200;
        int nDiam       = 50;
        int nGridScale  = 50;
        int nMaxspeed   = 4;
        int nMaxforce   = 1;
        int nLength     = 1000;
        int nWidth      = 600;
        int nHeight     = 600;
        float fMutRate  = 0.05f;
        int   Method    = 0;
        
        //Create New Instance of Path Planning Object
        CGaPathFinding GA = new CGaPathFinding(nPopsize,nDiam,nGridScale,
                                               nMaxspeed,nMaxforce,nLength,
                                               nWidth,nHeight,fMutRate,Method);
        
        //Create Start and Goal Points
        GA.setStartPoint(0,0,0);
        GA.setEndPoint(nLength/2+3*nDiam,nWidth/2,3*nDiam);
        
        //Create Obstacles
        GA.createObstacle(0,2*nDiam,0,3*nDiam,nDiam,3*nDiam);
        GA.createObstacle(3*nDiam,0,0,nDiam,3*nDiam,3*nDiam);
        GA.createObstacle(nLength/2,(nWidth/2)-2*nDiam,0,nDiam,nDiam,4*nDiam);
        GA.createObstacle(nLength/2,(nWidth/2)+2*nDiam,0,nDiam,nDiam,4*nDiam);
        GA.createObstacle(nLength/2,(nWidth/2)-2*nDiam,4*nDiam,nDiam,5*nDiam,nDiam);
        
        GA.initialiseGA();
    }
}
