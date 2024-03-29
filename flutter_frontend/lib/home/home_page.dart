import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/home.dart';
import 'package:flutter_frontend/sign_in/google_sign_in.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<UserBloc, UserState>(builder: (context, userState) {
      return Scaffold(
          body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.all(8),
              child: GoogleSignInPage(userState: userState),
            ),
            Padding(
              padding: const EdgeInsets.all(8),
              child: ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).push(MaterialPageRoute(
                        builder: (context) =>
                            LibraryPage(userState: userState),),);
                  },
                  child: const Text('Bibliothek'),),
            ),
          ],
        ),
      ),);
    },);
  }
}
