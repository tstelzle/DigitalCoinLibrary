import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/home_page.dart';
import 'package:flutter_frontend/home/library_page.dart';

import 'core/filter_state.dart';

void main() {
  runApp(MultiBlocProvider(providers: [
    BlocProvider<FilterCubit>(create: (context) => FilterCubit()),
    BlocProvider<UserBloc>(create: (context) => UserBloc()),
  ], child: const MyApp(),),);
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Digital Coin Library',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const HomePage(),
    );
  }
}
