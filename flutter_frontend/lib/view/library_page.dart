import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/repository/edition_api.dart';
import 'package:flutter_frontend/view/library_view.dart';
import 'package:flutter_frontend/viewmodel/library_cubit.dart';

class LibraryPage extends StatelessWidget {
  const LibraryPage({required this.librarianId, super.key});
  final String librarianId;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (BuildContext context) => LibraryCubit(
        context.read<EditionApi>(),
        context.read<AuthenticationRepository>(),
        librarianId,
      )..getEditions(),
      child: const LibraryView(),
    );
  }
}
