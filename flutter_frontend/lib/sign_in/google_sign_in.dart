import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:google_sign_in_platform_interface/google_sign_in_platform_interface.dart';
import 'package:google_sign_in_web/google_sign_in_web.dart' as web;

import '../core/authentication.dart';
import '../core/user_state.dart';

const googleClientID = String.fromEnvironment("GOOGLE_CLIENT_ID");

class GoogleSignInPage extends StatefulWidget {
  const GoogleSignInPage({super.key});

  @override
  State<GoogleSignInPage> createState() => _GoogleSignInPageState();
}

class _GoogleSignInPageState extends State<GoogleSignInPage> {
  final GoogleSignIn _googleSignIn =
      GoogleSignIn(scopes: ['email'], clientId: googleClientID);

  GoogleSignInAccount? user;

  @override
  void initState() {
    _googleSignIn.onCurrentUserChanged.listen((GoogleSignInAccount? account) {
      setState(() {
        user = account;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if (user == null) {
      return (GoogleSignInPlatform.instance as web.GoogleSignInPlugin)
          .renderButton();
    } else {
      return FutureBuilder<GoogleSignInAuthentication>(
          future: user!.authentication,
          builder: (context, auth) {
            return BlocBuilder<UserBloc, UserState>(
                builder: (context, userState) {
              return Center(
                  child: FutureBuilder(
                      // TODO spams backend
                      future: authenticateUser(auth.data!.idToken!),
                      builder: (context, verified) {
                        if (verified.data! == true) {
                          context
                              .read<UserBloc>()
                              .add(LoggedInUserEvent(verified.data!));
                          context
                              .read<UserBloc>()
                              .add(UserUserEvent(user!.displayName!));
                          context
                              .read<UserBloc>()
                              .add(IdUserEvent(user!.email));
                          return GoogleUserCircleAvatar(identity: user!);
                        } else {
                          user == null;
                          // TODO AlertDialog for failed login
                          return const Icon(
                            Icons.error,
                            color: Colors.red,
                          );
                        }
                      }));
            });
          });
    }
  }
}
