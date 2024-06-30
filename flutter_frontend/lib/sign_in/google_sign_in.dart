import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:google_sign_in_platform_interface/google_sign_in_platform_interface.dart';
import 'package:google_sign_in_web/google_sign_in_web.dart' as web;

class GoogleSignInPage extends StatelessWidget {
  const GoogleSignInPage({required this.userState, super.key});

  final UserState userState;

  @override
  Widget build(BuildContext context) {
    print('UserState (google_sign_in): ${userState == null}');
    print('UserState.user (google_sign_in): ${userState.user?.email}');

    if (userState.user == null) {
      try {
        return (GoogleSignInPlatform.instance as web.GoogleSignInPlugin)
            .renderButton();
      } catch (e) {
        print(e);
      }
    }

    return Center(
      child: GoogleUserCircleAvatar(identity: userState.user!),
    );
  }
}
