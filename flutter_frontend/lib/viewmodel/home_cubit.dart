import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_api.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:google_sign_in/google_sign_in.dart';

const googleClientID = String.fromEnvironment('GOOGLE_CLIENT_ID');

class HomeCubit extends Cubit<GoogleSignInAccount?> {
  HomeCubit(this.authenticationApi, this.authenticationRepository)
      : super(null) {
    _googleSignIn.onCurrentUserChanged
        .listen((GoogleSignInAccount? account) async {
      if (account != null) {
        await login(account);
      }
    });
  }
  AuthenticationApi authenticationApi;
  AuthenticationRepository authenticationRepository;

  final GoogleSignIn _googleSignIn = GoogleSignIn(
    scopes: ['email', 'profile', 'openid'],
    clientId: googleClientID,
  );

  Future<void> login(GoogleSignInAccount account) async {
    try {
      final authentication = await account.authentication;
      final idToken = authentication.idToken;
      if (idToken != null) {
        final backendAuthentication =
            await authenticationApi.authenticateUser(idToken);

        // TODO extract to method -> ask when needed
        final authorized =
            await _googleSignIn.canAccessScopes(_googleSignIn.scopes);
        if (!authorized) {
          await _googleSignIn.requestScopes(_googleSignIn.scopes);
        }

        if (backendAuthentication != true) {
          await authenticationRepository.login('', '');
          emit(null);
        } else {
          await authenticationRepository.login(
            account.email,
            authentication.idToken ?? '',
          );
          emit(account);
        }
      }
    } catch (exception) {
      print(exception);
      emit(null);
    }
  }
}
