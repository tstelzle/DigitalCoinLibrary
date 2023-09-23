import 'api.dart';

Future<bool>
authenticateUser(String idToken) async {
  // TODO spams backend
  String body = await get("/api/authenticate", {"idToken": idToken});

  if (body == "true") {
    return true;
  } else {
    return false;
  }
}
