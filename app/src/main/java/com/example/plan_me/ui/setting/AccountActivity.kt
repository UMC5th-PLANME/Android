package com.example.plan_me.ui.setting

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.service.auth.ImageService
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.databinding.ActivityAccountBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogDeleteActivity
import com.example.plan_me.ui.dialog.DialogLogoutActivity
import com.example.plan_me.ui.login.InitProfileActivity
import com.example.plan_me.ui.login.LoginActivity
import com.example.plan_me.utils.ImageUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AccountActivity: AppCompatActivity(), ChangeProfileView, ProfileImageView, LookUpMemberView {
    private lateinit var binding: ActivityAccountBinding
    private var userName: String? = ""
    private var userImg: String? = ""
    private var social: String? = ""
    private var userEmail: String? = ""
    private var accessToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        setLookUpService()

        getData1()
        getData2()

        binding.accountNameTv.text = userName
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg" && userImg != "null") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.accountImageIv)
        }
        binding.accountSocialTypeTv.text = social

        binding.accountBackBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }

        binding.accountCameraLo.setOnClickListener {
            // 권한이 있는지 확인
            if (checkPermission()) {
                // 이미지 선택 화면으로 이동
                openImagePicker()
            } else {
                // 권한 요청
                requestPermission()
            }
        }

        binding.accountNicknameBtn.setOnClickListener {
            switchActivity(ChangeNicknameActivity())
        }

        binding.accountSocialBtn.setOnClickListener {
            switchActivity(ChangeTypeActivity())
        }

        binding.accountLogoutTv.setOnClickListener {
            showDialog(DialogLogoutActivity(this@AccountActivity, social))
        }

        binding.accountDeleteTv.setOnClickListener {
            showDialog(DialogDeleteActivity(this@AccountActivity, accessToken!!))
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission(): Boolean {
        Log.d("checkPermission", "check")
        return (ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        Log.d("requestPermission", "request")
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
            // 사용자에게 왜 권한이 필요한지 설명을 보여줄 수 있는 로직 추가
            // Android 6.0 (API 23) 이상에서는 권한을 동적으로 요청할 때 왜 필요한 지에 대한 설명 필요
            Log.d("permission", "사진 권한 요청")
        }
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            InitProfileActivity.REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == InitProfileActivity.REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this@AccountActivity, "사진 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, InitProfileActivity.REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == InitProfileActivity.REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // 이미지를 선택한 경우, 이미지뷰에 설정
            val selectedImageUri = data?.data
            binding.accountImageIv.setImageURI(selectedImageUri)
            Picasso.get().load(selectedImageUri).transform(CircleTransform()).into(binding.accountImageIv)

            setEditImg()
        }
    }

    private fun getData1() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
//        userName = sharedPreferences.getString("userName", userName)
//        userImg = sharedPreferences.getString("userImg", userImg)
//        social = sharedPreferences.getString("social", social)
        userEmail = sharedPreferences.getString("email", userEmail)
    }

    private fun getData2() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun setEditImg() {
        val drawable = binding.accountImageIv.drawable
        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap

            // 추출한 비트맵을 파일로 변환
            val imageFile = ImageUtils.bitmapToFile(this, bitmap)

            // 파일을 MultipartBody.Part로 변환
            val imagePart = createImagePart(imageFile)

            // setImageService 호출
            val setImageService = ImageService()
            setImageService.setImageView(this@AccountActivity)
            setImageService.setProfileImg(accessToken!!, imagePart)
        } else {
            // BitmapDrawable로 변환할 수 없는 경우에 대한 처리
            Log.e("Image Conversion", "Drawable is not a BitmapDrawable")
        }

        val setChangeImgService = MemberService()
        setChangeImgService.setChangeProfileView(this@AccountActivity)
        val member = EditProfile(userName!!, userImg!!)
        setChangeImgService.setChangeProfile(accessToken!!, member)
    }

    // 이미지 파일을 MultipartBody.Part로 변환하는 함수
    private fun createImagePart(file: File): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun setLookUpService() {
        val setLookUpService = MemberService()
        setLookUpService.setLookUpMemberView(this@AccountActivity)
        setLookUpService.getLookUpMember()
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("이미지 변경", response.result.toString())
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 변경 실패", message)
    }

    override fun onSetImgSuccess(response: ProfileImageRes) {
        Log.d("이미지 등록", response.result.toString())
    }

    override fun onSetImgFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 등록 실패", message)
    }

    override fun onGetMemberSuccess(response: MemberRes) {
        Log.d("회원 조회", response.result.toString())
        userName = response.result.nickname
        userImg = response.result.profile_image
        social = response.result.login_type
    }

    override fun onGetMemberFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("회원 조회 실패", message)
    }
}