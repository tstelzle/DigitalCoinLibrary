import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/LibraryPage/library_state.dart';
import 'package:flutter_frontend/LibraryPage/edition_view.dart';
import 'package:flutter_frontend/LibraryPage/library_cubit.dart';

class LibraryView extends StatelessWidget {
  const LibraryView({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LibraryCubit, LibraryState>(
      builder: (context, state) {
        return Scaffold(
          appBar: AppBar(title: Text(state.title)),
          body: ListView.builder(
            itemCount: state.editions.length,
            itemBuilder: (context, index) {
              final edition = state.editions[index];
              return EditionView(
                edition: edition,
              );
            },
          ),
        );
      },
    );
  }
}
