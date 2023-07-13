import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/authentication.dart';
import 'package:google_sign_in/google_sign_in.dart';

const googleClientID = String.fromEnvironment("GOOGLE_CLIENT_ID");

final GoogleSignIn _googleSignIn =
    GoogleSignIn(scopes: ['email'], clientId: googleClientID);

class GoogleSignInWidget extends StatelessWidget {
  const GoogleSignInWidget({super.key});


  Future<void> _signInWithGoogle() async {
    try {
      // Trigger the Google Authentication flow
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();

      // Obtain the Google Authentication details
      final GoogleSignInAuthentication googleAuth =
          await googleUser!.authentication;

      if (googleAuth.idToken != null) {
        bool verified =
            await authenticateUser(googleAuth.idToken!);
        if (verified) {}
      } else {}
      print(googleAuth.accessToken);
    } catch (error) {
      print('Error signing in with Google: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: _signInWithGoogle,
      child: const Text('Sign In with Google'),
    );
  }
}
