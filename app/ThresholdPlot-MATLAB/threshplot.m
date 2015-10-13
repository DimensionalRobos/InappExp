function threshplot(X,Y,borderline)
    plot(Y,X);
    hold on;
    border=1:length(Y);
    for i=1:length(Y);
        border(i)=borderline
    end
    plot(Y,border,'r');
end