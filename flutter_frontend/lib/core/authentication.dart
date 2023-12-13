import 'package:flutter_frontend/core/api.dart';

Future<bool>
authenticateUser(String idToken) async {
  final body = await get('/api/authenticate', {'idToken': idToken});

  if (body == 'true') {
    return true;
  } else {
    return false;
  }
}
