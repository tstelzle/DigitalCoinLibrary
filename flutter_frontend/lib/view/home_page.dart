import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_api.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/view/home_view.dart';
import 'package:flutter_frontend/viewmodel/home_cubit.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (BuildContext context) => HomeCubit(
        context.read<AuthenticationApi>(),
        context.read<AuthenticationRepository>(),
      ),
      child: const HomeView(),
    );
  }
}
