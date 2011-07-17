% %Test Function for Path Planning Algorithms
% 
% %Setup
% clc
% close all;
% clear all;
% clear classes;
% 
% %Volume to be Considered
% Dimensions = a.Dims;
% 
% %Obstacles [x,y,Height]
% Obs = a.Obs;
% 
% %Start & Goal Positions
% Start = a.Start;
% Goal  = a.End

Diam       = 1;
Dimensions = [10,10,10];
Start      = [1,1,1];
Goal       = [6,6,6];

Obs = [2,1,1;
       2,2,1;
       1,2,1;
       2,1,2;
       2,2,2;
       1,2,2;
       5,6,1;
       5,5,1;
       5,4,1;
       5,3,1;
       5,2,1;
       5,1,1;
       5,6,2;
       5,5,2;
       5,4,2;
       5,3,2;
       5,2,2;
       5,1,2;
       5,6,3;
       5,5,3;
       5,4,3;
       5,3,3;
       5,2,3;
       5,1,3;
       5,6,4;
       5,5,4;
       5,4,4;
       5,3,4;
       5,2,4;
       5,1,4;
       5,6,5;
       5,5,5;
       5,4,5;
       5,3,5;
       5,2,5;
       5,1,5;
       5,6,6;
       5,5,6;
       5,4,6;
       5,3,6;
       5,2,6;
       5,1,6];
% AStar = AStar(Dimensions,1,Start,Goal,Obs);
Path = AStar(Start,Goal,Dimensions,Obs,0,Diam);