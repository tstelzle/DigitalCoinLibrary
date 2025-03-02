import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:google_sign_in_platform_interface/google_sign_in_platform_interface.dart';
import 'package:google_sign_in_web/google_sign_in_web.dart' as web;

class GoogleSignInPage extends StatefulWidget {
  const GoogleSignInPage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<UserBloc, UserState>(
      builder: (context, userState) {
        if (userState.user == null) {
          return (GoogleSignInPlatform.instance as web.GoogleSignInPlugin)
              .renderButton();
        }

        return Center(
          child: GoogleUserCircleAvatar(identity: userState.user!),
        );
      },
    );
  }
}
