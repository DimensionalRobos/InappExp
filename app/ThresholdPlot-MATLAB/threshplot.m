function threshplot(X,Y,borderline)
    figure;
    plot(Y,X);
    hold on;
    stem(Y,X);
    border=1:length(Y);
    for i=1:length(Y);
        border(i)=borderline
    end
    plot(Y,border,'r');
end