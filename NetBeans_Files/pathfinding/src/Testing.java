//
//
//public class Testing {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) 
//    {
//        CObstacle Loc = new CObstacle(0,0,0,50,50,50);
//        CObstacle Tar = new CObstacle(1000,600,500,50,50,50);
//        
//        int Finish = 1;
//        int RecDst = 10000;
//        int Diam = 50;
//        int Length = 1000;
//        int Width = 600;
//        int Height = 500;
//        int Grid = 50;
//        int fla = (int) ((Length * Width * Height) / Math.pow(Diam, 3));
//        CDNA DNA= new CDNA(fla);
//        
////        CObstacle Obs = new CObstacle(10,10,10,50,50,50);
//        CObstacle[] O = {new CObstacle(10,10,10,50,50,50)};
//        CPopulation pop = new CPopulation(1,1,0,0.05f,Length,Width,Height,Diam,Loc,Tar) ;
//        pop.getCurrGeneration();
//        pop.propagateGen(O, Grid);
//    }
//}
