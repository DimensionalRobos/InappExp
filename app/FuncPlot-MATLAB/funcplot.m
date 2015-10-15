function funcplot(X,Y)
    figure;
    plot(Y,X);
    hold on;
    stem(Y,X);
    border=1:length(Y);
    for i=1:length(Y);
        border(i)=max(X);
    end
    plot(Y,border,'g');
    for i=1:length(Y);
        border(i)=min(X);
    end
    plot(Y,border,'r');
    for i=1:length(Y);
        border(i)=mean(X);
    end
    plot(Y,border,'k');
    hold off;
end