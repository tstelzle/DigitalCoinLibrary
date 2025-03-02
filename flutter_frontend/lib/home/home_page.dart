import 'package:flutter/material.dart';
import 'package:flutter_frontend/sign_in/google_sign_in.dart';
import 'package:go_router/go_router.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Padding(
              padding: EdgeInsets.all(8),
              child: GoogleSignInPage(),
            ),
            Padding(
              padding: const EdgeInsets.all(8),
              child: ElevatedButton(
                onPressed: () {
                  context.go('/library');
                },
                child: const Text('Bibliothek'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
