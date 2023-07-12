import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/home/home_page.dart';

import 'core/filter_state.dart';
import 'home/home.dart';

void main() {
  runApp(MultiBlocProvider(
      providers: [BlocProvider<FilterBloc>(create: (context) => FilterBloc())],
      child: MyApp()));
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
