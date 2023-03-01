const String ipAddress = String.fromEnvironment("API_IP", defaultValue: "127.0.0.1");
const String port = String.fromEnvironment("API_PORT");
const String apiBaseUrl = port == "" ? "http://$ipAddress:$port/api" : "http://$ipAddress/api";
const String coinUrl = "$apiBaseUrl/coin";
