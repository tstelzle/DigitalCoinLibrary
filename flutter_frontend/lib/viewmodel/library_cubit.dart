import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/model/edition.dart';
import 'package:flutter_frontend/model/library_state.dart';
import 'package:flutter_frontend/repository/authentication_repository.dart';
import 'package:flutter_frontend/repository/edition_api.dart';

class LibraryCubit extends Cubit<LibraryState> {
  LibraryCubit(this.editionApi, this.authenticationRepository)
      : super(LibraryState(editions: [], title: 'Library'));
  EditionApi editionApi;

  AuthenticationRepository authenticationRepository;

  Future<List<Edition>> getEditions() async {
    try {
      final user = await authenticationRepository.getCurrentUser();
      final userEmail = user?.email ?? '';
      final editions = await editionApi.fetchEditions(userEmail);
      var title = 'Digital Coin Library';
      if (userEmail != '') {
        title = "$userEmail's Digital Coin Library";
      }
      emit(LibraryState(editions: editions, title: title));
      return editions;
    } catch (e) {
      emit(LibraryState(editions: [], title: ''));
      return [];
    }
  }

  void dispose() {
    return;
  }
}
