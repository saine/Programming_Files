%--------------------------------------------------------------------------
%TITLE      : POTENTIAL FIELD CLASS
%AUTHOR     : PAUL BERTEMES
%CREATED    : 10.06.2010
%VERSION    : 1.0
%DESCRIPTION: Example showing Potential Field atraction/reaction forces. 
%             The checkking fucntion used in this code will return the nearest 
%             deeper location (a1,b1) around current location (a,b) in 
%             a 3D plane which altitude/depth is saved in matrix A.
%             10 x 10 Matrix grid converted to a 100 x 100 grid !!
%
%             [A] = Mobile2(90,[3,1],70,[5,7],70,[8,3],15,[8,8]); 
%             [A] = Mobile2(90,[3,1],70,[3,5],70,[8,8],15,[7,4]); 
%             [A] = Mobile2(90,[3,1],70,[2,6],70,[5,5],15,[8,8]); 
%             [A] = Mobile2(90,[3,1],70,[3,5],70,[5,10],15,[10,10]);
%
%--------------------------------------------------------------------------
%HELP
%--------------------------------------------------------------------------
%
%Functions:
%   1. Constructor for the CPotentialField Class.

%
%--------------------------------------------------------------------------

%--------------------------------------------------------------------------
%CDNA CLASS DEFINITION
%--------------------------------------------------------------------------

classdef CPotentialField < handle

%--------------------------------------------------------------------------
%CLASS PROPERTIES
%--------------------------------------------------------------------------

properties (SetAccess='private', GetAccess='private')
    m_Start;
    m_Width;
    m_Height;
    m_Goal;
    m_Obstacles;
    m_NumCells;
    m_Diam;
    m_ObsDist;
    m_GoalDist;
    m_Field;
    m_B;
    m_A;
end

%--------------------------------------------------------------------------
%CLASS METHODS
%--------------------------------------------------------------------------

methods
    
    %Constructor (makes a DNA of random Vectors)
    function this = CPotentialField(Start,Goal,Obstacles,Width,Height,Diam)
        
        %Populate Member Variables
        this.m_Start       = Start;
        this.m_Goal        = Goal(1,1:2);
        this.m_Obstacles   = Obstacles(:,1:2);
        this.m_Width       = Width;
        this.m_Height      = Height;

        %Calculate Number of Cells
        this.m_NumCells(1) = ceil(Width/Diam);
        this.m_NumCells(2) = ceil(Height/Diam);

        for i = 1:this.m_NumCells(1)
            for j = 1:this.m_NumCells(2)
                
                %Calculate Distance to Obstacles for each Cell
                Obs = Obstacles(:,1:2);
                this.m_ObsDist(i,j,:) = this.MyDist([i*Diam,j*Diam],Obs);
               
                %Calculate Distance to Goal for each Cell
                this.m_GoalDist(i,j) = sqrt(this.MyDist([i*Diam,i*Diam],Goal(1,1:2)));
                
                %Calculate Field Intensity for each Cell + 0.001 as
                %Division By Zero Protection
                ObsDist(1,:)      = this.m_ObsDist(i,j,:);
                FObs              = Obstacles(:,3)'./(ObsDist+0.001);
                this.m_Field(j,i) = sum(FObs)+Goal(1,3)*this.m_GoalDist(i,j); 
               
                %Clipping of High Values
                if this.m_Field(j,i) > 300
                	this.m_Field(j,i) = 300; 
                end
            end
        end
    end       
    
    %Calculate Euclidiean Distance Between two Points
    function Dist = MyDist(this,Point1,Point2)
        Dist = (Point1(:,1)-Point2(:,1)).^2+(Point1(:,2)-Point2(:,2)).^2;
    end
    
    %Search For Path
    function searchPath(this)

        a = 1; 
        b = 1; 
        
        this.m_B = zeros(length(this.m_Field)); 
        
        Value = (abs(a-this.m_Goal(1,1)*10)+abs(b-this.m_Goal(1,2)*10)); 

        while Value > 0,
            [a1,b1] = checking(this.m_Field,a,b,100,100); 

            if a1 == a && b1 == b
                a = a1; 
                b = b1; 
                this.m_B(a,b) = 15; 
                break;
            else
                a      = a1;
                b      = b1;
                this.m_B(a,b) =15; 
            end

            Value = (abs(a-this.m_Goal(1,1)*10)+abs(b-this.m_Goal(1,2)*10)); 
        end
    end
    
    %Checking Function

    function showPath(this)
        
        i = 1:100;
        j = 1:100;
        %Show Path Plot in 3D
        figure;
        mesh(i,j,this.m_Field(i,j)) 

        %axis([1, this.m_Width/this.m_Diam,1,this.m_Height/This.m_Diam,-10,300]); 
        %view([-20,-15,20]) 

        figure;
        contour(i,j,this.m_Field(i,j),60); 
    end
end

%--------------------------------------------------------------------------
end
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------


