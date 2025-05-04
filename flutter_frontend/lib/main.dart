import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/home_page.dart';
import 'package:flutter_frontend/home/library_page.dart';
import 'package:get_it/get_it.dart';
import 'package:go_router/go_router.dart';

final GetIt getIt = GetIt.instance;

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
        librarianID: state.pathParameters['librarianID'],
      ),
    ),
    GoRoute(
      path: '/library',
      builder: (context, state) => const LibraryPage(librarianID: ''),
    ),
  ],
  redirect: (BuildContext context, GoRouterState state) {
    final user = getIt<UserBloc>().state.user;
    if (user != null) {
      return '/library/${user.email}';
    } else {
      return null;
    }
  },
);

void SetUp() {
  getIt.registerSingleton<UserBloc>(UserBloc());
}

void main() async {
  SetUp();
  runApp(
    MultiBlocProvider(
      providers: [
        BlocProvider<FilterCubit>(create: (context) => FilterCubit()),
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
