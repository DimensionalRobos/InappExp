var bubbles = new function () {
    // Initilizes the tooltips for elements with "tooltip" or "info" class present
    // Uses the title parameter of an element and adds it to the tooltip/info bubble
    // Usage:   <a href="#" class="tooltip bubbleBottom" title="<strong>Did you know?</strong><br />Clicking here will take you elsewhere. ">Link</a>

    // Or:      <a href="#" class="info bubbleBottom" title="<strong>Did you know?</strong><br />Clicking here will take you elsewhere. ">Link</a>

    // Or:      $(document).ready(function () {
    //              blackout.init($('.quickSearch'));
    //              $('.quickSearch').attr("title", "-60,83@@Click here for more info").addClass("info"); // this includes custom positioning eg. -60,83@@ (Top,Left)
    //              bubbles.init();
    //          });
    this.init = function () {
        $('.tooltip,.info').each(function () {
            var title = $(this).attr("title"); // get html to display
            $(this).attr("title", ""); // clear title to prevent it displaying on mouseover
            var emClass = "arrowRight"; // default class for arrow pointer
            var anchorWidth = $(this).width(); // get anchor tag pixel length
            var marginLeft = 25; // set default margin-left to bubbleRight values
            var marginTop = -35; // set default margin-top to bubbleRight values

            if (title.indexOf("@@") > -1) {
                var positions = title.substring(0, title.indexOf("@@"));
                positions = positions.split(",");
                marginTop = positions[0];
                marginLeft = positions[1];
                title = title.substr(title.indexOf("@@") + 2);
            }

            // if bubble position class present then set bubble up accordingly
            if ($(this).hasClass("bubbleLeft")) { emClass = "arrowLeft"; marginLeft = 0 - 300 - anchorWidth; } // if bubbleLeft class present then set bubble up accordingly
            if ($(this).hasClass("bubbleBottom")) { emClass = "arrowUp"; marginLeft = 0 - anchorWidth; marginTop = 35; }
            if ($(this).hasClass("bubbleTop")) { emClass = "arrowDown"; marginLeft = 0 - anchorWidth; }

            if ($(this).hasClass("info")) title = "<a href='#' onclick='bubbles.closeBubble($(this));return false' class='bubbleClose fontColor_A' title='Close'>X</a>" + title;

            // add html for bubble
            $(this).append("<em class='backColor_C shadowGradBottom bubble " + emClass + "'><div class='arrow borderColor_C'></div>" + title + "</em>");

            // check the new height of the bubble for the margin-top style for bubbleTop class if present
            if ($(this).hasClass("bubbleTop")) { marginTop = -20 - $(this).children(0).outerHeight(); }

            // position bubble
            $(this).find('em').css("margin-left", marginLeft + "px");
            $(this).find('em').css("margin-top", marginTop + "px");
        });
    },
    this.closeBubble = function (obj) {
        obj.parent().hide();
        blackout.exit();
    };
}