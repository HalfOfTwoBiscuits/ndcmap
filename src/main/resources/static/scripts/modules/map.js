export class MapHandler {
    #map;
    #image;
    #notificationHandler;
    #selectedAreaIcon;
    #infoBoxElem;
    #areaNameElem;
    #altNamesElem;

    constructor(notificationHandler, infoBoxElem, areaNameElem, altNamesElem) {
        this.#map = L.map("map", {
            crs: L.CRS.Simple,
            minZoom: 1,
            maxZoom: 3
        });

        // Position, width, and height.
        let mapBounds = [[0,0], [550, 300]];

        this.#image = L.imageOverlay(
            `/images/Map.png`,
            mapBounds
        )
        this.#map.fitBounds(mapBounds);

        // Restrict panning outside the map.
        this.#map.setMaxBounds(mapBounds);

        // Add map image.
        this.#image.addTo(this.#map);

        // Store icon for selected area.
        // Image from:
        // https://www.streamlinehq.com/icons/flex-line?search=box-outline&icon=ico_dtKyVX23N5nEEUHo
        this.#selectedAreaIcon = L.imageOverlay(
            '/images/SelectedAreaIcon.png',
            [[0,0], [48,48]]
        )

        // Store elements for info box content.
        this.#infoBoxElem = infoBoxElem;
        this.#areaNameElem = areaNameElem;
        this.#altNamesElem = altNamesElem;

        // Register event for clicking the map.
        map.on("click", (event) => {this.#selectArea(event)});
    }

    #selectArea(event) {
        let x = event.layerPoint.x;
        let y = event.layerPoint.y;

        // Fetch area data from server.
        let url = `${location.hostname}/getAreaFromPos/${x}/${y}`;
        fetch(url)
        .then((response) => {
            try {
                // Handle errors.
                if (response.status == 400) {
                    throw new Error(`Invalid x,y parameters for area select: ${x},${y}`);
                }
                else if (response.status == 404) {
                    console.log(`No area found at ${x},${y}`);
                }
                else if (!response.ok) {
                    throw new Error(`Unexpected error when selecting area at ${x},${y}`);
                }
                else {
                    let json = await response.json();
                    // Pan to area.
                    this.#map.flyTo([json.centroidX, json.centroidY], 1.5);
    
                    // Add icon showing the area is selected.
                    this.#addAreaSelectedIcon(json.centroidX, json.centroidY);

                    // Add info box.
                    this.#addAreaInfoBox(json.areaName, json.altNames);
                }
            }
            catch (error) {
                // Display errors in the console, and to the user.
                console.error(error.message);
                if (this.#notificationHandler) {
                    this.#notificationHandler.announceError(error.message);
                }
                else {
                    // This might happen if the error region element was not found.
                    console.error("Notification handler object was not provided, so no error is shown to the user!")
                }
            }
        })
    }

    #addAreaSelectedIcon(x, y) {
        this.#selectedAreaIcon.setBounds(
            [[y,x], [48,48]]
        );
        this.#selectedAreaIcon.addTo(this.#map);
    }

    #addAreaInfoBox(areaName, altNames) {
        this.#areaNameElem.textContent = areaName;
        this.#altNamesElem.textContent = `A.K.A. ${altNames.join(", ")}`;
        this.#infoBoxElem.hidden = false;
    }

    hideAreaInfoBox() {
        this.#infoBoxElem.hidden = true;
    }
}