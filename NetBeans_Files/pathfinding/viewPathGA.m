%--------------------------------------------------------------------------
%READ GA CHROMOSOME OUTPUT AND VISUALIZE IT
%--------------------------------------------------------------------------

function [Ret,Path] = viewPathGA(Generation)

    format long eng;
    clc

    %Load Summary Data
    Summary = importdata('_Summary.txt');
    
    Ret.Obs = [];
    Counter = 1;
    if size(Summary.data,1) > 20
        for i = 20:3:size(Summary.data,1)-1
            Ret.Obs(Counter,:) = Summary.data(i:(i+2))'; 
            Counter = Counter+1;
        end
    end
    
    %Display some Information
    disp(sprintf('Total Number of Generations: %d',Summary.data(1)));
    disp(sprintf('Number of Best Generation:   %d',Summary.data(2)));
    disp(sprintf('Maximum Fitness Value:       %d',Summary.data(3)));
    disp(sprintf('Start Point:                 [%d %d %d]',...
                  Summary.data(4),Summary.data(5),Summary.data(6)));
    disp(sprintf('End Point:                   [%d %d %d]',...
                  Summary.data(7),Summary.data(8),Summary.data(9)));
    disp(sprintf('Population Size:             %d',Summary.data(10)));
    disp(sprintf('Environment Length:          %d',Summary.data(11)));
    disp(sprintf('Environment Width:           %d',Summary.data(12)));
    disp(sprintf('Environment Height:          %d',Summary.data(13)));
    disp(sprintf('Gridscale:                   %d',Summary.data(14)));
    disp(sprintf('Maximum Speed:               %d',Summary.data(15)));
    disp(sprintf('Maximum Acceleration:        %d',Summary.data(16)));
    disp(sprintf('Mutation Rate:               %d',Summary.data(17)));
    disp(sprintf('Life Time:                   %d',Summary.data(18)));
    disp(sprintf('Zone Diameter:               %d',Summary.data(19)));
    
    %Return Useful Values
    Ret.BestGen  = Summary.data(2);
    Ret.Start    = [Summary.data(4),Summary.data(5),Summary.data(6)];
    Ret.End      = [Summary.data(7),Summary.data(8),Summary.data(9)];
    Ret.LifeTime = Summary.data(18);
    Ret.Diam     = Summary.data(19);
    Ret.Dims     = [Summary.data(11),Summary.data(12),Summary.data(13)];
    Ret.MaxSpeed = Summary.data(15);
    Ret.MaxForce = Summary.data(16);
    
    %Plot Specific Generation
    if Generation ~= -1
        Ret.BestGen = Generation;
    end
    
    Path = [];
    %Show Best Solution
    Path = visualisePath(Ret);
end
        
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------