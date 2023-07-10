import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/home/edition_view.dart';
import 'package:flutter_frontend/home/filter_bar.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';

import '../model/edition.dart';

class MyHomePage extends StatefulWidget {
  final String title;

  const MyHomePage({super.key, required this.title});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
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
    return Scaffold(
        appBar: AppBar(title: Text(widget.title), actions: <Widget>[
          IconButton(
              icon: const Icon(Icons.filter_alt),
              tooltip: "Filter",
              onPressed: () {
                showModalBottomSheet(
                    context: context,
                    builder: (BuildContext context) {
                      return const FilterBar();
                    });
              })
        ]),
        body: PagedListView<int, Edition>(
          pagingController: _pagingController,
          physics: const ScrollPhysics(),
          shrinkWrap: true,
          builderDelegate: PagedChildBuilderDelegate<Edition>(
            itemBuilder: (context, edition, index) {
              return EditionView(edition: edition);
            },
          ),
        ));
  }

  Future<void> _fetchEditions(int pageKey) async {
    try {
      final List<Edition> newEditions = await editionApi.fetchEditions(pageKey);
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
