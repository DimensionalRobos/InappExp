var moreManager = new function () {
    this.hideSpeed = 150; //speed of the hide animation
    this.showSpeed = 300; //speed of the show animation

    // wire up all more class references
    this.init = function () {
        $('.more').each(function () {
            // get the object
            var more = $(this);

            // get charactor limit from attribute
            var charsLimit = more.attr("data-char");

            // get title text from attribute
            var title = more.attr("data-title") || 'Click for more';

            // get state from attribute
            var state = more.attr("data-state") || 'collapse';

            // store full html content for later
            var fullHtml = $.trim(more.html());

            // get charactor count for full html content
            var charCount = fullHtml.length;

            // if needed then add a "more" link if charsLimit set
            if (charCount > charsLimit) {

                // hide full content and add shortened version to DOM
                more.hide();

                if (charsLimit > 0) {
                    // store a shortened content to limit stated
                    var shortText = fullHtml.substring(0, charsLimit);

                    more.after('<p class="moreText">' + shortText + '&nbsp;&nbsp;<span class="more pointer fontColor_A" title="' + title + '" onclick="moreManager.hide($(this).parent());"> more...</span></p>');
                } else {
                    more.prev().addClass("pointer")
                               .attr("title", title)
                               .click(function () {
                                   moreManager.transition($(this).next());
                               });
                }
            } else {
                // if no charLimit set then put the more after the selected element
                if (typeof charLimit == 'undefined') {

                    var okToHide = false;
                    $(this).parent().children().each(function (index) {
                        $(this).parent().parent().css("padding-top", "10px").css("padding-bottom", "10px");
                        $(this).parent().css("padding-left", "10px").css("padding-right", "10px").css("padding-top", "5px").css("padding-bottom", "5px");
                        if (okToHide) $(this).hide(); // hide all elements after the element with the 'more' class
                        if ($(this).hasClass("more")) {
                            if ($(this).hasClass("open")) {
                                // add styling to section heading (no click)
                                $(this).parent().parent().find("h2")
                                            .addClass("addDownArrowBefore")
                                            .css("background-color", "#999")
                                            .attr("title", title)
                            } else {
                                okToHide = true;
                                // add click on the section heading
                                $(this).parent().parent().find("h2")
                                            .addClass("pointer addRightArrowBefore")
                                            .css("background-color", "#999")
                                            .attr("title", title)
                                            .click(function () {
                                                moreManager.showHide($(this).next());
                                            });

                                more.append('<span class="more pointer" style="display: inline-block; padding: 0px 7px; color: #ff8000; font-weight: bold;" title="' + title + '" onclick="moreManager.showHide($(this).parent().parent());"> [Read more]</span>');
                            }
                        }
                    });
                }
            }
            if (state == 'expand') { moreManager.transition(more); }
        });
    };

    // detect if to hide or show animation
    this.transition = function (obj) {
        var h = 0;
        // if full text is not visible then get height of full text
        if (!obj.is(':visible')) h = this.getHeight(obj);
        // animate object
        this.animate(obj, h);
    };

    // get height of object
    this.getHeight = function (obj) {
        // show object so we can get the height
        obj.show();
        // get the height of the object
        var h = obj.height();
        // quickly hide the object before anyone sees it
        obj.hide();
        // return the height of the object
        return h
    }

    // animate the content
    this.animate = function (obj, h) {
        if (h == 0) {
            //if height = 0 then hide the object
            obj.animate({ height: "hide", opacity: "hide" }, moreManager.hideSpeed);
        } else {
            // set height to 0px
            obj.hide().css("height", "0px");
            // show full content when shortened content has finsihed animating
            obj.animate({ height: h, opacity: "show" }, moreManager.showSpeed);
        }
    };

    // hide an object and fire transition
    this.hide = function (obj) {
        // hide the object
        obj.animate({ height: "hide", opacity: "hide" }, moreManager.hideSpeed, function () {
            // fire transition for prev() object
            moreManager.transition($(this).prev());
        });
    };

    // show all elements after more link
    this.showHide = function (obj) {
        var h2Element = obj.parent().find("h2");
        //alertify.log(h2Element.hasClass("down"));
        if (h2Element.hasClass("addDownArrowBefore")) {
            h2Element.removeClass("addDownArrowBefore");
            //obj.children().not('.more').animate({ height: "hide" }, 150);
            var okToHide = false;
            obj.children().each(function (index) {
                if (okToHide) $(this).animate({ height: "hide" }, 150);
                //alertify.log($(this).text(), '', 0);
                if ($(this).hasClass("more")) {
                    okToHide = true;
                    $(this).find("span.more").show();
                }
            });
        } else {
            h2Element.addClass("addDownArrowBefore");
            obj.children().animate({ height: "show" }, 150);
            obj.children().find("span.more").hide();
        }        
        
    };

};

$(function () {
    moreManager.init();
});
