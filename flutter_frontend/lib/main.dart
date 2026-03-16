import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/repository/user_repository.dart';
import 'package:flutter_frontend/repository/edition_repository.dart';
import 'package:flutter_frontend/HomePage/home_page.dart';
import 'package:flutter_frontend/LibraryPage/library_page.dart';
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
        RepositoryProvider(create: (context) => EditionRepository()),
        RepositoryProvider(create: (context) => AuthenticationRepository()),
        RepositoryProvider(
          create: (context) => UserRepository(),
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
