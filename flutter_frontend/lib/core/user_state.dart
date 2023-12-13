import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/authentication.dart';
import 'package:google_sign_in/google_sign_in.dart';

const googleClientID = String.fromEnvironment('GOOGLE_CLIENT_ID');

// State
class UserState {

  UserState(this.user);
  GoogleSignInAccount? user;
}

// Bloc
class UserBloc extends Cubit<UserState> {
  UserBloc() : super(UserState(null)) {
    _googleSignIn.onCurrentUserChanged.listen((GoogleSignInAccount? account) {
      if (account != null) {
        login(account);
      }
    });
  }

  final GoogleSignIn _googleSignIn =
      GoogleSignIn(scopes: ['email', 'profile'], clientId: googleClientID);

  Future<void> login(GoogleSignInAccount account) async {
    final authentication = await account.authentication;
    final idToken = authentication.idToken;
    if (idToken != null) {
      // TODO inject backend https://bloclibrary.dev/#/architecture
      final backendAuthentication = await authenticateUser(idToken);

      if (backendAuthentication != true) {
        emit(UserState(null));
      } else {
        emit(UserState(account));
      }
    }
  }
}
