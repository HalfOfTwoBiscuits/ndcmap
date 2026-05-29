// Helper that displays error message notifications.
class NotificationHandler {
    #errorRegion;

    constructor(errorRegionElem) {
        this.#errorRegion = errorRegionElem;
    }

    announceError(errorMessage) {
        let notifElem = document.createElement('p');
        notifElem.textContent = content;
        notifElem.className = "bad box";
        this.#errorRegion.appendChild(notifElem);
    }
}