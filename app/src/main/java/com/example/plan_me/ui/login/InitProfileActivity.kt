package com.example.plan_me.ui.login

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import com.example.plan_me.ui.main.MainActivity
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import com.example.plan_me.data.remote.service.auth.ImageService
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.data.remote.view.auth.SignUpView
import com.example.plan_me.databinding.ActivityInitProfileBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.utils.ImageUtils
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class InitProfileActivity : AppCompatActivity(), ProfileImageView, ChangeProfileView {
    private lateinit var binding: ActivityInitProfileBinding
    private var userName: String? = ""
    private var userImg: String? = ""
    private var userEmail: String? = ""
    private var userType: String? = ""
    private var accessToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        getData()

        binding.initProfileNameTv.setText(userName)
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg" && userImg != "null") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.initProfileImagefileIv)
        }

        binding.initProfileCameraLo.setOnClickListener {
            // 권한이 있는지 확인
            if (checkPermission()) {
                // 이미지 선택 화면으로 이동
                openImagePicker()
            } else {
                // 권한 요청
                requestPermission()
            }
        }

        binding.initProfileCompletBtn.setOnClickListener {
            setEditProfileService()
        }
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
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this@InitProfileActivity, "사진 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {}

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    // 이미지 파일을 MultipartBody.Part로 변환하는 함수
    private fun createImagePart(file: File): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // 이미지를 선택한 경우, 이미지뷰에 설정
            val selectedImageUri = data?.data
            binding.initProfileImagefileIv.setImageURI(selectedImageUri)
            Picasso.get().load(selectedImageUri).transform(CircleTransform()).into(binding.initProfileImagefileIv)

            val drawable = binding.initProfileImagefileIv.drawable
            if (drawable is BitmapDrawable) {
                val bitmap: Bitmap = drawable.bitmap

                // 추출한 비트맵을 파일로 변환
                val imageFile = ImageUtils.bitmapToFile(this, bitmap)

                // 파일을 MultipartBody.Part로 변환
                val imagePart = createImagePart(imageFile)

                // setImageService 호출
                val setImageService = ImageService()
                setImageService.setImageView(this@InitProfileActivity)
                setImageService.setProfileImg(accessToken!!, imagePart)
            } else {
                // BitmapDrawable로 변환할 수 없는 경우에 대한 처리
                Log.e("Image Conversion", "Drawable is not a BitmapDrawable")
            }
        }
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        userEmail = sharedPreferences.getString("email", userEmail)
        userType = sharedPreferences.getString("social", userType)
        accessToken = sharedPreferences.getString("accessToken", accessToken)
    }

    private fun goMainActivity() {
        val intent = Intent(this@InitProfileActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun setEditProfileService() {
        val setEditProfileService = MemberService()
        setEditProfileService.setChangeProfileView(this@InitProfileActivity)
        val member = Member(userName!!, userImg!!, userType!!, userEmail!!)
        setEditProfileService.setChangeProfile(accessToken!!, member)
    }

    companion object {
        val REQUEST_PERMISSION_CODE = 423
        val REQUEST_IMAGE_PICK = 826
    }

    override fun onSetImgSuccess(response: ProfileImageRes) {
        Log.d("이미지 등록", response.message)
    }

    override fun onSetImgFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 등록 실패", message)
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("프로필 변경", response.result.toString())
        goMainActivity()
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("프로필 변경 실패", message)
    }
}