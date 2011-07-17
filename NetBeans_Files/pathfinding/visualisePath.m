%--------------------------------------------------------------------------
%READ GA CHROMOSOME OUTPUT AND VISUALIZE IT
%--------------------------------------------------------------------------

function Path = visualisePath(Ret)

    %Initialise Variables
    Generation = Ret.BestGen;
    Loc        = Ret.Start;
    Goal       = Ret.End;
    Diam       = Ret.Diam;
    LifeTime   = Ret.LifeTime;
    Dims       = Ret.Dims;
    MaxForce   = Ret.MaxForce;
    MaxSpeed   = Ret.MaxSpeed;
    Obs        = Ret.Obs;
    Acc        = [0,0,0];
    Vel        = [0,0,0];
    Path       = [];
    
    %Load Solution from Generation
%     BestChromGA = sprintf('Gen_%d.txt',Generation);
%     ChromData   = importdata(BestChromGA);
%     
%     %Reconstruct Path
%     for i=1:LifeTime
%         
%         %Chbeck if Target was Reached
%         if  (Loc(1)>Goal(1)) && (Loc(1)<(Goal(1)+Diam)) && (Loc(2)>Goal(2)) && (Loc(3)<(Goal(3)+Diam)) && (Loc(3)>Goal(3)) && (Loc(3)<(Goal(3)+Diam))
%             disp(sprintf('Target was reached in %d Iterations',i));
%             break;
%         else
%             %Select Gene based on Current Location within the Cells of the 
%             %Environment 
%             x = Loc(1)/Diam;
%             y = Loc(2)/Diam;
%             z = Loc(3)/Diam;
%             
%             %Limit Values so They do not Go over the Edges
%             x = constrain(x,0,Dims(1)/Diam);
%             y = constrain(y,0,Dims(2)/Diam);
%             z = constrain(z,0,Dims(3)/Diam);
%             
%             %Get the Acceleration Vector from Chromosome Information
%             xFactor = x;
%             yFactor = (y*Dims(1)/Diam);
%             zFactor = (z*(Dims(2)/Diam)*(Dims(1)/Diam));
%             temp2   = floor(xFactor+yFactor+zFactor)+1;
%             if temp2>size(ChromData,2)
%                 Genes = [0;0;0];
%             else
%                 Genes = ChromData(:,temp2);
%             end
%             
%             %Update Acceleration
%             Acc = Acc + Genes'.*MaxForce;
% 
%             %Update Velocity
%             Vel(1) = constrain(Vel(1)+Acc(1),-MaxSpeed,MaxSpeed);
%             Vel(2) = constrain(Vel(2)+Acc(2),-MaxSpeed,MaxSpeed);
%             Vel(3) = constrain(Vel(3)+Acc(3),-MaxSpeed,MaxSpeed);
% 
%             %Update Position Information for Vehicle
%             Loc = Loc + Vel;
%         
%             %Reset Acceleration Values
%             Acc = [0,0,0];
%             
%         end
%         %Save Path Information
%         Path = [Path;Loc];
%     end
    
    %Plot Solution
    figure();
%     plot3(Path(:,1),Path(:,2),Path(:,3),'b','linewidth',2);
    hold on;
    axis([0,Dims(1),0,Dims(2),0,Dims(3)]);
    plotcube([Diam,Diam,Diam],...
             [Ret.Start(1),Ret.Start(2),Ret.Start(3)],0.3,[0 1 0]);
    plotcube([Diam,Diam,Diam],...
             [Ret.End(1),Ret.End(2),Ret.End(3)],0.3,[1 0 0]);
         
    for j = 1:size(Ret.Obs,1)
        plotcube([Diam,Diam,Diam],...
                 [Ret.Obs(j,1),Ret.Obs(j,2),Ret.Obs(j,3)],1.0,[0 0 0]);
    end
    
    xlabel('Length [cm]');
    ylabel( 'Width [cm]');
    zlabel('Height [cm]');
    grid;
    hold off;

    %Save Plot
    print(gcf,'-depsc',sprintf('_Result_%d_%d',Generation,i));
end
        
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------