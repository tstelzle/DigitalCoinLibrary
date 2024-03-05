import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:google_sign_in/google_sign_in.dart';

import 'authentication.dart';

const googleClientID = String.fromEnvironment("GOOGLE_CLIENT_ID");

// State
class UserState {
  GoogleSignInAccount? user;
  GoogleSignInAuthentication? authentication;

  UserState(this.user, this.authentication);
}

// Bloc
class UserBloc extends Cubit<UserState> {
  UserBloc() : super(UserState(null, null)) {
    _googleSignIn.onCurrentUserChanged.listen((GoogleSignInAccount? account) {
      if (account != null) {
        login(account);
      }
    });
  }

  final GoogleSignIn _googleSignIn =
      GoogleSignIn(scopes: ['email', 'profile'], clientId: googleClientID);

  void login(GoogleSignInAccount account) async {
    final authentication = await account.authentication;
    final idToken = authentication.idToken;
    if (idToken != null) {
      // TODO inject backend https://bloclibrary.dev/#/architecture
      final backendAuthentication = await authenticateUser(idToken);

      if (backendAuthentication != true) {
        emit(UserState(null, null));
      } else {
        emit(UserState(account, authentication));
      }
    }
  }
}
