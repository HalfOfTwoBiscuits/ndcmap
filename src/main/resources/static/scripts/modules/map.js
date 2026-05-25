export class MapHandler {
    #map;
    #image;

    constructor() {
        this.#map = window.L.map("map", {
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

        this.#image.addTo(this.#map);
    }
}