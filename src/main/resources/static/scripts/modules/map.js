export class MapHandler {
    #map;
    #image;

    constructor() {
        this.#map = window.L.map("map", {
            crs: L.CRS.Simple
        });
        this.#image = L.imageOverlay(
            `${window.location.hostname}/images/Map.png`,
            [[0,0], [3000, 5500]]
        ).addTo(this.#map);
    }
}