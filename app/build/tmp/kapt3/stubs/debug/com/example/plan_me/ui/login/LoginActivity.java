package com.example.plan_me.ui.login;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u000eH\u0002J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\u0016\u0010\u001f\u001a\u00020\u001c2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001e0!H\u0002J\b\u0010\"\u001a\u00020\u001cH\u0002J\b\u0010#\u001a\u00020\u001cH\u0002J\"\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020&2\b\u0010(\u001a\u0004\u0018\u00010\u000bH\u0014J\u0012\u0010)\u001a\u00020\u001c2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\b\u0010,\u001a\u00020\u001cH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0013\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\u0016\u00a8\u0006-"}, d2 = {"Lcom/example/plan_me/ui/login/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "NATIVE_APP_KEY", "", "getNATIVE_APP_KEY", "()Ljava/lang/String;", "binding", "Lcom/example/plan_me/databinding/ActivityLoginBinding;", "googleAuthLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "getGoogleSignInClient", "()Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "googleSignInClient$delegate", "Lkotlin/Lazy;", "nickname", "getNickname", "setNickname", "(Ljava/lang/String;)V", "profile", "getProfile", "setProfile", "getGoogleClient", "getUserInfoFromGoogle", "", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "handleGoogleSignInResult", "compltedTask", "Lcom/google/android/gms/tasks/Task;", "login_google", "login_kakao", "onActivityResult", "requestCode", "", "resultCode", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openTermsPopup", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String NATIVE_APP_KEY = "55b1939dd22f9a5ef6176930e7a09231";
    private com.example.plan_me.databinding.ActivityLoginBinding binding;
    @org.jetbrains.annotations.NotNull
    private java.lang.String nickname = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String profile = "";
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy googleSignInClient$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> googleAuthLauncher = null;
    
    public LoginActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNATIVE_APP_KEY() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNickname() {
        return null;
    }
    
    public final void setNickname(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProfile() {
        return null;
    }
    
    public final void setProfile(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    private final com.google.android.gms.auth.api.signin.GoogleSignInClient getGoogleSignInClient() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void handleGoogleSignInResult(com.google.android.gms.tasks.Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount> compltedTask) {
    }
    
    private final void getUserInfoFromGoogle(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    private final void login_kakao() {
    }
    
    private final com.google.android.gms.auth.api.signin.GoogleSignInClient getGoogleClient() {
        return null;
    }
    
    private final void login_google() {
    }
    
    private final void openTermsPopup() {
    }
}