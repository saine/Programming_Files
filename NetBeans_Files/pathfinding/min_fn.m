function minIndex = min_fn(OpenList,OLCount,Goal)
 
    %Initialise Variables
    tArray      = [];
    c           = 1;
    GoalReached = 0;
    GoalIndex   = 0;
    tMin        = 0;
 
    %Get all Nodes from the OpenList
    for i = 1:OLCount
        if OpenList(i,1) == 1
            tArray(c,:) = [OpenList(i,:) i];
            nOL         = [OpenList(i,2),OpenList(i,3),OpenList(i,4)];
            
            %Store the Index Target Node
            if (nOL == Goal)
                GoalReached = 1;
                GoalIndex   = i;
            end;
            c = c+1;
        end
    end

    %If one of the Neighbours is the Goal Node
    if GoalReached == 1 
        minIndex = GoalIndex;
    end
    
    %Get Index of the smallest cost Node
    if size(tArray ~= 0)
        [Temp,tMin] = min(tArray(:,10));
        minIndex    = tArray(tMin,11);
    else
        %If there is no Path Return -1
        minIndex = -1;
    end
end

%--------------------------------------------------------------------------
%--------------------------------------------------------------------------