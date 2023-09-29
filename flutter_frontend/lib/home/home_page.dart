import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/home/home.dart';

import '../core/user_state.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<UserBloc, UserState>(builder: (context, userState) {
      var title = "Digital Coin Library";
      if (userState.user != null) {
        title = "${userState.user!.displayName}'s $title";
      }
      return Scaffold(body: LibraryPage(title: title));
    });
  }
}
