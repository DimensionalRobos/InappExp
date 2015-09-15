var panelButtons = new function () {

    this.init = function () {
        this.setupPanelButtons();
        this.setupPanelAreas();
    };

    this.setupPanelButtons = function () {
        $(".panelButton").each(function () {
            $(this).wrap("<div class='panelButtonContainer'></div>");
            var w = $(this).attr("data-width");
            var m = $(this).attr("data-margins");
            var theme = "panelButtonTheme_" + $(this).attr("data-theme");

            // add arrows if required
            var arrowSize = $(this).attr("data-arrows-size");
            var arrowPos = $(this).attr("data-arrows-margins");
            var arrowClass = "upDown";
            if (arrowSize == "mini") arrowClass = "upDown miniUpDown";
            if ($(this).attr("data-arrows") == "true") $(this).prepend('<span class="' + arrowClass + '" style="float:right; margin:' + arrowPos + ';"></span>');

            // add default styles to panelButtonContainer
            if (theme == "panelButtonTheme_undefined") $(this).parent().addClass("shadowGradBottom backColor_E borderColor_J");

            // add styles to panelButtonContainer and events too
            $(this).parent()
                .css("width", w)
                .css("margin", m)
                .addClass(theme)
                .click(function () {
                    panelButtons.panelAreaAnimate($(this));
                })
                .find(".upDown").css("left", parseInt(w - 5) + "px")
            ;
        });
    };

    this.setupPanelAreas = function () {
        $(".panelButton").each(function () {
            var panel = $(this);

            // find hidden panel
            var panelAreaStr = typeof panel.attr("data-panel") != "undefined" ? panel.attr("data-panel") : "panelArea";
            var panelArea = $(this).parent().parent().parent().find("." + panelAreaStr);
            
            // make hover over and clickable events
            panelArea.hide().wrapInner('<div class="panelAreaInner"></div>');
            panelArea.find('.panelAreaInners').hover(
                function () { $(this).addClass("backColor_E"); },
                function () { $(this).removeClass("backColor_E"); })
                .click(function () {
                    //get anchor tag link for click
                    var url = $(this).find("a").attr("href");
                    if (typeof (url) != "undefined") location.href = url;
                })
            ;
        });
    };

    this.panelAreaAnimate = function (obj) {

        var panel = obj.children(0);
        var panelAreaStr = typeof panel.attr("data-panel") != "undefined" ? panel.attr("data-panel") : "panelArea";
        var panelArea = obj.parent().parent().find("." + panelAreaStr);
        
        if (panelArea.is(":visible")) {
            // if visible then hide
            panelArea.animate({ "height": "hide", "opacity": "hide" }, 300);
            obj.parent().find(".upDown").removeClass("showUp")
        } else {
            // if not visible then get height, hide and animate
            panelArea.show();
            var h = panelArea.outerHeight();
            
            panelArea.css("height", "0px").hide();

            panelArea.animate({ "height": h, "opacity": "show" }, 300, function () {
                $(this).css("height", "auto");
            });

            // toggle arrows
            if (panelAreaStr == "panelArea") {
                obj.parent().find(".upDown").addClass("showUp");
            } else {
                obj.find(".upDown").addClass("showUp");
            }
        }
    };

}

$(function () {
    panelButtons.init();
});
