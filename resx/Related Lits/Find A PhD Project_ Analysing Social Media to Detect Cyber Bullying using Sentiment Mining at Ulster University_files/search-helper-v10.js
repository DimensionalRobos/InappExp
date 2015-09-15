/*
* Search page helper functions
* Copyright 2010 The Science Registry Limited - by Yan Rupik and Pete Smith
*/

var sciReg = (sciReg != undefined) ? sciReg : new Object();

sciReg.searchHelperClass = function(options) {

    var preloadedImg = null;
    var keywordState = 0;

    if (options == undefined) options = new Object();
    if (options.page == undefined) page = "search";
    if (options.pageHelper == undefined) options.pageHelper = new sciReg.pageHelperClass();
    if (options.bucketIconImageUrl == undefined) options.bucketIconImageUrl = "/images/Icons/coursebucket.gif";
    if (options.saveIconImageUrl == undefined) options.saveIconImageUrl = "/images/saveCourseIcon.gif";
    if (options.bucketName == undefined) options.bucketName = "Bucket";

    if (options.domain == undefined) {
        var domain = document.domain.toLowerCase();
        options.domain = document.domain;
    }

    // Simple page methods
    var simplePage = function(parent, options) {
        return {
            init: function() {
                parent.courseBucketCounter();
            }
        }
    }

    // Search page methods
    var searchPage = function(parent, options) {

        return {

            init: function() {
                //preload images when that page loads
                preloadedImg = new Image(32, 32); preloadedImg.src = '../images/counter/runningCounter.gif';
                var preloadedImgLoading = new Image(32, 32); preloadedImgLoading.src = '../images/loading1.gif';
                var preloadedImgBarCounter = new Image(32, 32); preloadedImgBarCounter.src = '../images/counter/counterBackground.gif';
                var preloadedImgBarRight = new Image(32, 32); preloadedImgBarRight.src = '../images/counter/counterBackground3.gif';
                var preloadedImgBarBG = new Image(32, 32); preloadedImgBarBG.src = '../images/counter/counterBackground2.gif';
                parent.clearKeywords(document.getElementById("txtKeywords"), "imgClear");
                $("div#divResults").add("div#divStatsFooter").animate({ height: "show", opacity: "show" }, 800);
                $("div#loadbox").animate({ opacity: "hide" }, 400);
                $("#FilterPanel").find("input, select").each(function (idx, item) {
                    item.defaultValue = $(item).val();
                });
                this.checkForBucket();
            },

            checkForBucket: function() {

                var ca;
                var strNumber = '';
                if (parent.pageHelper.readCookie('Bucket')) {
                    ca = parent.pageHelper.readCookie('Bucket').split(',');
                }
                else {
                    ca = new Array();
                }

                for (var ii = 0; ii < 100; ii++) {
                    if (ii < 10) { strNumber = '0' + ii } else { strNumber = '' + ii }
                    strObjCID = 'dlCourseSearchResults_ctl' + strNumber + '_lblCourseID';
                    strObjIMG = 'dlCourseSearchResults_ctl' + strNumber + '_imgCourseIcon';
                    if (document.getElementById(strObjCID)) {
                        $("#" + strObjIMG).attr("src", "/images/listFlag0.gif").addClass("bucketCursorPlus").removeClass("bucketCursorMinus");
                        for (var i = 0; i < ca.length; i++) {
                            var c = ca[i];
                            if (c == document.getElementById(strObjCID).innerHTML) {
                                $("#" + strObjIMG).attr("src", options.bucketIconImageUrl).removeClass("bucketCursorPlus").addClass("bucketCursorMinus");
                            }
                        }
                    }
                }

                $("input.CourseId[type='hidden']").each(function(index) {
                    var item = $(this);
                    if ($.inArray(item.val(), ca) == -1) {
                        item.closest("td").find("img.flag").attr("src", "/images/listFlag0.gif").addClass("bucketCursorPlus").removeClass("bucketCursorMinus");
                        return true;
                    }
                    item.closest("td").find("img.flag").attr("src", options.bucketIconImageUrl).removeClass("bucketCursorPlus").addClass("bucketCursorMinus");
                });

                //update bucket counter
                parent.courseBucketCounter();
            },

            addRemoveFromBucket: function(sender, days, imgBinId) {
                if (typeof sender == "object") {
                    sender = $(sender);
                    var idTag = $(sender).closest("td,.resultsRow").find('.CourseId,.CourseID');
                    value = idTag.is("input") ? idTag.val() : idTag.text();
                } else {
                    var strTagID = sender.substring(0, 27) + '_lblCourseID';
                    value = document.getElementById(strTagID).innerHTML;
                    sender = $("#" + sender);
                }

                var i = 0;
                var ii = 0;
                var cb = new Array();

                if (parent.pageHelper.readCookie('Bucket')) {
                    var ca = parent.pageHelper.readCookie('Bucket').split(',');

                    for (i = 0; i < ca.length; i++) {
                        //rebuild ca array in cb without duplicate matches i.e. remove from bucket
                        if (value != ca[i]) { cb[ii] = ca[i]; ii++; }
                    }
                }

                if (cb.length < 50) {

                    if (i > 0) {
                        if (i == ii) {
                            parent.pageHelper.createCookie('Bucket', value + ',' + cb.join(), days);
                            sender.attr("src", options.bucketIconImageUrl).removeClass("bucketCursorPlus").addClass("bucketCursorMinus").attr("onmouseover", "");
                        }
                        else {
                            parent.pageHelper.createCookie('Bucket', cb.join(), days);
                            sender.attr("src", options.saveIconImageUrl).removeClass("bucketCursorMinus").addClass("bucketCursorPlus");
                        }
                    } else {
                        parent.pageHelper.createCookie('Bucket', value, days);
                        sender.attr("src", options.bucketIconImageUrl).removeClass("bucketCursorPlus").addClass("bucketCursorMinus").attr("onmouseover", "");
                    }
                } else {
                    alert("Your course bucket is limited to 50 courses at a time");
                }
                //update bucket counter
                parent.courseBucketCounter();
            },

            form_submit: function(sender, urlKey, pgKey) {
                if (!sender.jquery) {
                    if (typeof sender == "object") sender = $(sender);
                    else if (typeof sender == "string") sender = $((sender.indexOf("#") == -1 ? "#" : "") + sender);
                    else throw "type undefined or not recognised"
                }

                var updated = sender;

                var strCounterText = $("#counterText");
                if( strCounterText.children("h2").length > 0 )
                    strCounterText.html("<h2 style='color:white;'>&nbsp; Please wait...</h2>");
                else
                    strCounterText.html("Please wait...");

                var urlString = sender.val();
                if (urlString.substring(0, 7) != "http://" && urlString.substring(0, 8) != "https://") urlString = document.URL;

                var stripPage = false;

                if (typeof urlKey == "string") {
                    var re1 = (urlString.match(new RegExp("\\?" + urlKey + "=[^&]*", "i"))) ? new RegExp(urlKey + "=[^&]*&?", "i") : new RegExp("&" + urlKey + "=[^&]*", "i");
                    urlString = urlString.replace(re1, "");
                    if (sender.val()) {
                        urlString += (urlString.indexOf("?") == -1 ? "?" : "&") + urlKey + "=" + encodeURI(jQuery.trim(sender.val()).replace(/\s/g, "+")).replace(/\&/g, "%26");
                        stripPage = true;
                    }
                } else if (typeof urlKey == "object") {
                    $.each(urlKey, function(key, value) {
                        if (value && value.indexOf("#") == -1 && value.indexOf(".") == -1) value = "#" + value;
                        var newValue = value ? encodeURI(jQuery.trim($(value).val()).replace(/\s/g, "+")).replace(/\&/g, "%26") : null;
                        var re1 = (urlString.match(new RegExp("\\?" + key + "=[^&]*", "i"))) ? new RegExp(key + "=([^&]*)&?", "i") : new RegExp("&" + key + "=([^&]*)", "i");
                        var match = urlString.match(re1);
                        var oldValue = match ? match[1] : null;
                        if (match && (!newValue || oldValue != newValue)) urlString = urlString.replace(re1, "");
                        if (newValue && oldValue != newValue) {
                            urlString += (urlString.indexOf("?") == -1 ? "?" : "&") + key + "=" + newValue;
                            updated = updated.add(value);
                            stripPage = true;
                        }
                    });
                }

                if (pgKey && stripPage) {
                    var re2 = (urlString.match(new RegExp("\\?" + pgKey + "=[^&]*", "i"))) ? new RegExp(pgKey + "=[^&]*&?", "i") : new RegExp("&" + pgKey + "=[^&]*", "i");
                    urlString = urlString.replace(re2, "");
                }

                // Reset
                var defaultValue = sender[0].defaultValue ? sender[0].defaultValue : sender.children('option[defaultSelected]').attr("value") ? sender.children('option[defaultSelected]').attr("value") : sender.children().first().attr("value");
                sender.val(defaultValue);
                setTimeout(function () { sender.val(defaultValue); }, 0);

                if (navigator.userAgent.indexOf("Firefox") != -1) {
                    document.getElementById("form2").method = "post";
                    document.getElementById("form2").action = urlString;
                    //document.getElementById('form1').reset();
                    document.getElementById("form2").submit();
                } else {
                    //document.getElementById('form1').reset();
                    top.location.href = urlString;
                }

                searchHelper.pleaseWaitDiv(updated);

                var load = document.getElementById('loadbox');
                load.style.display = 'block';
                load.src = searchHelper.preloadedImg;

                //IE only - if browser stop button is fired the reload browser
                document.onstop = pageHelper.onStopButtonOnBrowser;
            },

            getCourseCountTitle: function(strLocation) {
                var intStart = strLocation.indexOf("$") + 1;
                return strLocation.substring(intStart, strLocation.length);
            },

            pleaseWaitDiv: function(sender, id) {
                var d = (id != undefined) ? $("div#" + id) : $("div#divPleaseWait");
                sender.each(function() {
                    var parent = ($(this).attr("type") == "text") ? $(this).parent().wrap("<div/>") : $(this).wrap("<div/>");
                    d.clone().appendTo(d.parent()).offset(parent.offset()).outerWidth(parent.outerWidth()).outerHeight(parent.outerHeight()).show().offset(parent.offset());
                });
            },

            clearKeywords: function(obj, clearId) {
                if (obj == null) return;
                if ((obj.value.length > 0) && (keywordState == 0)) {
                    keywordState = 1;
                    $(document.getElementById(clearId)).animate({ opacity: "toggle" }, 500);
                } else if (obj.value.length == 0) {
                    keywordState = 0;
                    document.getElementById(clearId).style.display = 'none';
                }
            },

            showAdverts: function(id, onComplete) {
                if (id == undefined) id = "phMain_RightPanel";
                var pageHelper = this.pageHelper;
                $(document.getElementById(id)).animate({ height: "show", opacity: "show" }, 1000, function() {
                    parent.pageHelper.dottedLineExtender();
                    if (onComplete != undefined) onComplete()
                });
            },

            createBackCookie: function(days) {
                parent.pageHelper.createCookie('LastSearch', document.URL, days);
            }
        }
    }

    // Details page methods
    var detailsPage = function(parent, options) {

        return {

            init: function() {
                this.checkForBucket();
            },

            checkForBucket: function() {
                var value = document.getElementById("lblCourseID").innerHTML;

                if (parent.pageHelper.readCookie('Emailed')) {
                    //this changes the email icon to a sent email icon on items within the "Emailed" cookie
                    var emailedIDs = parent.pageHelper.readCookie('Emailed').split(',');
                    for (var iii = 0; iii < emailedIDs.length; iii++) {
                        if (value == emailedIDs[iii]) {
                            document.getElementById("imgEmailEnq1").src = "/images/icons/emailSent.gif";
                            document.getElementById("imgEmailEnq1").title = "Email Already Sent";
                            document.getElementById("imgEmailEnq2").title = "Email Already Sent";
                            document.getElementById("imgEmailEnq3").src = "/images/icons/emailSent.gif";
                            document.getElementById("imgEmailEnq3").title = "Email Already Sent";
                            break;
                        }
                    }
                }

                if (parent.pageHelper.readCookie('Bucket')) {
                    var c = parent.pageHelper.readCookie('Bucket').split(',');
                    for (var i = 0; i < c.length; i++) {
                        if (c[i] == value) {
                            document.getElementById("ancAddRemoveCourse").innerHTML = "Remove From " + options.bucketName;
                            $("#divFlagAddRemove").text("remove");
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                            }
                        }
                    }
                }

                //update bucket counter
                parent.courseBucketCounter();
            },

            addRemoveFromBucket: function(sender, days) {
                var i = 0;
                var ii = 0;
                var value = document.getElementById("lblCourseID").innerHTML;
                var cb = new Array();

                if (parent.pageHelper.readCookie('Bucket')) {
                    var ca = parent.pageHelper.readCookie('Bucket').split(',');
                    for (i = 0; i < ca.length; i++) {
                        //rebuild ca array in cb without duplicate matches i.e. remove from bucket
                        if (value != ca[i]) { cb[ii] = ca[i]; ii++; }
                    }
                }

                if (cb.length < 50) {

                    if (i > 0) {
                        if (i == ii) {
                            parent.pageHelper.createCookie('Bucket', value + ',' + cb.join(), days);
                            document.getElementById("ancAddRemoveCourse").innerHTML = "Remove From " + options.bucketName;
                            $("#divFlagAddRemove").text("remove");
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                            }
                        }
                        else {
                            parent.pageHelper.createCookie('Bucket', cb.join(), days);
                            document.getElementById("ancAddRemoveCourse").innerHTML = "Add To " + options.bucketName;
                            $("#divFlagAddRemove").text("add");
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag0.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag0.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Add";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentre.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeft.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRight.gif";
                            }
                        }
                    } else {
                        parent.pageHelper.createCookie('Bucket', value, days);
                        document.getElementById("ancAddRemoveCourse").innerHTML = "Remove From " + options.bucketName;
                        $("#divFlagAddRemove").text("remove");
                        var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                        var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                        var spnShortlistButton = document.getElementById("spnShortlistButton");
                        if (spnShortlistButton != undefined && spnShortlistButton != null) {
                            document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                            $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                            document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                            document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                        }
                    }
                } else {
                    alert("Your course bucket is limited to 50 courses at a time");
                }
                //update bucket counter
                parent.courseBucketCounter();
            }
        }
    }

    // Shortlist page methods
    var shortlistPage = function(parent, options) {

        if (options.comparePageUrl == undefined) options.comparePageUrl = "/search/CourseCompare.aspx";
        if (options.compareQsKey == undefined) options.compareQsKey = "FACIDs";

        var refreshBucketItems = function(sender) {
            if (parent.pageHelper.readCookie('Bucket')) {
                var ca = parent.pageHelper.readCookie('Bucket').split(',');

                var itemId = "txtCID";
                switch (options.domain.toLowerCase()) {
                    case "www.findamasters.com": itemId = "txtCID"; break;
                    case "www.findaphd.com": itemId = "txtPhDID"; break;
                }

                for (ii = 0; ii < 99; ii++) {
                    var zero = "";
                    if (ii < 10) zero = "0";

                    if (document.getElementById("dlCourseBucket_ctl" + zero + ii + "_" + itemId)) {
                        var intCounter = 0;
                        for (i = 0; i < ca.length; i++) {

                            if (document.getElementById("dlCourseBucket_ctl" + zero + ii + "_" + itemId).value == ca[i]) {
                                var obj = document.getElementById("dlCourseBucket_ctl" + zero + ii + "_divBucketItem");

                                //if not a emailed list checkup then animate item
                                if (sender == '') $(obj).animate({ height: "show", opacity: "show" }, 400, function() { parent.pageHelper.dottedLineExtender(); });

                                intCounter++;
                                checkEmailIcons(ca[ii], zero, ii);
                                break;
                            }
                        }
                        //if no longer in bucket cookie then untick the checkbox that is hidden for compare etc..
                        if ((intCounter == 0) && (itemId == "txtCID")) {
                            var objSelect = document.getElementById("dlCourseBucket_ctl" + zero + ii + "_cbSelect");
                            objSelect.checked = false;
                        }
                    } else { break }
                }
            } else {
                for (ii = 0; ii < 99; ii++) {
                    var zero = "";
                    if (ii < 10) zero = "0";
                    var divItem = document.getElementById("dlCourseBucket_ctl" + zero + ii + "_divBucketItem");

                    //if no bucket cookie then hide all items
                    if (sender == '') $(divItem).animate({ height: "hide", opacity: "hide" }, 400, function () { parent.pageHelper.dottedLineExtender(); });
                }
            }
        }

        var checkboxCount = function() {
            // Counts the number of checkboxes that are checked
            var form1 = $("form#form1")[0];
            var strCompareIDs = "";
            for (var i = 0; i < form1.elements.length; i++) {
                var e = form1.elements[i];
                // checks for ticked checkboxes that are visible (this ignores the emailed already checked)
                if ((e.type == 'checkbox') && (!e.disabled) && (e.checked)) {
                    // finds job id from next form element
                    strCompareIDs = strCompareIDs + form1.elements[i + 1].value + ",";
                }
            }
            // removes extra comma from string
            if (strCompareIDs.length > 1) strCompareIDs = strCompareIDs.substring(0, strCompareIDs.length - 1)
            return strCompareIDs;
        }

        var checkEmailIcons = function(caValue, zero, ii) {
            if (parent.pageHelper.readCookie('Emailed')) {
                //this changes the email icon to a sent email icon on items within the "Emailed" cookie
                var emailedIDs = parent.pageHelper.readCookie('Emailed').split(',');
                for (var iii = 0; iii < emailedIDs.length; iii++) {
                    if (caValue == emailedIDs[iii]) {
                        document.getElementById("dlCourseBucket_ctl" + zero + ii + "_imgEmailIcon").src = "/images/icons/emailSent.gif";
                        document.getElementById("dlCourseBucket_ctl" + zero + ii + "_imgEmailIcon").title = "Email Already Sent";
                        break;
                    }
                }
            }
        }

        var updateBucket = function() {
            if (!parent.pageHelper.readCookie('Bucket')) {
                document.getElementById("divEmptyBucket").style.display = 'block';
                var saveBucket = $("#divSaveBucket");
                saveBucket.hide();

                var emptyLink = $("#divEmptyBucket2");
                emptyLink.hide();
                $("#divCourseCompare").hide();
                $("#divEmailAll").hide();

                $('#noOptionsAvailable').remove();
                $("<div id='noOptionsAvailable' style='text-align:center; color: #888; cursor: pointer;'>No Options Available</div>").insertAfter(emptyLink);
            }
            parent.courseBucketCounter();
        }

        var loadingShortlist = false;

        return {

            init: function() {
                this.showColumn(document.getElementById('phMain_RightPanel'));
                this.checkForBucket();
                var shortlistRecord = $("input#shortlistRecord");
                if (shortlistRecord.length > 0) {
                    var currentBucketVal = parent.pageHelper.readCookie('Bucket');
                    if (shortlistRecord.val() != currentBucketVal && !loadingShortlist) {
                        loadingShortlist = true;
                        $("table#dlCourseBucket").find("div").stop();
                        $("div#divLeftContent").load("/search/myshortlist.aspx?cookieCheck=true&r=" + (new Date()).getTime().toString() + " div#divLeftContent > *", function() {
                            $("table#dlCourseBucket").find("div").animate({ height: "show", opacity: "show" }, 400);
                            updateBucket()
                        });
                    }
                }
            },

            showColumn: function(sender) {
                var thisParent = parent;
                $(sender).animate({ height: "show", opacity: "show" }, 500, function() {
                    thisParent.onload_Complete();
                });
            },

            onload_Complete: function() {
                dottedLineExtender();

                //--check if the referrer page is courses.aspx to the browser cache can be used, if not, use cookie url--
                var strReferrerURL = document.referrer.toLocaleLowerCase();

                //document.getElementById("breadCrumb").innerHTML = "<a href='http://" + options.domain + "/'>" + options.domain.replace("www.", "") + "</a> &gt; <a id='ancBreadCrumbSearch' href='/search'>Search</a> &gt; My Shortlist";

                if (strReferrerURL.indexOf("courses.aspx", 0) > -1) {
                    //document.getElementById("siteMenuRootNode").style.display = "block";
                    document.getElementById("siteMenuRootNode").href = "javascript:window.history.back(1);";
                    //document.getElementById("breadCrumb").innerHTML = "<a href='http://" + options.domain + "/'>" + options.domain.replace("www.", "") + "</a> &gt; <a id='ancBreadCrumbSearch' href='javascript:window.history.back();'>Masters Search</a> &gt; My Shortlist";
                } else {
                    this.lastSearchFromCookie();
                    this.lastSearchFromCookieForBreadCrumb();
                }
            },

            compareCheck: function() {
                var intCompareCount = checkboxCount().split(',').length;
                if (checkboxCount().split(",") == "") intCompareCount = 0
                if ((intCompareCount) > 1 && (intCompareCount < 4)) {
                    var strURL = options.comparePageUrl + '?' + options.compareQsKey + '=' + checkboxCount();
                    location.href = strURL;
                }
                else {
                    var bucketCounter = $("#lblBucketCounter").add("#shortlistButtons_lblBucketCounter");
                    if (bucketCounter.html() == "0") {
                        alert("Please add items to your shortlist before using this feature.");
                    } else {
                        alert("You must select 2 or 3 items to use the compare feature.");
                    }
                }
            },


            groupEmailCheck: function() {
                try {
                    var intEmailCount = checkboxCount().split(',').length;

                    var obj = document.getElementById("ancGroupEmail");
                    if (checkboxCount().split(',') == "") intEmailCount = 0
                    if ((intEmailCount) > 0 && (intEmailCount < 11)) {
                        this.openEE();
                        if ((navigator.userAgent.match(/iPad/i)) || (navigator.userAgent.match(/iPhone/i))) {
                            obj.href = "http://" + options.domain + "/search/EmailEnquiryGroup.aspx?FACIDs=" + checkboxCount();
                        }
                        return true;
                    }
                    else {
                        if ((intEmailCount) > 0) {
                            alert("We only allow you to group email 10 courses at a time.");
                        }
                        else {
                            alert("Please select which courses you wish to email.");
                        }
                        return false;
                    }
                }
                catch (ex) {
                    alert(ex);
                }
            },

            openEE: function() { // open an Email Enquiry
                var urlString = "/search/EmailEnquiryGroup.aspx?FACIDs=" + checkboxCount();
                Shadowbox.open({
                    content: urlString,
                    player: "iframe",
                    title: "Group Email Enquiry",
                    height: 550,
                    width: 640
                });
            },

            checkForBucket: function() {
                updateBucket();
                refreshBucketItems('');
            },

            addRemoveFromBucket: function(value, days, imgBinID) {

                var obj = imgBinID.substring(0, 20) + "_divBucketItem";

                var i = 0;
                var ii = 0;

                if (parent.pageHelper.readCookie('Bucket')) {
                    var ca = parent.pageHelper.readCookie('Bucket').split(',');
                    var cb = new Array();
                    for (i = 0; i < ca.length; i++) {
                        //rebuild ca array in cb without duplicate matches i.e. remove from bucket
                        if (value != ca[i]) { cb[ii] = ca[i]; ii++; }
                    }
                }
                if (i > 0) {
                    if (i == ii) {
                        parent.pageHelper.createCookie('Bucket', cb.join() + ',' + value, days);
                    }
                    else {
                        parent.pageHelper.createCookie('Bucket', cb.join(), days);
                        //hides deleted bucket item
                        $(document.getElementById(obj)).animate({ height: "toggle", opacity: "toggle" }, 400, function() { $(document.getElementById(obj)).parent().parent().hide(); dottedLineExtender(); });
                        //removes tick from checkbox

                        var objSelect = imgBinID.substring(0, 20) + "_cbSelect";
                        switch (options.domain.toLowerCase()) {
                            case "www.findamasters.com": document.getElementById(objSelect).checked = false; break;
                        }
                    }
                } else {
                    parent.pageHelper.createCookie('Bucket', value, days);
                }
                //update bucket counter
                updateBucket();
            },

            emptyBucket: function() {
                var bucketCounter = $("#lblBucketCounter").add("#shortlistButtons_lblBucketCounter");
                if (bucketCounter.html() == "0") {
                    alert("There are no items in your shortlist to delete.");
                } else {

                    if (parent.pageHelper.areYouSure("Are you sure you want to empty your shortlist?")) {
                        parent.pageHelper.createCookie('Bucket', '', 0);
                        refreshBucketItems('');
                        updateBucket();
                    }
                }
            },

            lastSearchFromCookieForBreadCrumb: function() {
                if (parent.pageHelper.readCookie('LastSearch')) {
                    var ca = parent.pageHelper.readCookie('LastSearch').split(',');
                    if (ca != "") {
                        document.getElementById("siteMenuRootNode").href = ca;
                    }
                }
            },

            lastSearchFromCookie: function() {
                if (parent.pageHelper.readCookie('LastSearch')) {
                    var ca = parent.pageHelper.readCookie('LastSearch').split(',');

                    if (ca != "") {
                        document.getElementById("siteMenuRootNode").href = ca;
                        document.getElementById("siteMenuRootNode").attributes.item(0);
                        //document.getElementById("siteMenuRootNode").style.display = "block";
                    } 
                }
            },

            ValidateForm: function() {
                var txtEmail = document.getElementById('txtEmail').value;
                txtEmail = txtEmail.replace(" ", "");
                if (txtEmail == "") { return false; } else {
                    var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                    if (!(filter.test(txtEmail))) {
                        alert('Please provide a valid email address');
                        txtEmail.focus
                        return false;
                    }
                }
            }

        };
    }

    // Details page methods
    var bespokePage = function(parent, options) {

        return {

            init: function() {
                this.checkForBucket();
                var self = this;
                $(document).ready(function() {
                    self.onload_Complete();
                });
            },

            onload_Complete: function() {
                dottedLineExtender();

                //--check if the referrer page is courses.aspx to the browser cache can be used, if not, use cookie url--
                var strReferrerURL = document.referrer.toLocaleLowerCase();

                if (strReferrerURL.indexOf("courses.aspx", 0) > -1) {
                    //document.getElementById("siteMenuRootNode").style.display = "block";
                    document.getElementById("siteMenuRootNode").href = "javascript:window.history.back(1);";
                } else {
                    this.lastSearchFromCookie();
                    this.lastSearchFromCookieForBreadCrumb();
                }
            },

            lastSearchFromCookieForBreadCrumb: function() {
                if (parent.pageHelper.readCookie('LastSearch')) {
                    var ca = parent.pageHelper.readCookie('LastSearch').split(',');
                    if (ca != "") {
                        document.getElementById("ancBreadCrumbSearch").href = ca;
                    }
                }
            },

            lastSearchFromCookie: function() {
                if (parent.pageHelper.readCookie('LastSearch')) {
                    var ca = parent.pageHelper.readCookie('LastSearch').split(',');

                    if (ca != "") {
                        document.getElementById("siteMenuRootNode").href = ca;
                        document.getElementById("siteMenuRootNode").attributes.item(0);
                        //document.getElementById("siteMenuRootNode").style.display = "block";
                    }
                }
            },

            checkForBucket: function() {
                var value = document.getElementById("lblCourseID").innerHTML;

                if (parent.pageHelper.readCookie('Bucket')) {
                    var c = parent.pageHelper.readCookie('Bucket').split(',');
                    for (var i = 0; i < c.length; i++) {
                        if (c[i] == value) {
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                            }
                        }
                    }
                }

                //update bucket counter
                parent.courseBucketCounter();
            },

            addRemoveFromBucket: function(sender, days) {
                var i = 0;
                var ii = 0;
                var value = document.getElementById("lblCourseID").innerHTML;
                var cb = new Array();

                if (parent.pageHelper.readCookie('Bucket')) {
                    var ca = parent.pageHelper.readCookie('Bucket').split(',');
                    for (i = 0; i < ca.length; i++) {
                        //rebuild ca array in cb without duplicate matches i.e. remove from bucket
                        if (value != ca[i]) { cb[ii] = ca[i]; ii++; }
                    }
                }

                if (cb.length < 50) {

                    if (i > 0) {
                        if (i == ii) {
                            parent.pageHelper.createCookie('Bucket', value + ',' + cb.join(), days);
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                            }
                        }
                        else {
                            parent.pageHelper.createCookie('Bucket', cb.join(), days);
                            var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag0.gif";
                            var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag0.gif";
                            var spnShortlistButton = document.getElementById("spnShortlistButton");
                            if (spnShortlistButton != undefined && spnShortlistButton != null) {
                                document.getElementById("spnShortlistButton").innerHTML = "Add";
                                $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentre.gif')");
                                document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeft.gif";
                                document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRight.gif";
                            }
                        }
                    } else {
                        parent.pageHelper.createCookie('Bucket', value, days);
                        var imglistFlag = document.getElementById("imglistFlag"); if (imglistFlag != undefined && imglistFlag != null) imglistFlag.src = "/images/listFlag1.gif";
                        var imgFlag3 = document.getElementById("imgFlag3"); if (imgFlag3 != undefined && imgFlag3 != null) imgFlag3.src = "/images/listFlag1.gif";
                        var spnShortlistButton = document.getElementById("spnShortlistButton");
                        if (spnShortlistButton != undefined && spnShortlistButton != null) {
                            document.getElementById("spnShortlistButton").innerHTML = "Remove x";
                            $("#ancShortlistButton span").css("background-image", "url('/images/icons/shortlistButtonCentreRed.gif')");
                            document.getElementById("imgShortlistButtonLeft").src = "/images/icons/shortlistButtonLeftRed.gif";
                            document.getElementById("imgShortlistButtonRight").src = "/images/icons/shortlistButtonRightRed.gif";
                        }
                    }
                } else {
                    alert("Your course bucket is limited to 50 courses at a time");
                }
                //update bucket counter
                parent.courseBucketCounter();
            }
        }
    }

    // Main search area methods
    sciReg.searchHelperSingleton = {

        pageHelper: options.pageHelper,

        pageMethods: null,

        init: function() {
            //if ipad or iphone then remove the rel= function for shadowbox
            if ((navigator.userAgent.match(/iPad/i)) || (navigator.userAgent.match(/iPhone/i))) {
                //$(a[rel ^= 'shadowbox']).val("");
            } else {
                Shadowbox.init({ handleOversize: "drag", modal: true, overlayOpacity: 0.75, onOpen: this.pageHelper.scrollBackground, onClose: this.pageHelper.scrollBackground, troubleElements: ["select", "object", "embed", "canvas", "input", "textarea"] });
            }
            switch (options.page) {
                case "search": this.pageMethods = searchPage(this, options); break;
                case "details": this.pageMethods = detailsPage(this, options); break;
                case "shortlist": this.pageMethods = shortlistPage(this, options); break;
                case "bespoke": this.pageMethods = bespokePage(this, options); break;
                default: this.pageMethods = simplePage(this, options); break;
            }
            this.pageMethods.init();
        },

        courseBucketCounter: function(counterId) {
            if (counterId == undefined) counterId = "shortlistButtons_lblBucketCounter";
            var bucketCounter = $("#lblBucketCounter").add("#" + counterId);
            if (this.pageHelper.readCookie('Bucket')) {
                var ca = this.pageHelper.readCookie('Bucket').split(',');
                if (ca != "") {
                    bucketCounter.html(ca.length);
                    this.removePopupOnBucketIcon()
                }
            } else {
                bucketCounter.html(0);
            }
            // update shortlist widget
            if (typeof shortlist == 'object') { shortlist.checkShortlist(); }
        },

        removePopupOnBucketIcon: function(idFormat) {
            if (idFormat == undefined) idFormat = 'dlCourseSearchResults_ctl{0}_imgCourseIcon';
            var strObjName = '';
            var strNumber = '';

            for (var ii = 0; ii < 100; ii++) {
                if (ii < 10) { strNumber = '0' + ii } else { strNumber = '' + ii }
                strObjIMG = idFormat.replace("{0}", strNumber);
                if (document.getElementById(strObjIMG)) {
                    document.getElementById(strObjIMG).setAttribute("onmouseover", "");
                    //document.getElementById("divCourseBucket").setAttribute("onmouseover", "");
                }
            }
        },

        checkForBucket: function() { return this.pageMethods.checkForBucket(); },
        form_submit: function(sender, urlKey, pgKey) { if (this.pageMethods == null) setTimeout(function() { sciReg.searchHelperSingleton.form_submit(sender, urlKey, pgKey); }, 50); else return this.pageMethods.form_submit(sender, urlKey, pgKey); },
        pagerBuilder: function() { return this.pageMethods.pagerBuilder(); },
        getCourseCountTitle: function(strLocation) { return this.pageMethods.getCourseCountTitle(strLocation); },
        pleaseWaitDiv: function(sender, id) { return this.pageMethods.pleaseWaitDiv(sender, id); },
        clearKeywords: function(obj, clearId) { return this.pageMethods ? this.pageMethods.clearKeywords(obj, clearId) : null; },
        showAdverts: function(id, onComplete) { return this.pageMethods.showAdverts(id, onComplete); },
        addRemoveFromBucket: function(sender, days, imgBinId) { return this.pageMethods.addRemoveFromBucket(sender, days, imgBinId); },
        createBackCookie: function(days) { return this.pageMethods.createBackCookie(days); },
        lastSearchFromCookie: function() { return this.pageMethods.lastSearchFromCookie(); },
        lastSearchFromCookieForBreadCrumb: function() { return this.pageMethods.lastSearchFromCookieForBreadCrumb(); },
        emptyBucket: function() { return this.pageMethods.emptyBucket(); },
        compareCheck: function() { return this.pageMethods.compareCheck(); },
        groupEmailCheck: function() { return this.pageMethods.groupEmailCheck(); },
        onload_Complete: function() { return this.pageMethods.onload_Complete(); }
    };

    return sciReg.searchHelperSingleton;
}

if (sciReg.pageHelperClass == undefined) {
    sciReg.pageHelperClass = function() {
        return {

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
                    if (document.URL.indexOf("OD=1", 0) > -1) reload() // this is for old showopenday.asp forwards for opendays, it reloads the search after close
                } else {
                    objHTML.style.overflow = "hidden";
                    // Hiding the vertical scrollbar and having the webpage centered makes the content jump across by the amount of the scrollbars width
                    // this adjust that if html width is not set i.e. it only fires once.
                    if (objHTML.style.width == "") objHTML.style.width = "" + parseInt(objHTML.offsetWidth - 17) + "px";
                }
            },

            dottedLineExtender: function() {
                //document.getElementById('phMain_RightPanel').style.height = 0 + "px";
                var clientHeight = document.body.clientHeight - 300;
                document.getElementById('phMain_RightPanel').style.height = clientHeight + "px";
                //alert(document.getElementById('phMain_RightPanel').offsetHeight);
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

            queryString: function(key) {
                var qs = window.location.search.substring(1).split("&");
                for (var i = 0; i < qs.length; i++) {
                    var e = gy[i].split("=");
                    if (e[0] == key) return e[1];
                }
            }

        };
    }
}

function scrollBackground() { sciReg.pageHelper.scrollBackground(); }
function dottedLineExtender() { sciReg.pageHelper.dotChecker(); }
function dotChecker() { sciReg.pageHelper.dotChecker(); }
