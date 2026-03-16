import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/repository/user_repository.dart';
import 'package:flutter_frontend/HomePage/home_view.dart';
import 'package:flutter_frontend/HomePage/home_cubit.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (BuildContext context) => HomeCubit(
        context.read<AuthenticationRepository>(),
        context.read<UserRepository>(),
      ),
      child: const HomeView(),
    );
  }
}
