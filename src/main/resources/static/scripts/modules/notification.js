// Helper that displays error message notifications.
export class NotificationHandler {
    #errorRegion;

    constructor(errorRegionElem) {
        this.#errorRegion = errorRegionElem;
    }

    announceError(errorMessage) {
        let notifElem = document.createElement('p');
        notifElem.textContent = errorMessage;
        notifElem.className = "bad box";
        this.#errorRegion.appendChild(notifElem);
    }
}