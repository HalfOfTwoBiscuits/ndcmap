import { MapHandler } from "./modules/map"

// Class responsible for initialising custom JS and adding it to the document's scope.
class Base {
    static loadPage() {
        if (document.getElementById("map")) {
            let mh = new MapHandler();
            window.mapHandler = mh;
        }
    }
}

// Add Base to the namespace for the HTML document.
// This must be done explicitly because JS with type `module` isn't
// added to the namespace until the HTML is completely loaded, which may
// not be the case at the time of the `onload` event that calls `Base.loadPage()`.
window.Base = Base;