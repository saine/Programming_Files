%This function returns the index of the location of a node in the list OPEN
function Index = node_index(OpenList,Node)
    
    Index = 1;
    
    %Parse Through OpenList
    while OpenList(Index,2)~=Node(1,1) || OpenList(Index,3)~=Node(1,2) || OpenList(Index,4)~=Node(1,3)
        Index = Index+1;
    end
end