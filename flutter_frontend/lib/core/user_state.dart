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

// State
class UserState {
  // final bool special;
  final bool loggedIn;
  final String user;

  UserState(this.loggedIn, this.user);
}

// Bloc
class UserBloc extends Bloc<UserEvent, UserState> {
  UserBloc() : super(UserState(false, "")) {
    on<LoggedInUserEvent>(
        (event, emit) => emit(UserState(event.value, state.user)));
    on<UserUserEvent>(
        (event, emit) => emit(UserState(state.loggedIn, event.value)));
  }
}
