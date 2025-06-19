import 'package:flutter_frontend/model/user.dart';

class AuthenticationRepository {
  User? _cachedUser;

  Future<User?> getCurrentUser() async {
    if (_cachedUser != null) return _cachedUser;
    return null;
  }

  Future<User> login(String email, String idToken) async {
    final user = User(email: email, idToken: idToken);
    _cachedUser = user;
    return user;
  }

  Future<void> logout() async {
    _cachedUser = null;
  }
}
