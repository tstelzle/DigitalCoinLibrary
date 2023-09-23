import 'package:flutter_bloc/flutter_bloc.dart';

abstract class UserEvent {}

class LoggedInUserEvent extends UserEvent {
  final bool value;

  LoggedInUserEvent(this.value);
}

class UserUserEvent extends UserEvent {
  final String value;

  UserUserEvent(this.value);
}

class IdUserEvent extends UserEvent {
  final String value;

  IdUserEvent(this.value);
}

// State
class UserState {
  // final bool special;
  final bool loggedIn;
  final String user;
  final String userId;

  UserState(this.loggedIn, this.user, this.userId);
}

// Bloc
class UserBloc extends Bloc<UserEvent, UserState> {
  UserBloc() : super(UserState(false, "", "")) {
    on<LoggedInUserEvent>(
        (event, emit) => emit(UserState(event.value, state.user, state.userId)));
    on<UserUserEvent>(
        (event, emit) => emit(UserState(state.loggedIn, event.value, state.userId)));
    on<IdUserEvent>(
            (event, emit) => emit(UserState(state.loggedIn, state.user, event.value)));
  }
}
