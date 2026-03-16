import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/HomePage/home_cubit.dart';
import 'package:go_router/go_router.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:google_sign_in_platform_interface/google_sign_in_platform_interface.dart';
import 'package:google_sign_in_web/google_sign_in_web.dart' as web;

class HomeView extends StatelessWidget {
  const HomeView({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocListener<HomeCubit, GoogleSignInAccount?>(
      listener: (context, state) {
        if (state != null) {
          context.go('/library/${state.email}');
        }
      },
      child: BlocBuilder<HomeCubit, GoogleSignInAccount?>(
        builder: (context, state) {
          return Scaffold(
            body: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: (state == null)
                        // ? const Center(child: Text('Not signed in'))
                        // : const Center(child: Text('Signed in')),
                        ? (GoogleSignInPlatform.instance
                                as web.GoogleSignInPlugin)
                            .renderButton()
                        : Center(
                            child: GoogleUserCircleAvatar(identity: state),
                          ),
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
        },
      ),
    );
  }
}
