function Val = constrain(Val,dMIN,dMAX)

        if Val < dMIN
            Val = dMIN;
        end
        
        if Val > dMAX
            Val = dMAX;
        end
end