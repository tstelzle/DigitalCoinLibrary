import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/home.dart';
import 'package:flutter_frontend/sign_in/google_sign_in.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<UserBloc, UserState>(
      builder: (context, userState) {
        return Scaffold(
          body: Center(
            child: Padding(
              padding: const EdgeInsets.all(8),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text('Digital Coin Library'),
                  GoogleSignInPage(userState: userState),
                  ElevatedButton(
                    onPressed: () => _navigateToLibraryPage(userState, context),
                    child: const Text('Bibliothek'),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  void _navigateToLibraryPage(UserState userState, BuildContext context) {
    Navigator.of(context).push(
      MaterialPageRoute<LibraryPage>(
        builder: (context) => LibraryPage(userState: userState),
      ),
    );
  }
}
