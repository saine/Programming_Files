%--------------------------------------------------------------------------
%Insert Node Information into Open List
%--------------------------------------------------------------------------

function NewNode = OpenListAdd(Node,Parent,hn,fn,gn)
    
    xNode = Node(1,1);
    yNode = Node(1,2);
    zNode = Node(1,3);
    
    xParent = Parent(1,1);
    yParent = Parent(1,2);
    zParent = Parent(1,3);
    
    NewNode = [1,xNode,yNode,zNode,xParent,yParent,zParent,hn,gn,fn];
end

%--------------------------------------------------------------------------
%--------------------------------------------------------------------------