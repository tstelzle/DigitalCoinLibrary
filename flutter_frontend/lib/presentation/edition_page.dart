import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/coin_api.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/model/edition.dart';
import 'package:flutter_frontend/presentation/edition_block.dart';
import 'package:flutter_frontend/presentation/edition_view.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';

class EditionPage extends StatefulWidget {
  const EditionPage({
    required this.librarianID,
    super.key,
  });

  final String? librarianID;

  @override
  State<StatefulWidget> createState() => _EditionPageState();
}

class _EditionPageState extends State<EditionPage> {
  late final EditionBloc _bloc;

  @override
  void initState() {
    super.initState();
    _bloc = EditionBloc(EditionApi(), CoinApi(), widget.librarianID);
  }

  @override
  void dispose() {
    _bloc.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) =>
      BlocBuilder<EditionBloc, PagingState<int, Edition>>(
        bloc: _bloc,
        builder: (context, state) => PagedListView<int, Edition>(
          state: state,
          fetchNextPage: _bloc.fetchNextPage,
          builderDelegate: PagedChildBuilderDelegate(
            itemBuilder: (context, item, index) =>
                EditionView(edition: item, librarianId: widget.librarianID),
          ),
        ),
      );
}
