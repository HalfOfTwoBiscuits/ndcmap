export class MapHandler {
    #map;
    #image;
    #notificationHandler;
    #selectedAreaIcon;
    #infoBoxElem;
    #areaNameElem;
    #altNamesElem;

    // Width and height of the map in map units.
    #MAP_WIDTH = 300;
    #MAP_HEIGHT = 550;
    #ICON_HEIGHT = 4.8;

    #MAP_UNIT_TO_PIXEL_SCALE = 10; // 1 map unit = 10 pixels on-image.
    #IMAGE_WIDTH = 3000;
    #IMAGE_HEIGHT = 5500;

    constructor(notificationHandler, infoBoxElem, areaNameElem, altNamesElem) {
        this.#map = L.map("map", {
            crs: L.CRS.Simple,
            minZoom: 1,
            maxZoom: 3
        });

        // Position, width, and height.
        // Co-ordinates in Leaflet are written y,x to match lat,long.
        let mapBounds = [[0,0], [this.#MAP_HEIGHT, this.#MAP_WIDTH]];

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
            [[0,0], [this.#ICON_HEIGHT,this.#ICON_HEIGHT]]
        )

        // Store elements for info box content.
        this.#infoBoxElem = infoBoxElem;
        this.#areaNameElem = areaNameElem;
        this.#altNamesElem = altNamesElem;

        // Store notification handler object.
        this.#notificationHandler = notificationHandler;

        // Register event for clicking the map.
        this.#map.on("click", (event) => {this.selectArea(event)});
    }

    async selectArea(event) {
        // Calculate corresponding x,y on the image for the x,y tapped on the map.
        let container = this.#map.getContainer();
        let containerStyle = window.getComputedStyle(container);

        let containerWidth = parseInt(containerStyle.width);
        let containerHeight = parseInt(containerStyle.height);

        // Determine the scale factor between pixels on-page and on the map image.
        let horzScaleFactor = this.#IMAGE_WIDTH / containerWidth;
        let vertScaleFactor = this.#IMAGE_HEIGHT / containerHeight;
        
        let x = Math.round(event.containerPoint.x * horzScaleFactor);
        let y = Math.round(event.containerPoint.y * vertScaleFactor);

        // Fetch area data from server.
        let url = `getAreaFromPos/${x}/${y}`;
        let response = await fetch(url);
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
                const json = await response.json();

                let centroidX = json.centroidX / this.#MAP_UNIT_TO_PIXEL_SCALE;
                let centroidY = json.centroidY / this.#MAP_UNIT_TO_PIXEL_SCALE;

                // Pan to area.
                this.#map.flyTo([centroidY, centroidX], 1.5);

                // Add icon showing the area is selected.
                this.#addAreaSelectedIcon(centroidX, centroidY);

                // Add info box.
                this.#addAreaInfoBox(json.areaName, json.altNames);
            }
        }
        catch (error) {
            // Display errors in the console, and to the user.
            console.error(error.message);
            if (this.#notificationHandler) {
                this.#notificationHandler.announceError("Something went wrong when selecting the tapped area. Please reload the page and try again.");
            }
            else {
                // This might happen if the error region element was not found.
                console.error("Notification handler object was not provided, so no error is shown to the user!")
            }
        }
    }

    #addAreaSelectedIcon(x, y) {
        this.#selectedAreaIcon.setBounds(
            [[y,x], [this.#ICON_HEIGHT,this.#ICON_HEIGHT]]
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