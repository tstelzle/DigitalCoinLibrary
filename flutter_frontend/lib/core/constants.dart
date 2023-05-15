const String ipAddress = String.fromEnvironment("API_URL");
const String port = String.fromEnvironment("API_PORT");
const String apiBaseUrl = port == "" ? "http://$ipAddress:$port/api" : "http://$ipAddress/api";
const String coinUrl = "$apiBaseUrl/coin";
