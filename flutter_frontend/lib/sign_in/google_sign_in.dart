import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';

final GoogleSignIn _googleSignIn = GoogleSignIn(
    scopes: ['email'],
    clientId: const String.fromEnvironment("GOOGLE_CLIENT_ID")
);

class GoogleSignInWidget extends StatelessWidget {
  const GoogleSignInWidget({super.key});

  Future<void> _signInWithGoogle() async {
    try {
      // Trigger the Google Authentication flow
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();

      // Obtain the Google Authentication details
      final GoogleSignInAuthentication googleAuth =
          await googleUser!.authentication;

      // Create a new credential using the obtained token
      final AuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      print(credential.accessToken);
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
