const String ipAddress = String.fromEnvironment("API_URL");
const String port = String.fromEnvironment("API_PORT");
final String apiBaseUrl = port.isEmpty ? "https://$ipAddress/api" : "http://$ipAddress:$port/api";
final String coinUrl = "$apiBaseUrl/coin";
