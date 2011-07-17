function viewFitness()
    Fitnesses = importdata('Fitnesses.txt');
    
    figure()
    scatter(Fitnesses(:,1),Fitnesses(:,3),'b');
end