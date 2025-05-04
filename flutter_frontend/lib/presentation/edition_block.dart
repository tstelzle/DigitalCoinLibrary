import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/coin_api.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/model/edition.dart';
import 'package:infinite_scroll_pagination/infinite_scroll_pagination.dart';

sealed class EditionEvent {}

final class FetchNextEditionPage extends EditionEvent {}

class EditionBloc extends Bloc<EditionEvent, PagingState<int, Edition>> {
  EditionBloc(this.editionApi, this.coinApi, this.librarianId)
      : super(PagingState()) {
    on<FetchNextEditionPage>((event, emit) async {
      final currentState = state;
      if (currentState.isLoading) return;

      emit(currentState.copyWith(isLoading: true, error: null));

      try {
        final newKey = (currentState.keys?.last ?? -1) + 1;
        final newItems = await getEditions(newKey);
        final isLastPage = newItems.isEmpty;

        emit(
          state.copyWith(
            pages: [...?state.pages, newItems],
            keys: [...?state.keys, newKey],
            hasNextPage: !isLastPage,
            isLoading: false,
          ),
        );
      } catch (error) {
        emit(
          state.copyWith(
            error: error,
            isLoading: false,
          ),
        );
      }
    });
  }

  void fetchNextPage() => add(FetchNextEditionPage());

  final EditionApi editionApi;
  final CoinApi coinApi;
  final String? librarianId;

  Future<List<Edition>> getEditions(int pageKey) async {
    final editions = await editionApi.fetchEditions(pageKey, 'all', false);

    final librarianIdNotNull = librarianId ?? '';

    for (final edition in editions) {
      // TODO get librarian identification
      final editionCoins =
          await coinApi.fetchCoinsByEdition(edition.id, -1, librarianIdNotNull);
      for (final coin in editionCoins) {
        coin.setAvailableColor(coin.available, librarianIdNotNull);
      }
      edition.coins = editionCoins;
    }

    return editions;
  }

  void dispose() {
    return;
  }
}
