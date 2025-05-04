import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/filter_bar.dart';
import 'package:flutter_frontend/presentation/edition_page.dart';

class LibraryPage extends StatefulWidget {
  const LibraryPage({super.key, this.librarianID});
  final String? librarianID;

  @override
  State<LibraryPage> createState() => _LibraryPageState();
}

class _LibraryPageState extends State<LibraryPage> {
  late FilterCubit filterBloc;

  @override
  Widget build(BuildContext context) {
    filterBloc = BlocProvider.of<FilterCubit>(context);
    var title = 'Digital Coin Library';
    if (widget.librarianID != null) {
      title = "${widget.librarianID}'s $title";
    }
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.filter_alt),
            tooltip: 'Filter',
            onPressed: () {
              showModalBottomSheet(
                context: context,
                builder: (context) {
                  return BlocBuilder<FilterCubit, FilterState>(
                    builder: (context, filterState) {
                      return FilterBar(filterState: filterState);
                    },
                  );
                },
              );
            },
          ),
        ],
      ),
      body: EditionPage(librarianID: widget.librarianID),
    );
  }
}
