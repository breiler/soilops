export class WebsocketUtils {

    public static websocketUrlByPath(path) {
        return this.websocketProtocolByLocation() +
            window.location.hostname +
            this.websocketPortWithColonByLocation() +
            path;
    }

    private static websocketProtocolByLocation() {
        return window.location.protocol === "https:" ? "wss://" : "ws://";
    }

    private static websocketPortWithColonByLocation() {
        const defaultPort = window.location.protocol === "https:" ? "443" : "80";
        if (window.location.port !== defaultPort) {
            return ":" + window.location.port;
        } else {
            return "";
        }
    }
}
