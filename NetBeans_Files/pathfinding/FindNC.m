%--------------------------------------------------------------------------
%Find Adjacent Cells and evaluate Cost
%--------------------------------------------------------------------------

function NC = FindNC(Node,Goal,hn,ClosedList,Length,Width,Height)

    %Initialise Variables
    NC      = [];
    NCCount = 1;
    CLNum   = size(ClosedList,1);
    
    %Find Adjacent Cells
    for i = 1:-1:-1
        for j = 1:-1:-1
            for k = 1:-1:-1
            	%Node Itself is not a Neighbour
            	if i~=0 || j~=0 || k~=0
                    %Get Neighbouring Node
                    NCx = Node(1,1)+i;
                    NCy = Node(1,2)+j;
                    NCz = Node(1,3)+k;
                	nNC = [NCx,NCy,NCz];
                        
                    %Node Within Area
                    if nNC(1,1)>0&&nNC(1,2)>0&&nNC(1,3)>0&&nNC(1,1)<=Length&&nNC(1,2)<=Length&&nNC(1,3)<=Length
                    	%Copy Cell Coordinates
                        Flag = 1;                    
                        for c = 1:CLNum
                        	nCL = ClosedList(c,:);
                            if(nNC == nCL)
                                Flag = 0;
                            end 
                        end
                    
                        %Add Cells to NC List
                        if (Flag == 1)
                            NC(NCCount,1)   = NCx;
                            NC(NCCount,2)   = NCy;
                            NC(NCCount,3)   = NCz;
                            NC(NCCount,4)   = hn + Distance(Node,nCL,0);
                            NC(NCCount,5)   = Distance(Goal,nNC,0);
                            NC(NCCount,6)   = NC(NCCount,4)+NC(NCCount,5);
                            NCCount         = NCCount + 1;
                        end
                    end
                end
            end
        end
    end
    Flag = 0;
end

%--------------------------------------------------------------------------
%--------------------------------------------------------------------------