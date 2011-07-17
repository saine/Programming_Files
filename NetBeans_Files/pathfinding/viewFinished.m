    %Plot Solution
    Path = importdata('_Finished_770.txt');
    figure();
    plot3(Path(:,1),Path(:,2),Path(:,3),'b','linewidth',2);
    hold on;
    axis([0,Dims(1),0,Dims(2),0,Dims(3)]);
    plotcube([Diam,Diam,Diam],...
             [Ret.Start(1),Ret.Start(2),Ret.Start(3)],0.3,[0 1 0]);
    plotcube([Diam,Diam,Diam],...
             [Ret.End(1),Ret.End(2),Ret.End(3)],0.3,[1 0 0]);
         
    for i = 1:size(Ret.Obs,1)
        plotcube([Diam,Diam,Diam],...
                 [Ret.Obs(i,1),Ret.Obs(i,2),Ret.Obs(i,3)],0.5,[0 0 0]);
    end
    
    xlabel('Length [cm]');
    ylabel( 'Width [cm]');
    zlabel('Height [cm]');
    grid;
    hold off;

    %Save Plot
    print(gcf,'-depsc',sprintf('_Result_%d',Generation));