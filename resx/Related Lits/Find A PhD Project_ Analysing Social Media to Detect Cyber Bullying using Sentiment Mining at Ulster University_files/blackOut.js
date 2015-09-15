var blackout = new function () {
    // creates a blackout opacity screen to highlight a selector/element
    this.init = function (obj) {
        obj.addClass("blackoutCanvas");
        if ($.isEmptyObject('.blackout') || !$('.blackout').length) $('body').append("<div class='blackout'></div>");
        var t = setTimeout("$('.blackout').fadeTo('slow',0.4);", 400);
        $('.blackout').click(function () {
            blackout.exit();
        });
    };
    this.exit = function () {
        $('.blackout').fadeTo('slow', 0, function () { $(this).remove(); });
        $('.blackoutCanvas').removeClass("blackoutCanvas");
        $('.bubble').hide();
    };
}