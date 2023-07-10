const String ipAddress = String.fromEnvironment("API_URL");
const String port = String.fromEnvironment("API_PORT");
final String domain = port.isEmpty ? ipAddress : "$ipAddress:$port";

const String coinPath = "/api/coin";
const String editionPath = "/api/edition/page";
const String frontImage = "/api/frontImage/";

Uri generateUri(String path, Map<String, String> queryParameters) {
    if (ipAddress == "localhost") {
      return Uri.http(domain, path, queryParameters=queryParameters);
    } else {
      return Uri.https(domain, path, queryParameters=queryParameters);
    }
}
