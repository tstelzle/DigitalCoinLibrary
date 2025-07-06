import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/repository/user_repository.dart';
import 'package:flutter_frontend/repository/edition_repository.dart';
import 'package:flutter_frontend/LibraryPage/library_view.dart';
import 'package:flutter_frontend/LibraryPage/library_cubit.dart';

class LibraryPage extends StatelessWidget {
  const LibraryPage({required this.librarianId, super.key});
  final String librarianId;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (BuildContext context) => LibraryCubit(
        context.read<EditionRepository>(),
        context.read<UserRepository>(),
        librarianId,
      )..getEditions(),
      child: const LibraryView(),
    );
  }
}
