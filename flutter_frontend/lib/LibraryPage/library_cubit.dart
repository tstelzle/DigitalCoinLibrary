import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/model/edition.dart';
import 'package:flutter_frontend/LibraryPage/library_state.dart';
import 'package:flutter_frontend/repository/user_repository.dart';
import 'package:flutter_frontend/repository/edition_repository.dart';

class LibraryCubit extends Cubit<LibraryState> {
  LibraryCubit(this.editionApi, this.authenticationRepository, this.librarianId)
      : super(LibraryState(editions: [], title: 'Library'));
  EditionRepository editionApi;
  UserRepository authenticationRepository;
  String librarianId;

  Future<List<Edition>> getEditions() async {
    try {
      String userEmail;
      if (librarianId.isNotEmpty) {
        userEmail = librarianId;
      } else {
        final user = await authenticationRepository.getCurrentUser();
        userEmail = user?.email ?? '';
      }
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
