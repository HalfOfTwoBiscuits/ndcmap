import { MapHandler } from "./modules/map.js"
import { NotificationHandler } from "./modules/notification.js"

// Class responsible for initialising custom JS and adding it to the document's scope.
class Base {
    static loadPage() {

        // Add notification handler for error messages.
        let notificationHandler = null;
        let errorRegion = document.getElementById("errors");
        if (errorRegion) {
            notificationHandler = new NotificationHandler(errorRegion);
        }

        // Add map widget.
        if (document.getElementById("map")) {
            let areaDetailDiv = document.getElementById("areaInfo");
            let areaNameHeading = document.getElementById("areaName");
            let areaAltNamesP = document.getElementById("areaAltNames");

            if (areaDetailDiv && areaNameHeading && areaAltNamesP) {
                let mh = new MapHandler(notificationHandler, areaDetailDiv, areaNameHeading, areaAltNamesP);
                window.mapHandler = mh;
            }
        }
    }
}

// Add Base to the namespace for the HTML document.
// This must be done explicitly because JS with type `module` isn't
// added to the namespace until the HTML is completely loaded, which may
// not be the case at the time of the `onload` event that calls `Base.loadPage()`.
window.Base = Base;