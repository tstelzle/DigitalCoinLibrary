import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_api.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/repository/edition_api.dart';
import 'package:flutter_frontend/view/home_page.dart';
import 'package:flutter_frontend/view/library_page.dart';
import 'package:go_router/go_router.dart';

// GoRouter configuration
final _router = GoRouter(
  routes: [
    GoRoute(
      path: '/',
      builder: (context, state) => const HomePage(),
    ),
    GoRoute(
      path: '/library/:librarianID',
      builder: (context, state) {
        return LibraryPage(librarianId: state.pathParameters['librarianID']!);
      },
    ),
    GoRoute(
      path: '/library',
      builder: (context, state) => const LibraryPage(librarianId: ''),
    ),
  ],
);

void main() async {
  runApp(
    MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (context) => EditionApi()),
        RepositoryProvider(create: (context) => AuthenticationApi()),
        RepositoryProvider(
          create: (context) => AuthenticationRepository(),
        ),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Digital Coin Library',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routerConfig: _router,
    );
  }
}
