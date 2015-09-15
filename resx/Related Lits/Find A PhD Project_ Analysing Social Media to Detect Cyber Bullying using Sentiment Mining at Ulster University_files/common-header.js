/*
* Page helper functions
* Copyright 2010 The Science Registry Limited - by Yan Rupik and Pete Smith
*/

var sciReg = (sciReg != undefined) ? sciReg : new Object();

sciReg.pageHelperClass = function() {

    var inited = false;

    if (sciReg.pageHelperSingleton == null)
        sciReg.pageHelperSingleton = {

            init: function() {
                if (inited) return;
                inited = true;

                // Sort out Shadowbox
                if ((navigator.userAgent.match(/iPad/i)) || (navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/android/i))) {
                    $("a[rel ^= 'shadowbox']").attr("rel", "");
                } else {
                    Shadowbox.init({ handleOversize: "drag", modal: true, overlayOpacity: 0.75, onOpen: this.scrollBackground, onClose: this.scrollBackground, troubleElements: ["select", "object", "embed", "canvas", "input", "textarea"] });
                }

                // Make everything the same length
                sciReg.pageHelper.dotChecker();
            },

            scrollBackground: function() {
                // Disables background screen scrolling while viewing shadowbox popups

                // Copyright 2010 Peter Smith - The Science Registry
                // Usage: Allowed if copyright statement remains

                // add the following to the Shadowbox.init ({})
                // onOpen: scrollBackground, onClose: scrollBackground

                // Get a reference to the HTML element
                var objHTML = document.getElementsByTagName("html").item(0);

                // Hide/show scrollbar
                if (objHTML.style.overflow == "hidden") {
                    objHTML.style.overflow = "auto";
                    objHTML.style.width = "auto";
                    // PS. i removed this as it was causing problems see link: http://www.findamasters.com/search/masters-degree.aspx?OD=1&course=6442
                    //if (document.URL.indexOf("OD=1", 0) > -1) reload() // this is for old showopenday.asp forwards for opendays, it reloads the search after close
                } else {
                    objHTML.style.overflow = "hidden";
                    // Hiding the vertical scrollbar and having the webpage centered makes the content jump across by the amount of the scrollbars width
                    // this adjust that if html width is not set i.e. it only fires once.
                    if (objHTML.style.width == "" || objHTML.style.width == "auto") objHTML.style.width = "" + parseInt(objHTML.offsetWidth - 17) + "px";
                }
            },

            dottedLineExtender: function() {
                //document.getElementById('phMain_RightPanel').style.height = 0 + "px";
                //var clientHeight = document.body.clientHeight - 300;
                //document.getElementById('phMain_RightPanel').style.height = clientHeight + "px";
                //alert(document.getElementById('phMain_RightPanel').offsetHeight);
                this.dotChecker();
            },

            dotChecker: function() {
                // -- Chrome Bug Fix : if chrome then alter dotted line style to dashed because of chrome bug with 4096px height --
                var divMainContent = document.getElementById("divMainContent") || document.getElementById("main");
                //alert(divMainContent.offsetHeight);
                if (divMainContent != null && divMainContent.offsetHeight > 4095) {
                    var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
                    if (is_chrome && $(divMainContent).css("border-right").indexOf("0px") == -1) divMainContent.setAttribute("style", "border-right:dashed 1px gray;")
                }

                var divs = new Array(), entry;
                var addFunc = function(id, adjust) {
                    var div = $("#" + id);
                    if (div.length == 0) return;
                    divs[divs.length] = entry = new Object();
                    var isHidden = div.is(":hidden");
                    div.show();
                    entry.element = div;
                    entry.height = div.height();
                    entry.outerHeight = div.outerHeight();
                    entry.offset = div.offset().top;
                    entry.adjust = adjust;
                    if (isHidden) div.hide();
                }

                addFunc("vMenu", 0);
                addFunc("divMainContent", 0);
                addFunc("main", 0);
                addFunc("phMain_RightPanel", 15);

                var maxHi = 0;
                for (var i = 0; i < divs.length; i++) {
                    entry = divs[i];
                    var newHi = entry.outerHeight + entry.offset;
                    if (newHi > maxHi) maxHi = newHi;
                }
                for (var i = 1; i < divs.length; i++) {
                    entry = divs[i];
                    var newHi = maxHi - entry.offset - (entry.outerHeight - entry.height) - entry.adjust;
                    if (newHi > entry.element.height())
                        entry.element.css("min-height", newHi + "px");
                }
            },

            areYouSure: function(Question) {
                if (confirm(Question)) { return true } else { return false }
            },

            areYouSure: function(Question) {
                if (confirm(Question)) { return true } else { return false }
            },

            // for IE6, hides the dropdown lists because they always appear above the 'please wait' div layer //
            hideDDLs: function(objDDL) {
                var ddl = document.getElementById(objDDL);
                ddl.style.visibility = 'hidden';
            },

            onStopButtonOnBrowser: function() {
                //--IE only, nothing for mozilla
                top.location.reload();
            },

            createCookie: function(name, value, days) {
                if (days) {
                    var date = new Date();
                    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                    var expires = "; expires=" + date.toGMTString();
                }
                else var expires = "";
                document.cookie = name + "=" + value + expires + "; path=/";
            },

            createCookieList: function (name, value, days, delimiter) {
                if (delimiter == undefined || delimiter == null) delimiter = ",";

                var newData = [];
                var isAdding = true;

                var items = this.readCookieList(name);
                if (items && items.length > 0) {
                    var ignore = {};
                    jQuery.each(items, function (idx, item) {
                        if (item == value)
                            isAdding = false;
                        if (!ignore[item]) {
                            newData.push(item);
                            ignore[item] = true;
                        }
                    });
                }

                if (isAdding)
                    newData.push(value);

                this.createCookie(name, newData.join(delimiter), days);

                return isAdding;
            },

            readCookie: function(name) {
                var nameEQ = name + "=";
                var ca = document.cookie.split(';');

                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') c = c.substring(1, c.length);
                    if (c.indexOf(nameEQ) == 0) { return c.substring(nameEQ.length, c.length); }
                }
                return null;
            },

            readCookieList: function (name, delimiter) {
                if (delimiter == undefined || delimiter == null) delimiter = ",";
                var data = this.readCookie(name);
                if (data) {
                    var items = data.split(delimiter);
                    return items;
                }
                return [];
            },

            removeFromCookieList: function (name, value, days, delimiter) {
                if (delimiter == undefined || delimiter == null) delimiter = ",";

                var newData = [];
                var found = false;

                var items = this.readCookieList(name);
                if (items && items.length > 0) {
                    jQuery.each(items, function (idx, item) {
                        if (item == value)
                            found = true;
                        else
                            newData.push(item);
                    });
                }

                if (newData.length > 0)
                    this.createCookie(name, newData.join(delimiter), days);
                else
                    this.createCookie(name, null, days);

                return found;
            },

            queryString: function(key) {
                var qs = window.location.search.substring(1).split("&");
                for (var i = 0; i < qs.length; i++) {
                    var e = gy[i].split("=");
                    if (e[0] == key) return e[1];
                }
            },

            openShadowbox: function(url, title, width, height) {
                Shadowbox.open({ content: url, player: "iframe", title: title, height: height, width: width });
            },

            setDynamicWidths: function (container) {
                switch(typeof container) {
                    case "string": if (container.substring(0, 1) != "#") container = "#" + container; break;
                    case "object": break;
                    default: throw "Custom SciReg Error: Unknown container argument for dynamic resizing";
                }
                var area = $(container);
                if (area.length == 0) return;

                // All children we need to be aware of.
                var allWidth = area.find("*").filter(function () { var item = $(this); return (item.is("img") || /width:\s*\d/i.test(item.attr("style")) || item.attr("width") != undefined ) ? true : false; });
                var allImg = allWidth.filter("img");
                var allNotImg = allWidth.filter(function () { return !$(this).is("img"); });

                // Set original widths based on largest screen size. However, we can't do this for images because they might not have loaded yet.
                area.css("width", "1024px");
                allNotImg.each(function () {
                    var item = $(this);
                    if (!item.data("originalWidth") && item.css("width")) item.data("originalWidth", item.css("width"));
                });
                area.css("width", "auto");

                // Function for resizing.
                var resizeMe = function () {
                    var item = $(this);
                    if (area.width() == 0) return;
                    if (item.data("originalWidth")) { item.css("width", item.data("originalWidth")); }
                    if (item.outerWidth() > area.width()) {
                        item.css("width", (area.width() - (item.outerWidth() - item.width())) + "px");
                    }
                }

                // Sort out everything that is not an image.
                allNotImg.each(resizeMe);

                // Sort out images.
                allImg.load(function () {
                    var item = $(this);
                    area.css("width", "1024px");
                    item.data("originalWidth", item.css("width"));
                    area.css("width", "auto");
                    resizeMe.call(item);
                }).each(function () { var item = $(this); item.attr("src", item.attr("src")) });

                // finally, set an on resize event.
                window.onresize = function () { allWidth.each(resizeMe); }
            }

        }; //End searchPageClass methods

    return sciReg.pageHelperSingleton;
}       //End searchPageClass

sciReg.pageHelper = new sciReg.pageHelperClass();
$(document).ready(function() {
    var wait = setTimeout("sciReg.pageHelper.init()", 50);
});

function scrollBackground() { sciReg.pageHelper.scrollBackground(); }
function dottedLineExtender() { sciReg.pageHelper.dotChecker(); }
function dotChecker() { sciReg.pageHelper.dotChecker(); }