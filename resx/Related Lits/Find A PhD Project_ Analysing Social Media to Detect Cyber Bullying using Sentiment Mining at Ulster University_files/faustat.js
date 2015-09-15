var faustat = new function () {
    this.addstat = function (domain, type, id) {
        //saveStat
        var jqxhr = $.post("/common/faustat.ashx", { domain: domain, type: type, id: id }, function () {
            //alertify.log("Data Sent");
        })
          .done(function (id) {
              //alertify.success("Saved");
              //console.log("Stat Details: " + domain + "," + type + "," + id);
          })
          .fail(function () {
              //alertify.error("Save Settings Error");
          })
          .always(function () {
              //always run this code last regardless of status
              //alertify.log("Finished");
          });
    };
    this.addstats = function (domain, type, id) {
        //saveStat
        var jqxhr = $.post("/common/faustats.ashx", { domain: domain, type: type, id: id, hostHeader: '' }, function () {
            //alertify.log("Data Sent");
        })
          .done(function (id) {
              //alertify.success("Saved");
              //console.log("Stat Details: " + domain + "," + type + "," + id);
          })
          .fail(function () {
              //alertify.error("Save Settings Error");
          })
          .always(function () {
              //always run this code last regardless of status
              //alertify.log("Finished");
          });
    };
    this.addstats = function (domain, type, id, TinT) {
        //saveStat
        var jqxhr = $.post("/common/faustats.ashx", { domain: domain, type: type, id: id, TinT: TinT }, function () {
            //alertify.log("Data Sent");
        })
          .done(function (id) {
              //alertify.success("Saved");
              //console.log("Stat Details: " + domain + "," + type + "," + id);
          })
          .fail(function () {
              //alertify.error("Save Settings Error");
          })
          .always(function () {
              //always run this code last regardless of status
              //alertify.log("Finished");
          });
    };
    this.addstats = function (domain, type, id, TinT, hostHeader) {
        //saveStat
        var jqxhr = $.post("/common/faustats.ashx", { domain: domain, type: type, id: id, TinT: TinT, hostHeader: hostHeader }, function () {
            //alertify.log("Data Sent");
        })
          .done(function (id) {
              //alertify.success("Saved");
              //console.log("Stat Details: " + domain + "," + type + "," + id);
          })
          .fail(function () {
              //alertify.error("Save Settings Error");
          })
          .always(function () {
              //always run this code last regardless of status
              //alertify.log("Finished");
          });
    };
}