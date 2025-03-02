import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/home_page.dart';
import 'package:flutter_frontend/home/library_page.dart';
import 'package:go_router/go_router.dart';
import 'package:google_sign_in/google_sign_in.dart';

// GoRouter configuration
final _router = GoRouter(
  routes: [
    GoRoute(
      path: '/',
      builder: (context, state) => const HomePage(),
    ),
    GoRoute(
      path: '/library/:librarianID',
      builder: (context, state) => LibraryPage(
          librarianID: state.pathParameters['librarianID'],),
    ),
    GoRoute(
      path: '/library',
      builder: (context, state) => const LibraryPage(),
    ),
  ],
  redirect: (BuildContext context, GoRouterState state) {
    final user = context.read<UserBloc>().state.user;
    if (user != null) {
      return '/library/${user.email}';
    } else {
      return null;
    }
  },
);

void main() {
  runApp(
    MultiBlocProvider(
      providers: [
        BlocProvider<FilterCubit>(create: (context) => FilterCubit()),
        BlocProvider<UserBloc>(create: (context) => UserBloc()),
      ],
      child: const MyApp(),
    ),
  );
  runApp(
    MultiBlocProvider(
      providers: [
        BlocProvider<FilterCubit>(create: (context) => FilterCubit()),
        BlocProvider<UserBloc>(create: (context) => UserBloc()),
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
