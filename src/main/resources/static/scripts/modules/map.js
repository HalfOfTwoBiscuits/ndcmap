export class MapHandler {
    #map;
    #image;

    constructor() {
        this.#map = window.L.map("map", {
            crs: L.CRS.Simple
        });

        // Position, width, and height.
        let mapBounds = [[0,0], [300, 550]];

        this.#image = L.imageOverlay(
            `/images/Map.png`,
            mapBounds
        )
        this.#map.fitBounds(mapBounds);

        this.#image.addTo(this.#map);
    }
}