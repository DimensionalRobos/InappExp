// anchorScroll v1.0
// by Pete Smith FindAUniversity Limited Copyright 2014  
// smooth scroll for anchors/ids
// requires jQuery

var anchorScroll = new function () {
    var _this = this;
    _this.elementOffset = 50; // in pixels
    _this.scrollDuration = 1000; // in milliseconds
    _this.hash = window.location.hash;

    _this.init = function () {
        _this.scrollDownPage();
        _this.makeAnchorsOnPageScrollToo();
    };
    _this.scrollDownPage = function () {
        if (_this.hash) {
            if ($(_this.hash).length) $('html, body').animate({ scrollTop: 0 }, 1).delay(500).animate({ scrollTop: $(_this.hash).offset().top - _this.elementOffset }, _this.scrollDuration);
        }
    };
    _this.makeAnchorsOnPageScrollToo = function () {
        $('a[href^="#"]').click(function (e) {
            e.preventDefault();
            var o = $(this.hash).offset();
            if (o) $('body, html').animate({ scrollTop: o.top }, _this.scrollDuration);
        });
    };
}