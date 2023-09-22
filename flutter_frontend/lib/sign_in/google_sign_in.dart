import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:google_sign_in_platform_interface/google_sign_in_platform_interface.dart';
import 'package:google_sign_in_web/google_sign_in_web.dart' as web;

import '../core/authentication.dart';

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
            return Column(
              children: [
                const ListTile(
                  leading: Icon(Icons.person),
                  title: Text('ID Token'),
                ),
                SelectableText(auth.data?.idToken ?? ''),
                const ListTile(
                  leading: Icon(Icons.lock),
                  title: Text('Access Token'),
                ),
                SelectableText(auth.data?.accessToken ?? ''),
                FutureBuilder(
                    future: authenticateUser(auth.data!.idToken!),
                    builder: (context, verified) {
                      return Text(
                          verified.data! == true ? "Verified" : "Error");
                    })
              ],
            );
          });
    }
  }
}
