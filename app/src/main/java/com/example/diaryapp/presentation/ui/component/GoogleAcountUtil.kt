package com.example.diaryapp.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.diaryapp.presentation.ui.component.button.GoogleSignInButton
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

private const val WEB_CLIENT_ID = "242031461378-1dis0g4b9jqp9aajuu5ajglntdvaa4ut.apps.googleusercontent.com"

@Composable
fun GoogleAccountUtil(
    onSuccess: (email: String, idToken: String) -> Unit,
    onError: (String) -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope() // 컴포즈에서 비동기 작업을 위해 필요

    val credentialManager = remember {
        CredentialManager.create(context)
    }

    GoogleSignInButton(
        onClick = {
            scope.launch {
                try {
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(WEB_CLIENT_ID)
                        .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    val result = credentialManager.getCredential(
                        context = context,
                        request = request
                    )

                    val credential = result.credential

                    if (
                        credential is CustomCredential &&
                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {
                        val googleCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        onSuccess(
                            googleCredential.id,
                            googleCredential.idToken
                        )
                    } else {
                        onError("지원하지 않는 로그인 방식입니다.")
                    }
                } catch (e: GetCredentialException) {
                    onError(e.message ?: "구글 로그인에 실패했습니다.")
                } catch (e: Exception) {
                    onError(e.message ?: "알 수 없는 오류가 발생했습니다.")
                }
            }
        }
    )
}

