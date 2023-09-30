import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/edition_view.dart';
import 'package:flutter_frontend/home/filter_bar.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';

import '../model/edition.dart';

class LibraryPage extends StatefulWidget {
  final UserState userState;

  const LibraryPage({super.key, required this.userState});

  @override
  State<LibraryPage> createState() => _LibraryPageState();
}

class _LibraryPageState extends State<LibraryPage> {
  late FilterCubit filterBloc;
  final EditionApi editionApi = EditionApi();
  static const _pageSize = 5;
  final PagingController<int, Edition> _pagingController =
      PagingController(firstPageKey: 0);

  @override
  void initState() {
    _pagingController.addPageRequestListener((pageKey) {
      _fetchEditions(pageKey);
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    filterBloc = BlocProvider.of<FilterCubit>(context);
    var title = "Digital Coin Library";
    if (widget.userState.user != null) {
      title = "${widget.userState.user!.displayName}'s $title";
    }
    return Scaffold(
        appBar: AppBar(title: Text(title), actions: <Widget>[
          IconButton(
              icon: const Icon(Icons.filter_alt),
              tooltip: "Filter",
              onPressed: () {
                showModalBottomSheet(
                    context: context,
                    builder: (context) {
                      return BlocBuilder<FilterCubit, FilterState>(
                          builder: (context, filterState) {
                        return FilterBar(filterState: filterState);
                      });
                    });
              })
        ]),
        body: PagedListView<int, Edition>(
          pagingController: _pagingController,
          physics: const ScrollPhysics(),
          shrinkWrap: true,
          builderDelegate: PagedChildBuilderDelegate<Edition>(
              itemBuilder: (context, edition, index) {
            return BlocBuilder<FilterCubit, FilterState>(
                builder: (context, filterState) {
              return EditionView(
                  edition: edition,
                  userState: widget.userState,
                  filterState: filterState);
            });
          }),
        ));
  }

  Future<void> _fetchEditions(int pageKey) async {
    try {
      filterBloc.stream.listen((event) {
        _pagingController.refresh();
      });
      final List<Edition> newEditions = await editionApi.fetchEditions(
          pageKey, filterBloc.state.country, filterBloc.state.coinSize == -1);
      final isLastPage = newEditions.length < _pageSize;
      if (isLastPage) {
        _pagingController.appendLastPage(newEditions);
      } else {
        final nextPageKey = pageKey + 1;
        _pagingController.appendPage(newEditions, nextPageKey);
      }
    } catch (error) {
      _pagingController.error = error;
    }
  }

  @override
  void dispose() {
    _pagingController.dispose();
    super.dispose();
  }
}
