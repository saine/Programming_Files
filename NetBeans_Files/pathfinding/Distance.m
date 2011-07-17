%--------------------------------------------------------------------------
%Calculate Euclidian Distance between 2 Points 
%--------------------------------------------------------------------------

function Dist = Distance(Point1,Point2,Method)
    
    switch Method
        case{0}
            %Calculate Distance Components dx,dy,dz
            dx   = Point1(1,1)-Point2(1,1);
            dy   = Point1(1,2)-Point2(1,2);
            dz   = Point1(1,3)-Point2(1,3);
            
            %Calculate Euclidian Distance
            Dist = sqrt(dx^2+dy^2+dz^2);
    end
end

%--------------------------------------------------------------------------
%--------------------------------------------------------------------------