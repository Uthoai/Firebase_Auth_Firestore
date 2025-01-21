package com.firebase.auth.firestore

import com.firebase.auth.firestore.utils.Constants

sealed class Destination(val route: String) {
    data object StarterScreen: Destination(Constants.STARTER_SCREEN)
    data object SignInScreen: Destination(Constants.SIGN_IN_SCREEN)
    data object SignUpScreen: Destination(Constants.SIGN_UP_SCREEN)
    data object HomeScreen: Destination(Constants.HOME_SCREEN)
}

