figure();
a = importdata('_Finished_1.txt')
Diam = 50;    
plot3(a(1,:),a(2,:),a(3,:),'r','linewidth',2);
    hold on;
    plotcube([Diam,Diam,Diam],...
             [0,0,0],0.3,[0 1 0]);
    plotcube([Diam,Diam,Diam],...
             [500,300,250],0.3,[1 0 0]);
    legend('Evolved Path','Starting Point','Goal Point');
    xlabel('Length [cm]');
    ylabel('Width [cm]');
    zlabel('Height [cm]');
    axis([0 1000 0 600 0 500])
    grid;
    hold off;