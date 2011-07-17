%--------------------------------------------------------------------------
%interactive selection of start goal and obstacles
%--------------------------------------------------------------------------

function [Start,Goal,Map] = INInteract(Length,Width,Height)

    %Generate Matrix Representation of Map
    Map = 2*ones(Width,Length,Height);
    
    %Create Figure
    hFig = figure;
    axis([0 Length 0 Width]);
    grid on;
    hold on;

    %Set Figure Label
    title ('Goal Position');
    xlabel('Please Select the Target using the Left Mouse button');

    %Get Target Coordinates
    BREAK = 0;
    while (BREAK ~= 1) %Repeat until Left Button Released
        [xCoord,yCoord,BREAK]=ginput(1);
    end
    
    %Get Position of Goal Point 
    xGoal = floor(xCoord);
    yGoal = floor(yCoord);

    Prompt = sprintf('Enter the Target Position along the Z Axis (Positive Upwards) <= %d ',Height);
    Title  = 'Z Position for Target Point';
    zGoal  = str2num(cell2mat(inputdlg(Prompt,Title)));

    % Put Goal Position onto Map
    Map(xGoal,yGoal,zGoal) = 0;
    plot(xGoal+.5,yGoal+.5,'gd');
    text(xGoal+1 ,yGoal+.5,'Goal');
    
    Goal = [xGoal,yGoal,zGoal];

%--------------------------------------------------------------------------
    %Get Obstacle Positions
    title ('Obstacle Positions');
    xlabel('Please Select the Obstacles using the Left Mouse button (Use Right Button for Last one)');
    BREAK = 1;
    while BREAK == 1
        [xCoord,yCoord,BREAK] = ginput(1);
        xObs = floor(xCoord);
        yObs = floor(yCoord);

        %Get Height of Object
        Prompt = sprintf('Enter the Target Position along the Z Axis (Positive Upwards) <= %d ',Height);
        Title  = 'Z Position for Target Point';
        zObs  = str2num(cell2mat(inputdlg(Prompt,Title)));

        for i = 1:zObs
            Map(xObs,yObs,i) = -1;%Put on the closed list as well
        end

        plot(xObs+.5,yObs+.5,'ro');
    end
    
%-------------------------------------------------------------------------- 
    %Get Starting Point
    title('Starting Position');
    xlabel('Please Select the Vehicle initial position ');
    
    BREAK = 0;
    while (BREAK ~= 1) %Repeat until the Left button is not clicked
        [xCoord,yCoord,BREAK]=ginput(1);
        xStart = floor(xCoord);
        yStart =floor(yCoord);
    end

    %Get Height of Object
    Prompt = sprintf('Enter the Starting Position along the Z Axis (Positive Upwards) <= %d ',Height);
    Title  = 'Z Position for Target Point';
    zStart = str2num(cell2mat(inputdlg(Prompt,Title)));
        
    Map(xStart,yStart,zStart) = 1;
    plot(xStart+.5,yStart+.5,'bo');
    text(xStart+1,yStart+.5,'Start');
    
    Start = [xStart,yStart,zStart];
end
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------

